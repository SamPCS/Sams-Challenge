
import requests
import json

# --- Configuration ---
url = "https://httpbin.org/post"  # Replace with your API endpoint
headers = {
    "Content-Type": "application/json",
    "Accept": "application/json"
}
payload = {
    "message": "Hello from Python!",
    "count": 1
}

# --- Perform POST request ---
try:
    response = requests.post(url, headers=headers, data=json.dumps(payload))

    # --- Output ---
    print("Status Code:", response.status_code)
    print("Response Body:", response.text)
except requests.exceptions.RequestException as e:
    print("Request failed:", e)
