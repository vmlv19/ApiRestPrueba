package com.prueba.ApiRestPrueba.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID id;
    private String email;
    private String name;
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String taxId;
    private String createdAt;
    private List<Address> addresses;
}
