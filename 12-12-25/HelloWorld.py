
import requests
import json
from dotenv import load_dotenv
import os

# --- Load .env and read SECRET ---
load_dotenv()                         # <-- loads variables from .env
secret = os.getenv("SECRET")          # <-- reads SECRET

# --- Configuration ---
url = "https://httpbin.org/post"
headers = {
    "Content-Type": "application/json",
    "Accept": "application/json",
    "Authorization": f"Bearer {secret}"  # use the secret in a header (example)
}
payload = {
    "message": "Hello from Python!",
    "count": 1
}

# --- Perform POST request ---
try:
    response = requests.post(url, headers=headers, data=json.dumps(payload))
    print("Status Code:", response.status_code)
    print("Response Body:", response.text)
except requests.exceptions.RequestException as e:
    print("Request failed:", e)
