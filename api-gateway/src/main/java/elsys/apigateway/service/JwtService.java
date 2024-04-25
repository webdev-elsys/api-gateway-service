package elsys.apigateway.service;

import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String generateAccessToken(String uuid);
    String generateRefreshToken(String uuid);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String extractUuid(String token);
    Date extractExpiration(String token);
    Boolean validateToken(String token);
}
