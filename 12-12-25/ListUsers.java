
import io.github.cdimascio.dotenv.Dotenv;
// ... other imports

public class SimplePostJava11 {
    public static void main(String[] args) {
        // Load .env and read SECRET
        String secret = Dotenv.load().get("SECRET"); // <-- one line to read from .env

        String url = "https://httpbin.org/post";
        String jsonBody = "{\"message\":\"Hello from Java!\"}";
        String contentType = "application/json";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", contentType)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + secret) // use the secret
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:\n" + response.body());
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }
    }
}
