package elsys.apigateway.controller;

import elsys.apigateway.dto.AuthResponse;
import elsys.apigateway.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    private ResponseEntity<AuthResponse> signUp(@RequestBody Object signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    private ResponseEntity<AuthResponse> signIn(@RequestBody Object signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/refresh-token")
    private void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @DeleteMapping("/signout")
    private ResponseEntity<Void> signOut() {
        authService.signOut();
        return ResponseEntity.noContent().build();
    }
}
