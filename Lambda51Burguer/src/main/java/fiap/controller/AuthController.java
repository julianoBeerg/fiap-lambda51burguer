package fiap.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import fiap.Service.AuthService;
import fiap.request.LoginRequest;
import fiap.response.LoginResponse;
import java.util.HashMap;
import java.util.Map;

public class AuthController implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    private final AuthService authService = new AuthService();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, Object> response = new HashMap<>();
        response.put("headers", headers);

        try {
            String bodyString = (String) input.get("body");
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(bodyString, LoginRequest.class);
            String path = input.get("path").toString();

            LoginResponse loginResponse = authService.authenticate(loginRequest, path);
            response.put("statusCode", 200);
            response.put("body", objectMapper.writeValueAsString(loginResponse));

        } catch (IllegalArgumentException e) {
            response.put("statusCode", 400);
            response.put("body", "{\"status\": 400, \"message\": \"" + e.getMessage() + "\", \"timestamp\": " + System.currentTimeMillis() + "}");
        } catch (RuntimeException e) {
            response.put("statusCode", 404);
            response.put("body", "{\"status\": 404, \"message\": \"" + e.getMessage() + "\", \"timestamp\": " + System.currentTimeMillis() + "}");
        } catch (Exception e) {
            response.put("statusCode", 500);
            response.put("body", "{\"status\": 500, \"message\": \"Erro interno: " + e.getMessage() + "\", \"timestamp\": " + System.currentTimeMillis() + "}");
        }

        return response;
    }
}
