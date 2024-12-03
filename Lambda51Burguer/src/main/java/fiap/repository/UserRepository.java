package fiap.repository;

import fiap.model.User;
import org.springframework.web.client.RestTemplate;

public class UserRepository {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = System.getenv("BASE_URL");

    public User findByCpf(String cpf) {
        try {
            return restTemplate.getForObject(baseUrl + "/client/cpf?cpf=" + cpf, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public User createUser(String cpf, String nome, String email) {

        String url = String.format(baseUrl + "%s/client?nameClient=%s&emailClient=%s&cpfClient=%s", nome, email, cpf);

        try {

            return restTemplate.postForObject(url, null, User.class);
        } catch (Exception e) {

            return null;
        }
    }
}
