package fiap.request;

public class LoginRequest {
    private String cpf;

    public LoginRequest() {}

    public LoginRequest(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
