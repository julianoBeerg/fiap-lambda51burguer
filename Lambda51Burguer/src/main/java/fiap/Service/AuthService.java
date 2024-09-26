package fiap.Service;
import fiap.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.model.User;
import fiap.request.LoginRequest;
import fiap.utils.CpfValidator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {
    //todo jnunes utilizar esse link depois
//    private final String apiUrl = "https://2ce1bb3d-856b-4e5e-81fd-0c1b668bc4d7.mock.pstmn.io/profile?cpf=";
    private final String apiUrl = "https://2ce1bb3d-856b-4e5e-81fd-0c1b668bc4d7.mock.pstmn.io/profile";

    public LoginResponse authenticate(LoginRequest request, String path) throws Exception {
        String cpf = request.getCpf();

        if (!CpfValidator.isValid(cpf)) {
            throw new IllegalArgumentException("CPF '" + cpf + "' inválido!");
        }

        //todo jnunes passar cpf como parametro
        URL url = new URL(apiUrl);
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
            User user = objectMapper.readValue(content.toString(), User.class);

            return new LoginResponse(user.getId(), user.getName(),user.getCpf(),user.getEmail(),  true, path.contains("admin"));

        } else if (responseCode == 404) {
            throw new RuntimeException("Cliente não encontrado");
        } else {
            throw new RuntimeException("Erro ao consultar a API");
        }
    }
}
