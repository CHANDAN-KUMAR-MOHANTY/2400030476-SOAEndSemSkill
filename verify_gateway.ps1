# Verification Script for API Gateway Path Rewriting
Write-Host "==========================================================" -ForegroundColor Cyan
Write-Host " Banking Platform API Gateway - Path Rewriting Verification" -ForegroundColor Cyan
Write-Host "==========================================================" -ForegroundColor Cyan

$gatewayUrl = "http://localhost:8080/accounts"
$accountServiceUrl = "http://localhost:8081/internal/accounts"

Write-Host "`n[Step 1] Checking Direct Target Service (Port 8081)..." -ForegroundColor Yellow
try {
    $directResp = Invoke-RestMethod -Uri $accountServiceUrl -Method GET -TimeoutSec 3
    Write-Host "Direct Service OK! Response:" -ForegroundColor Green
    $directResp | ConvertTo-Json -Depth 3 | Write-Host
} catch {
    Write-Host "Could not connect to Account Service directly on 8081: $_" -ForegroundColor Red
}

Write-Host "`n[Step 2] Testing Forwarding via Public Gateway: http://localhost:8080/accounts/" -ForegroundColor Yellow
try {
    $gwResp1 = Invoke-RestMethod -Uri "http://localhost:8080/accounts/" -Method GET -TimeoutSec 3
    Write-Host "Gateway Forwarded Successfully!" -ForegroundColor Green
    $gwResp1 | ConvertTo-Json -Depth 3 | Write-Host
} catch {
    Write-Host "Gateway request to /accounts/ failed: $_" -ForegroundColor Red
}

Write-Host "`n[Step 3] Testing Forwarding without trailing slash: http://localhost:8080/accounts" -ForegroundColor Yellow
try {
    $gwResp2 = Invoke-RestMethod -Uri "http://localhost:8080/accounts" -Method GET -TimeoutSec 3
    Write-Host "Gateway Forwarded Successfully!" -ForegroundColor Green
    $gwResp2 | ConvertTo-Json -Depth 3 | Write-Host
} catch {
    Write-Host "Gateway request to /accounts failed: $_" -ForegroundColor Red
}

Write-Host "`n==========================================================" -ForegroundColor Cyan
Write-Host " Verification Completed" -ForegroundColor Cyan
Write-Host "==========================================================" -ForegroundColor Cyan
