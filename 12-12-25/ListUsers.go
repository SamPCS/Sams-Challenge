
package main

import (
    "fmt"
    "io"
    "net/http"
)

func main() {
    // The URL you want to request
    url := "https://httpbin.org/get"

    // Perform a simple GET
    resp, err := http.Get(url)
    if err != nil {
        fmt.Println("Request error:", err)
        return
    }
    defer resp.Body.Close()

    // Check HTTP status code
    if resp.StatusCode != http.StatusOK {
        fmt.Printf("Unexpected status: %s\n", resp.Status)
        return
    }

    // Read and print the response body
    body, err := io.ReadAll(resp.Body)
    if err != nil {
        fmt.Println("Read error:", err)
        return
    }

    fmt.Println("Response:")
    fmt.Println(string(body))
}
