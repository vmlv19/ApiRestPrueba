package com.prueba.ApiRestPrueba.service.impl;

import com.prueba.ApiRestPrueba.model.User;
import com.prueba.ApiRestPrueba.repository.UserRepository;
import com.prueba.ApiRestPrueba.service.UserService;
import com.prueba.ApiRestPrueba.util.AesEncryptionUtil;
import org.springframework.stereotype.Service;
import com.prueba.ApiRestPrueba.util.ValidationUtil;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(String sortedBy) {
        List<User> users = userRepository.findAll();

        if(sortedBy == null || sortedBy.isEmpty()) {
            return users;
        }

        return switch (sortedBy) {
            case "email" -> users.stream()
                    .sorted(Comparator.comparing(User::getEmail))
                    .collect(Collectors.toList());

            case "id" -> users.stream()
                    .sorted(Comparator.comparing(u -> u.getId().toString()))
                    .collect(Collectors.toList());

            case "name" -> users.stream()
                    .sorted(Comparator.comparing(User::getName))
                    .collect(Collectors.toList());

            case "phone" -> users.stream()
                    .sorted(Comparator.comparing(User::getPhone))
                    .collect(Collectors.toList());

            case "tax_id" -> users.stream()
                    .sorted(Comparator.comparing(User::getTaxId))
                    .collect(Collectors.toList());

            case "created_at" -> users.stream()
                    .sorted(Comparator.comparing(User::getCreatedAt))
                    .collect(Collectors.toList());

            default -> users;
        };
    }

    @Override
    public List<User> findAllFiltered(String filter) {
        String[] parts = filter.split("\\+", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Filter format invalid. Use: field+operator+value");
        }

        String field = parts[0];
        String operator = parts[1];
        String value = parts[2];

        return userRepository.findAll().stream()
                .filter(user -> applyFilter(user, field, operator, value))
                .collect(Collectors.toList());
    }

    private boolean applyFilter(User user, String field, String operator, String value){
        String fieldValue = switch (field) {
            case "email" -> user.getEmail();
            case "id" -> user.getId().toString();
            case "name" -> user.getName();
            case "phone" -> user.getPhone();
            case "tax_id" -> user.getTaxId();
            case "created_at" -> user.getCreatedAt();
            default -> throw new IllegalArgumentException("Invalid field: " + field);
        };

        return switch (operator) {
            case "co" -> fieldValue.contains(value);
            case "eq" -> fieldValue.equals(value);
            case "sw" -> fieldValue.startsWith(value);
            case "ew" -> fieldValue.endsWith(value);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    @Override
    public User create(User user) {
        //Validar RFC
        if(!ValidationUtil.isValidRFC(user.getTaxId())) {
            throw new IllegalArgumentException("Invalid RFC format for tax_id: " + user.getTaxId());
        }

        //Validar celular
        if(!ValidationUtil.isValidPhone(user.getPhone())) {
            throw new IllegalArgumentException("Invalid phone format: " + user.getPhone());
        }

        //Validar tax_id unico
        if(userRepository.existsByTaxId(user.getTaxId())) {
            throw new IllegalArgumentException("tax_id already exosts: " + user.getId());
        }

        user.setId(UUID.randomUUID());
        user.setCreatedAt(getCurrentMadagascarTime());
        user.setPassword(AesEncryptionUtil.encrypt(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(UUID id, User updatedUser) {
        User existing = userRepository.findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        if (updatedUser.getEmail() != null) existing.setEmail(updatedUser.getEmail());
        if (updatedUser.getName() != null) existing.setName(updatedUser.getName());
        if (updatedUser.getPhone() != null) existing.setPhone(updatedUser.getPhone());
        if (updatedUser.getPassword() != null) existing.setPassword(updatedUser.getPassword());
        if (updatedUser.getTaxId() != null) existing.setTaxId(updatedUser.getTaxId());
        if (updatedUser.getAddresses() != null) existing.setAddresses(updatedUser.getAddresses());

        userRepository.update(existing);
        return existing;
    }

    @Override
    public void delete(UUID id) {
        userRepository.findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

        userRepository.delete(id);
    }

    @Override
    public User findByTaxId(String taxId) {
        return userRepository.findAll().stream()
                .filter(u -> u.getTaxId().equals(taxId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found with the tax_id: " + taxId));
    }

    private String getCurrentMadagascarTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Indian/Antananarivo"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return now.format(formatter);
    }
}