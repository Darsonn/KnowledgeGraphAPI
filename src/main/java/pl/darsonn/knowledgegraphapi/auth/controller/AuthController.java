package pl.darsonn.knowledgegraphapi.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.darsonn.knowledgegraphapi.auth.dto.AuthenticationRequest;
import pl.darsonn.knowledgegraphapi.auth.dto.AuthenticationResponse;
import pl.darsonn.knowledgegraphapi.auth.dto.RefreshTokenRequest;
import pl.darsonn.knowledgegraphapi.auth.dto.RegisterRequest;
import pl.darsonn.knowledgegraphapi.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}