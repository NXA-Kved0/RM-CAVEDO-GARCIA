package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @GetMapping("/public/hello")
    public ResponseEntity<Map<String, String>> publicHello() {
        return ResponseEntity.ok(Map.of("message", "Respuesta pública - Acceso sin autenticar"));
    }

    @GetMapping("/private/hello")
    public ResponseEntity<Map<String, Object>> privateHello(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("username");
        String email = jwt.getClaimAsString("email");

        return ResponseEntity.ok(Map.of(
            "message", "Respuesta protegida - Autenticado exitosamente con AWS Cognito",
            "username", username != null ? username : "N/A",
            "email", email != null ? email : "N/A",
            "sub", jwt.getSubject()
        ));
    }
}