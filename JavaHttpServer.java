import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class JavaHttpServer {
  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
    server.createContext("/", new MyHandler());

    server.start();
    System.out.println("Server started on port 8000");
  }

  static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      try {
        String method = exchange.getRequestMethod();
        String name = "Guest";
        String response;

        if ("GET".equals(method)) {
          // Handle GET with query param
          String query = exchange.getRequestURI().getQuery();
          if (query != null && query.contains("name=")) {
            name = query.split("=")[1];
          }
          response = "Hello, " + name + "! (via GET)";
        } else if ("POST".equals(method)) {
          // Handle POST by reading body (assume simple "name=xxx" format)
          InputStream is = exchange.getRequestBody();
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          String body = br.readLine(); // Read single line for simplicity
          if (body != null && body.contains("name=")) {
            name = body.split("=")[1];
          }
          response = "Hello, " + name + "! (via POST)";
        } else {
          // Unsupported method
          response = "Method not allowed.";
          exchange.sendResponseHeaders(405, response.getBytes().length);
          OutputStream os = exchange.getResponseBody();
          os.write(response.getBytes());
          os.close();
          return;
        }

        // Send success response
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

      } catch (Exception e) {
        String errorResponse = "Internal server error.";
        exchange.sendResponseHeaders(500, errorResponse.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(errorResponse.getBytes());
        os.close();
      }
    }
  }
}
