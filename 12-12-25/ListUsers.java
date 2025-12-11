
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimplePostJava8 {
    public static void main(String[] args) {
        // --- Configurable variables ---
        String urlString = "https://httpbin.org/post";    // Target URL
        String jsonBody = "{\"message\":\"Hello from Java!\",\"count\":1}";
        String contentType = "application/json";

        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10_000); // 10 seconds
            conn.setReadTimeout(10_000);    // 10 seconds

            // Headers
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Accept", "application/json");

            // Enable output for POST body
            conn.setDoOutput(true);

            // Write JSON body
            byte[] out = jsonBody.getBytes(StandardCharsets.UTF_8);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }

            // Read response (choose stream based on status code)
            int status = conn.getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream(),
                    StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append('\n');
            }
            reader.close();

            System.out.println("Status: " + status);
            System.out.println("Body:");
            System.out.println(result.toString());
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        } finally {
            if (conn != null) conn.disconnect();
        }
    }
}
