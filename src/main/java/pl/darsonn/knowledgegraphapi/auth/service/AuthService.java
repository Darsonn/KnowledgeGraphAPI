package pl.darsonn.knowledgegraphapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.darsonn.knowledgegraphapi.auth.dto.AuthenticationRequest;
import pl.darsonn.knowledgegraphapi.auth.dto.AuthenticationResponse;
import pl.darsonn.knowledgegraphapi.auth.dto.RegisterRequest;
import pl.darsonn.knowledgegraphapi.exception.DuplicateException;
import pl.darsonn.knowledgegraphapi.exception.ResourceNotFoundException;
import pl.darsonn.knowledgegraphapi.user.model.User;
import pl.darsonn.knowledgegraphapi.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateException("Email already taken");
        }

        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // Haszowanie!
                .displayName(request.displayName())
                .role(User.UserRole.ROLE_USER)
                .provider(User.AuthProvider.LOCAL)
                .enabled(true)
                .build();

        userRepository.save(user);

        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + request.email() + " not found"));

        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name().replace("ROLE_", ""))
                .build();

        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthenticationResponse(jwtToken);
    }
}