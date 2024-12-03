package fiap.Service;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import fiap.model.User;
import fiap.repository.UserRepository;
import fiap.response.LoginResponse;
import fiap.request.LoginRequest;
import fiap.utils.CpfValidator;
import fiap.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.utils.SecretsManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthService {
    private final String userPoolId;
    private final String clientId;

    private final AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.defaultClient();
    private final AWSSecretsManager secretsManager = AWSSecretsManagerClientBuilder.defaultClient();
    private final JwtUtil jwtUtil = new JwtUtil();
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        Map<String, String> secretValues = getSecretValues();
        this.userPoolId = secretValues.get("UserPoolId");
        this.clientId = secretValues.get("UserPoolClientId");
    }

    public LoginResponse authenticate(LoginRequest request, String path) throws Exception {

        String cpf = request.getCpf();
        String nome = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();

        boolean isAdminPath = path.contains("admin");

        if (!isAdminPath && (cpf == null || cpf.isEmpty())) {
            String token = jwtUtil.generateAnonymousToken();
            return new LoginResponse(token);
        }else if (isAdminPath && (cpf == null || cpf.isEmpty())){
            throw new IllegalArgumentException("O usuário administrador não pode ser genérico");
        }


        if (!CpfValidator.isValid(cpf)) {
            throw new IllegalArgumentException("CPF '" + cpf + "' inválido!");
        }
        cpf = cpf.replaceAll("[^0-9]", "");
        User user = userRepository.findByCpf(cpf);
        if (user == null) {
            if (nome != null && email != null && !nome.isEmpty() && !email.isEmpty()) {
                user = userRepository.createUser(cpf, nome, email);
            } else {
                throw new RuntimeException("Usuário não encontrado e informações de registro incompletas");
            }
        }

        try {
            AdminGetUserRequest adminGetUserRequest = new AdminGetUserRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(cpf);

            AdminGetUserResult adminGetUserResult = cognitoClient.adminGetUser(adminGetUserRequest);

            String userEmail = getAttributeValue(adminGetUserResult.getUserAttributes(), "email");
            String userNameAttr = getAttributeValue(adminGetUserResult.getUserAttributes(), "name");
            boolean isAdmin = checkUserIsAdmin(cpf);

            if (isAdminPath && !isAdmin && (email.isEmpty() || nome.isEmpty() || password.isEmpty())) {
                throw new IllegalAccessException("Acesso negado: usuário não tem permissão para acessar a API admin.");
            }

            String token = jwtUtil.generateToken(
                    user.getId(),
                    userNameAttr,
                    cpf,
                    userEmail,
                    isAdmin
            );

            return new LoginResponse(token);

        } catch (UserNotFoundException | ResourceNotFoundException e) {

            signUpUser(cpf, nome, email, password, isAdminPath);
            return authenticate(request, path);
        }
    }

    private void signUpUser(String cpf, String nome, String email, String password, boolean isAdmin) {
        SignUpRequest signUpRequest = new SignUpRequest()
                .withClientId(clientId)
                .withUsername(cpf)
                .withPassword(isAdmin ? password : "genericUserPassword")
                .withUserAttributes(
                        new AttributeType().withName("name").withValue(nome),
                        new AttributeType().withName("email").withValue(email)
                );

        cognitoClient.signUp(signUpRequest);

        AdminConfirmSignUpRequest confirmSignUpRequest = new AdminConfirmSignUpRequest()
                .withUserPoolId(userPoolId)
                .withUsername(cpf);
        cognitoClient.adminConfirmSignUp(confirmSignUpRequest);

        AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
                .withUserPoolId(userPoolId)
                .withUsername(cpf)
                .withGroupName(isAdmin ? "Admin" : "User");

        cognitoClient.adminAddUserToGroup(addUserToGroupRequest);
    }

    private boolean checkUserIsAdmin(String cpf) {
        List<GroupType> groups = cognitoClient.adminListGroupsForUser(new AdminListGroupsForUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(cpf)).getGroups();

        return groups.stream().anyMatch(group -> group.getGroupName().equals("Admin"));
    }

    private String getAttributeValue(List<AttributeType> attributes, String attributeName) {
        Optional<AttributeType> attribute = attributes.stream()
                .filter(attr -> attr.getName().equals(attributeName))
                .findFirst();
        return attribute.map(AttributeType::getValue).orElse("");
    }

    private Map<String, String> getSecretValues() {
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId("CognitoIds");
        GetSecretValueResult getSecretValueResult = secretsManager.getSecretValue(getSecretValueRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(getSecretValueResult.getSecretString(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao obter valores do Secrets Manager", e);
        }
    }
}
