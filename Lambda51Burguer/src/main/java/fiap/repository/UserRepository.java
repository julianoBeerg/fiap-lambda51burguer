package fiap.repository;

import fiap.model.User;
import org.springframework.web.client.RestTemplate;

public class UserRepository {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://aa4b76e56e8044a098a1882f19900bc0-1394836592.us-east-1.elb.amazonaws.com/client";

    public User findByCpf(String cpf) {
        try {
            return restTemplate.getForObject(BASE_URL + "/cpf?cpf=" + cpf, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public User createUser(String cpf, String nome, String email) {

        String url = String.format("%s?nameClient=%s&emailClient=%s&cpfClient=%s", BASE_URL, nome, email, cpf);

        try {

            return restTemplate.postForObject(url, null, User.class);
        } catch (Exception e) {

            return null;
        }
    }
}
