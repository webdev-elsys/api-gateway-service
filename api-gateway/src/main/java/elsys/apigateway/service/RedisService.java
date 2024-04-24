package elsys.apigateway.service;

public interface RedisService {
    void save(String key, String value);
    String get(String key);
    void delete(String key);
}
