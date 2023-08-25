package com.keycloak.apis;

import com.keycloak.models.KeycloakUser;
import com.keycloak.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class KeycloakApiController {

    @Autowired
    private KeycloakService keycloakService;

    @GetMapping("/generate-custom-token")
    public ResponseEntity<String> generateCustomToken() {
        return keycloakService.generateCustomToken();
    }

    @PostMapping("/create-user")
    public KeycloakUser createUser(@RequestBody KeycloakUser user) {
        return keycloakService.createUser( user);
    }
}
