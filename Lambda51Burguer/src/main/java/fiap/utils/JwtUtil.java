package fiap.utils;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = getSecret();
    Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3);

    private static String getSecret() {
        AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId("jwtSecretKey");
        GetSecretValueResult getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest);

        return getSecretValueResult.getSecretString();
    }

    public String generateToken(int id, String name, String cpf, String email, boolean isAdmin) {
        return JWT.create()
                .withClaim("id",id)
                .withClaim("cpf",cpf)
                .withClaim("name",name)
                .withClaim("email",email)
                .withClaim("isAdmin", isAdmin)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }
}
