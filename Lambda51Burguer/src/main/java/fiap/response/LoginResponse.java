package fiap.response;

public class LoginResponse {
    private final int id;
    private final String nome;
    private final String cpf;
    private final String email;
    private final boolean isAuthorized;
    private final boolean isAdmin;
    private final String token;


    public LoginResponse() {
        this.id = 0;
        this.nome = "";
        this.cpf = "";
        this.email = "";
        this.isAuthorized = false;
        this.isAdmin = false;
        this.token = "";

    }

    public LoginResponse(int id, String nome, String cpf, String email, boolean isAuthorized, boolean isAdmin, String token) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.isAuthorized = isAuthorized;
        this.isAdmin = isAdmin;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getToken() {
        return token;
    }
}
