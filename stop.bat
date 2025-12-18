@echo off
echo ========================================
echo Stopping E-commerce Microservices
echo ========================================
echo.

echo Stopping Product Service...
taskkill /f /im java.exe /fi "windowtitle eq Product Service*" >nul 2>&1

echo Stopping Order Service...
taskkill /f /im java.exe /fi "windowtitle eq Order Service*" >nul 2>&1

echo.
echo All services stopped!
echo.
pause
