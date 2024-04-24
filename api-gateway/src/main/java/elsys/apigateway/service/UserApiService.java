package elsys.apigateway.service;

import elsys.apigateway.dto.UserDto;

public interface UserApiService {
    UserDto signUp(Object signUpRequest);
    UserDto signIn(Object signInRequest);
}
