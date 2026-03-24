package com.prueba.ApiRestPrueba.controller;

import com.prueba.ApiRestPrueba.model.User;
import com.prueba.ApiRestPrueba.service.UserService;
import com.prueba.ApiRestPrueba.util.AesEncryptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>>  login(@RequestBody Map<String, String> request) {

        String taxId = request.get("tax_id");
        String password = request.get("password");

        if(taxId == null || password == null) {
            Map<String, String> error = new HashMap<>();
            error.put("Error", "tax_id and password are required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            User user = userService.findByTaxId(taxId);

            String encrytedPassword = AesEncryptionUtil.encrypt(password);

            if(!user.getPassword().equals(encrytedPassword)) {
                Map<String, String> error = new HashMap<>();
                error.put("Error", "Invalid credentiales");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            Map<String, String> response = new HashMap<>();
            response.put("Message", "Login succesfull");
            response.put("tax_id", user.getTaxId());
            response.put("name", user.getName());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("Error", "Invalid credentiales");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}