package fiap;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

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




}
