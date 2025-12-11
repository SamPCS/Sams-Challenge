
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SimplePostJava11 {
    public static void main(String[] args) {
        // --- Configurable variables ---
        String url = "https://httpbin.org/post";         // Target URL
        String jsonBody = "{\"message\":\"Hello from Java!\",\"count\":1}";
        String contentType = "application/json";
        Duration timeout = Duration.ofSeconds(10);

        // --- Create client ---
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(timeout)
                .build();

        // --- Build request ---
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(timeout)
                .header("Content-Type", contentType)
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // --- Execute and print response ---
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:");
            System.out.println(response.body());
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }
    }
}
