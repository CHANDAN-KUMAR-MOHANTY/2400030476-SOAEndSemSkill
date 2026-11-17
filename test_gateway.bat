@echo off
REM =========================================================================================
REM  Banking Platform - API Gateway Path Rewriting & StripPrefix Verification Suite
REM  Validates Task 1, Task 2, and Task 3
REM =========================================================================================

set GATEWAY_URL=http://localhost:8080
set TARGET_SERVICE_URL=http://localhost:8081

echo =========================================================================
echo [TASK 1] Testing Target Account Service Directly (Port 8081)
echo Internal route exposed at: %TARGET_SERVICE_URL%/internal/accounts
echo =========================================================================
curl -s -X GET "%TARGET_SERVICE_URL%/internal/accounts" -H "Accept: application/json"
echo.
echo.

echo =========================================================================
echo [TASK 2 & 3] Testing Gateway Path Rewriting (Port 8080)
echo Client calls public Gateway endpoint at: %GATEWAY_URL%/accounts/
echo Gateway rewrites path to: /internal/accounts/ and forwards to 8081
echo =========================================================================
curl -s -X GET "%GATEWAY_URL%/accounts/" -H "Accept: application/json"
echo.
echo.

echo =========================================================================
echo [TASK 3] Testing Gateway forwarding without trailing slash: %GATEWAY_URL%/accounts
echo =========================================================================
curl -s -X GET "%GATEWAY_URL%/accounts" -H "Accept: application/json"
echo.
echo.

echo =========================================================================
echo [TASK 3] Testing Gateway forwarding with account ID: %GATEWAY_URL%/accounts/ACC001
echo =========================================================================
curl -s -X GET "%GATEWAY_URL%/accounts/ACC001" -H "Accept: application/json"
echo.
echo.

echo =========================================================================
echo Verification Complete!
echo Look at "receivedPath" in the output above to confirm the backend received the modified path.
echo =========================================================================
pause
