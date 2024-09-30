package fiap.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtil {
    private final String secretKey = SecretsManager.getSecret("jwtSecretKey");
    private final Algorithm algorithm = Algorithm.HMAC256(secretKey);
    private final Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3); // 3 horas

    public String generateToken(int id, String name, String cpf, String email, boolean isAdmin) {
        return JWT.create()
                .withClaim("id", id)
                .withClaim("cpf", cpf)
                .withClaim("name", name)
                .withClaim("email", email)
                .withClaim("isAdmin", isAdmin)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }

    public String generateAnonymousToken() {
        return JWT.create()
                .withClaim("isAdmin", false)
                .withClaim("anonymous", true)
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }
}
