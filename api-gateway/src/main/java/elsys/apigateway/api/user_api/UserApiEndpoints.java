package elsys.apigateway.api.user_api;

public class UserApiEndpoints {
    private static final String baseUrl = System.getenv("USER_SERVICE_API_URL");
    private static final String users = "/users";

    public static String signUp() {
        return baseUrl + users + "/signup";
    }

    public static String signIn() {
        return baseUrl + users + "/signin";
    }
}
