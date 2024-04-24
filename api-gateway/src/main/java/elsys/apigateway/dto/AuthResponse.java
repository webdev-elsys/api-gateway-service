package elsys.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String uuid;
    private String accessToken;
    private String refreshToken;
}
