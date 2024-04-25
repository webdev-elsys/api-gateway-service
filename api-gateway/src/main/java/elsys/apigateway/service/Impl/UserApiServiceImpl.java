package elsys.apigateway.service.Impl;

import elsys.apigateway.api.user_api.UserApiEndpoints;
import elsys.apigateway.dto.UserDto;
import elsys.apigateway.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserApiServiceImpl implements UserApiService {
    private final WebClient webClient;

    @Autowired
    public UserApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public UserDto signUp(Object signUpRequest) {
        String endpoint = UserApiEndpoints.signUp();

        return webClient
                .post()
                .uri(endpoint)
                .bodyValue(signUpRequest)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public UserDto signIn(Object signInRequest) {
        String endpoint = UserApiEndpoints.signIn();

        return webClient
                .post()
                .uri(endpoint)
                .bodyValue(signInRequest)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
