package fiap.Service;
import fiap.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.model.User;
import fiap.request.LoginRequest;
import fiap.utils.CpfValidator;
import fiap.utils.JwtUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {
    //TODO jnunes alterar link
    private final String apiUrl = "https://2ce1bb3d-856b-4e5e-81fd-0c1b668bc4d7.mock.pstmn.io/profile";

    public LoginResponse authenticate(LoginRequest request, String path) throws Exception {
        String cpf = request.getCpf();
        String nome = request.getName();
        String email = request.getEmail();

        JwtUtil jwtUtil = new JwtUtil();

        if (cpf != null && !cpf.isEmpty() && (nome == null || nome.isEmpty()) && (email == null || email.isEmpty())) {
            if (!CpfValidator.isValid(cpf)) {
                throw new IllegalArgumentException("CPF '" + cpf + "' inválido!");
            }

            User user = getUserByCpf(cpf);
            if (user == null) {
                throw new RuntimeException("Cliente não encontrado");
            }

            String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getCpf(), user.getEmail(), path.contains("admin"));

            return new LoginResponse( token);
        }

        else if (cpf != null && !cpf.isEmpty() && (nome != null && !nome.isEmpty()) && (email != null && !email.isEmpty())) {
            if (!CpfValidator.isValid(cpf)) {
                throw new IllegalArgumentException("CPF '" + cpf + "' inválido!");
            }

            User user = getUserByCpf(cpf);
            if (user == null) {
                // TODO jnunes Registrar o usuário já que o CPF não existe
                user = registerUser(nome, email, cpf);

                String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getCpf(), user.getEmail(), path.contains("admin"));

                return new LoginResponse( token);
            }

            String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getCpf(), user.getEmail(), path.contains("admin"));

            return new LoginResponse( token);
        }

        else {
            String genericToken = jwtUtil.generateGenericToken();

            return new LoginResponse( genericToken);
        }
    }

    private User getUserByCpf(String cpf) throws Exception {
        //TODO jnunes inserir cpf na query
        String urlWithCpf = apiUrl;
        URL url = new URL(urlWithCpf);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(content.toString(), User.class);

        } else if (responseCode == 404) {
            return null;
        } else {
            throw new RuntimeException("Erro ao consultar a API");
        }
    }

    private User registerUser(String nome, String email, String cpf) {

        // jnunes, retornando um novo objeto User com ID fictício
        return new User(1, nome, email, cpf);
    }
}