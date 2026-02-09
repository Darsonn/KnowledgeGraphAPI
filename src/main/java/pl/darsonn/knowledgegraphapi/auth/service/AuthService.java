package pl.darsonn.knowledgegraphapi.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.darsonn.knowledgegraphapi.auth.dto.AuthenticationRequest;
import pl.darsonn.knowledgegraphapi.auth.dto.AuthenticationResponse;
import pl.darsonn.knowledgegraphapi.auth.dto.RefreshTokenRequest;
import pl.darsonn.knowledgegraphapi.auth.dto.RegisterRequest;
import pl.darsonn.knowledgegraphapi.auth.token.model.RefreshToken;
import pl.darsonn.knowledgegraphapi.auth.token.service.RefreshTokenService;
import pl.darsonn.knowledgegraphapi.exception.DuplicateException;
import pl.darsonn.knowledgegraphapi.user.model.User;
import pl.darsonn.knowledgegraphapi.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateException("Email already taken");
        }

        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .displayName(request.displayName())
                .role(User.UserRole.ROLE_USER)
                .provider(User.AuthProvider.LOCAL)
                .enabled(true)
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return new AuthenticationResponse(jwtToken, refreshToken.getToken());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        return new AuthenticationResponse(jwtToken, refreshToken.getToken());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        return refreshTokenService.findByToken(request.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                    String accessToken = jwtService.generateToken(userDetails);
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
                    return new AuthenticationResponse(accessToken, newRefreshToken.getToken());
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}