package com.keycloak.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakUser {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
