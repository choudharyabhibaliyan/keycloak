package com.keycloak.service;

import com.keycloak.constants.KeycloakEndpoints;
import com.keycloak.models.KeycloakUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class KeycloakService {
    @Value("${keycloak.admin.username}")
    private String keycloakAdminUsername;
    @Value("${keycloak.admin.password}")
    private String keycloakAdminPassword;
    @Value("${keycloak.admin.url}")
    private String keycloakAdminUrl;
    @Value("${keycloak.admin.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.secret}")
    private String clientSecret;


    public ResponseEntity<String> generateCustomToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Construct authorization header
        String credentials = clientId + ":" + clientSecret;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        // Create request entity
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("username", keycloakAdminUsername);
        requestBody.add("password", keycloakAdminPassword);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        // Keycloak token endpoint
        String tokenEndpoint = keycloakAdminUrl + realm + KeycloakEndpoints.tokenEndPoint;
        // Make request to Keycloak
        ResponseEntity<String> response = new RestTemplate().exchange(tokenEndpoint, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String generatedToken = response.getBody();
            return ResponseEntity.ok("Generated Token: " + generatedToken);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error generating token: " + response.getBody());
        }
    }


    public KeycloakUser createUser(KeycloakUser user) {
        String createUserUrl = keycloakAdminUrl + KeycloakEndpoints.createUserEndPoint;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(keycloakAdminUsername, keycloakAdminPassword);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<KeycloakUser> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<Void> response = new RestTemplate().exchange(createUserUrl, HttpMethod.POST, requestEntity, Void.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("User created successfully.");
        } else {
            System.err.println("Failed to create user.");
        }
        return user;
    }
}
