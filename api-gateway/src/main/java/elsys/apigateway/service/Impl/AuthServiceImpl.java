package elsys.apigateway.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import elsys.apigateway.dto.AuthResponse;
import elsys.apigateway.dto.UserDto;
import elsys.apigateway.service.AuthService;
import elsys.apigateway.service.RedisService;
import elsys.apigateway.service.UserApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserApiService userApiService;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final RedisService redisService;

    public AuthResponse signUp(Object signUpRequest) {
        UserDto user = userApiService.signUp(signUpRequest);
        return generateTokens(user);
    }

    public AuthResponse signIn(Object signInRequest) {
        UserDto user = userApiService.signIn(signInRequest);
        return generateTokens(user);
    }

    private AuthResponse generateTokens(UserDto user) {
        String accessToken = jwtServiceImpl.generateAccessToken(user.uuid());
        String refreshToken = jwtServiceImpl.generateRefreshToken(user.uuid());

        redisService.save(user.uuid(), passwordEncoder.encode(refreshToken));

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userUuid;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userUuid = jwtServiceImpl.extractUuid(refreshToken);

        if (userUuid != null) {
            String oldRefreshToken = redisService.get(userUuid);

            if (oldRefreshToken == null || !passwordEncoder.matches(refreshToken, oldRefreshToken)) {
                throw new IllegalArgumentException("Invalid refresh token");
            }

            if (jwtServiceImpl.validateToken(refreshToken)) {
                String accessToken = jwtServiceImpl.generateAccessToken(userUuid);
                String newRefreshToken = jwtServiceImpl.generateRefreshToken(userUuid);

                redisService.save(userUuid, passwordEncoder.encode(newRefreshToken));

                AuthResponse authResponse = new AuthResponse(accessToken, newRefreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void signOut() {
        String userUuid = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        redisService.delete(userUuid);
    }
}
