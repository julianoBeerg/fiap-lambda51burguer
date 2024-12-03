package fiap.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.AWSSecretsManagerException;

public class SecretsManager {

    public static String getSecret(String secret) {
        try {
            AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.standard().build();
            GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secret);
            GetSecretValueResult getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest);

            return getSecretValueResult.getSecretString();
        } catch (ResourceNotFoundException e) {
            throw new SecretNotFoundException("O segredo '" + secret + "' não foi encontrado no Secrets Manager.", e);
        } catch (AWSSecretsManagerException e) {
            throw new SecretNotFoundException("Erro ao acessar o Secrets Manager: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new SecretNotFoundException("Erro inesperado ao acessar o Secrets Manager: " + e.getMessage(), e);
        }
    }
}
