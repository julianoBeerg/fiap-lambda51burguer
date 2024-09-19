package fiap;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.request.LoginRequest;
import fiap.response.LoginResponse;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent request, final Context context) {
        var logger = context.getLogger();
        try {




            logger.log("Request received - " + request.getBody());

            var loginRequest = objectMapper.readValue(request.getBody(), LoginRequest.class);

            boolean isAuthorized = loginRequest.nome().equalsIgnoreCase("admin")
                    && loginRequest.cpf().equalsIgnoreCase("admin");

            var loginResponse = new LoginResponse(isAuthorized);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(objectMapper.writeValueAsString(loginResponse))
                    .withIsBase64Encoded(false);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }

    }
}
