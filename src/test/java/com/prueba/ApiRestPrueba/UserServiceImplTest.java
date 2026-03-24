package com.prueba.ApiRestPrueba;

import com.prueba.ApiRestPrueba.model.Address;
import com.prueba.ApiRestPrueba.model.User;
import com.prueba.ApiRestPrueba.repository.UserRepository;
import com.prueba.ApiRestPrueba.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(
                UUID.randomUUID(),
                "user1@mail.com",
                "user1",
                "+1 55 555 555 55",
                "passwordencriptada",
                "AARR990101XXX",
                "01-01-2026 00:00:00",
                Arrays.asList(
                        new Address(1, "workaddress", "street No. 1", "UK")
                )
        );
    }

    //Obtener todos los usuarios sin orden
    @Test
    void testFindAllWithoutSort() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.findAll((String) null);

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    //Crear usuario
    @Test
    void testCreateUserSuccess() {
        User newUser = new User(
                null,
                "user3@mail.com",
                "user3",
                "+1 55 555 555 57",
                "password123",
                "CCRR990101XXX",
                null,
                null
        );

        when(userRepository.existsByTaxId("CCRR990101XXX")).thenReturn(false);

        User result = userService.create(newUser);

        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
        verify(userRepository, times(1)).save(newUser);
    }

    //Crear usuario con tax_id duplicado
    @Test
    void testCreateUserDuplicateTaxId() {
        User newUser = new User(
                null,
                "user1@mail.com",
                "user1",
                "+1 55 555 555 55",
                "password123",
                "AARR990101XXX",
                null,
                null
        );

        when(userRepository.existsByTaxId("AARR990101XXX")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.create(newUser));
        verify(userRepository, never()).save(any());
    }

    //Eliminar usuario
    @Test
    void testDeleteUserSuccess() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        userService.delete(user1.getId());

        verify(userRepository, times(1)).delete(user1.getId());
    }
}
