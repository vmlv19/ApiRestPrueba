package com.prueba.ApiRestPrueba.service;

import com.prueba.ApiRestPrueba.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAll(String sortedBy);
    List<User> findAllFiltered(String filter);

    User create(User user);
    User update(UUID id, User user);
    void delete(UUID id);
    User findByTaxId(String taxId);

}
