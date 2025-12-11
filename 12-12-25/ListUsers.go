
package main

import (
    "bufio"
    "context"
    "fmt"
    "io"
    "net/http"
    "os"
    "strings"
    "time"
)

// loadEnvFile parses a simple .env file (KEY=VALUE) and returns a map.
// - Ignores blank lines and comments that start with '#'
// - Trims spaces around keys and values
func loadEnvFile(filename string) (map[string]string, error) {
    f, err := os.Open(filename)
    if err != nil {
        return nil, err
    }
    defer f.Close()

    env := make(map[string]string)
    sc := bufio.NewScanner(f)
    for sc.Scan() {
        line := strings.TrimSpace(sc.Text())
        if line == "" || strings.HasPrefix(line, "#") {
            continue
        }
        parts := strings.SplitN(line, "=", 2)
        if len(parts) != 2 {
            // Skip malformed lines
            continue
        }
        key := strings.TrimSpace(parts[0])
        val := strings.TrimSpace(parts[1])

        // Optional: strip surrounding quotes
        val = strings.Trim(val, `"'`)

        env[key] = val
    }
    return env, sc.Err()
}

func main() {
    // Load .env file
    env, err := loadEnvFile(".env")
    if err != nil {
        fmt.Println("Failed to read .env:", err)
        return
    }

    // Get variables from .env
    url := env["URL"]
    secret := env["SECRET"]
    if url == "" {
        fmt.Println("URL not set in .env (expected line like: URL=https://example.com)")
        return
    }

    // HTTP client with timeout
    client := &http.Client{
        Timeout: 10 * time.Second,
    }

    // Context with timeout
    ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
    defer cancel()

    // Build request
    req, err := http.NewRequestWithContext(ctx, http.MethodGet, url, nil)
    if err != nil {
        fmt.Println("Request build error:", err)
        return
    }
    req.Header.Set("Accept", "application/json")
    req.Header.Set("User-Agent", "Go-HttpClient/1.0")

    // Execute
    resp, err := client.Do(req)
    if err != nil {
        fmt.Println("Request error:", err)
        return
    }
    defer resp.Body.Close()

    // Print status and body
    fmt.Println("Status:", resp.Status)
    body, err := io.ReadAll(resp.Body)
    if err != nil {
        fmt.Println("Read error:", err)
        return
    }
    fmt.Println("Body:")
    fmt.Println(string(body))
