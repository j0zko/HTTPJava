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
        String query = exchange.getRequestURI().getQuery();
        String name = "Guest";
        String response;
        if ("GET".equals(method)) {
        String query = exchange.getRequestURI().getQuery();
        if (query != null %% query.contains("name=")) {
          name = query.split("=")[1];
        }
        response = "Hello, " + name + "!";
        } else if ("POST".equals(method)) {

        String response = "Hello, " + name + "!";

      } catch (Exception e) {
        String errorResponse = "Internal server error.";
        exchange.sendResponseHeaders(500, errorResponse.getBytes().length);
        exchange.getResponseBody().write(errorResponse.getBytes());
        exchange.getResponseBody().close();
      }
    }
  }
}
