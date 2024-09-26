package fiap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;
import fiap.controller.AuthController;

public class App implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final AuthController authController = new AuthController();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        return authController.handleRequest(input, context);
    }
}
