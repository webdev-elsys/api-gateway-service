package elsys.apigateway.service.Impl;

import elsys.apigateway.config.jwt.JwtConfig;
import elsys.apigateway.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtConfig jwtConfig;

    public String generateAccessToken(String uuid) {
        return generateToken(uuid, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(String uuid) {
        return generateToken(uuid, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(String uuid, long expiration) {
        Date now = new Date();
        Date tokenExpiration = new Date(now.getTime() + jwtConfig.getAccessTokenExpiration());

        return Jwts.builder()
                .setSubject(uuid)
                .setIssuedAt(now)
                .setExpiration(tokenExpiration)
                .signWith(jwtConfig.getSecretKey(), jwtConfig.getAlgorithm())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtConfig.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUuid(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
