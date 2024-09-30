package fiap.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

public class SecretsManager {
    public static String getSecret(String secret) {
        AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.standard().build();
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secret);
        GetSecretValueResult getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest);

        return getSecretValueResult.getSecretString();
    }
}
