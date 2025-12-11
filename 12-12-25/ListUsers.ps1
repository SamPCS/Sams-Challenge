# ==============================
# PowerShell POST Request Template
# ==============================

# --- Configuration Variables ---
#define this in env variables
$secret = "super secret"
$Uri        = "https://api.example.com/endpoint"   # API endpoint URL
$Headers    = @{                                   # HTTP headers
    "Content-Type"  = "application/json"
    "Authorization" = "Bearer YOUR_ACCESS_TOKEN"
}
$Body       = @{                                   # Request body (as a hashtable)
    "apikey" = $secret
} | ConvertTo-Json -Depth 10                       # Convert body to JSON

# --- Execute POST Request ---
try {
    $Response = Invoke-RestMethod -Uri $Uri -Method Post -Headers $Headers -Body $Body

    # --- Output Response ---
    Write-Host "Status: Success"
    Write-Host "Response:" ($Response | ConvertTo-Json -Depth 10)
}
catch {
    Write-Host "Status: Failed"
    Write-Host "Error:" $_.Exception.Message
}
