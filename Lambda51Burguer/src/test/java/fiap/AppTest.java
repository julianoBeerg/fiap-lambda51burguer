package fiap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import fiap.request.LoginRequest;
import fiap.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.IOException;

class AppTest {

  private App app;
  private Context mockContext;
  private LambdaLogger mockLogger;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    app = new App();
    mockContext = Mockito.mock(Context.class);
    mockLogger = Mockito.mock(LambdaLogger.class);
    objectMapper = new ObjectMapper();

    // Configure the mock context to return the mock logger
    Mockito.when(mockContext.getLogger()).thenReturn(mockLogger);
  }

  @Test
  void testHandleRequestAuthorized() throws IOException {
    // Mock request body with valid credentials
    var loginRequest = new LoginRequest("admin", "admin");

    APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent()
            .withBody(objectMapper.writeValueAsString(loginRequest));

    // Call handleRequest method
    APIGatewayProxyResponseEvent responseEvent = app.handleRequest(requestEvent, mockContext);

    // Validate response
    assertNotNull(responseEvent);
    assertEquals(200, responseEvent.getStatusCode());
    assertEquals("{\"isAuthorized\":true}", responseEvent.getBody());
    assertFalse(responseEvent.getIsBase64Encoded());
  }

  @Test
  void testHandleRequestUnauthorized() throws IOException {
    // Mock request body with invalid credentials
    var loginRequest = new LoginRequest("user", "1234");

    APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent()
            .withBody(objectMapper.writeValueAsString(loginRequest));

    // Call handleRequest method
    APIGatewayProxyResponseEvent responseEvent = app.handleRequest(requestEvent, mockContext);

    // Validate response
    assertNotNull(responseEvent);
    assertEquals(200, responseEvent.getStatusCode());
    assertEquals("{\"isAuthorized\":false}", responseEvent.getBody());
    assertFalse(responseEvent.getIsBase64Encoded());
  }

  @Test
  void testHandleRequestInvalidBody() {
    // Mock request body with invalid JSON format
    APIGatewayProxyRequestEvent requestEvent = new APIGatewayProxyRequestEvent()
            .withBody("invalid json");

    // Expect IOException to be thrown
    assertThrows(RuntimeException.class, () -> {
      app.handleRequest(requestEvent, mockContext);
    });
  }
}
