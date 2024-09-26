package fiap.response;

public class LoginResponse {

    private final String token;


    public LoginResponse() {

        this.token = "";

    }

    public LoginResponse(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }
}
