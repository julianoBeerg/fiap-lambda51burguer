package fiap.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String cpf;

    public User() {}

    public User(int id, String username, String email, String cpf) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.cpf = cpf;
    }

    public User( String username, String email, String cpf) {
    }

    public User(String username, String name, String email, String cpf) {
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }
}

