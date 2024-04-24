package elsys.apigateway.service;

import elsys.apigateway.dto.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    AuthResponse signUp(Object signUpRequest);
    AuthResponse signIn(Object signInRequest);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void signOut();
}
