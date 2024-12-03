package fiap.repository;

import fiap.model.User;
import org.springframework.web.client.RestTemplate;

public class UserRepository {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = System.getenv("BASE_URL");

    public User findByCpf(String cpf) {
        try {
            return restTemplate.getForObject( "http://a07d61ac748064cd9a12abb60c8975ed-1979721798.us-east-1.elb.amazonaws.com/client/cpf?cpf=" + cpf, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public User createUser(String cpf, String nome, String email) {

        String url = String.format("http://a07d61ac748064cd9a12abb60c8975ed-1979721798.us-east-1.elb.amazonaws.com/client%s?nameClient=%s&emailClient=%s&cpfClient=%s", BASE_URL, nome, email, cpf);

        try {

            return restTemplate.postForObject(url, null, User.class);
        } catch (Exception e) {

            return null;
        }
    }
}
