package elsys.apigateway.service.Impl;

import elsys.apigateway.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value, refreshTokenExpiration, TimeUnit.MILLISECONDS);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
