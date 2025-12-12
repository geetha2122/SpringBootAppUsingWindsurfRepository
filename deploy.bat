@echo off
echo ========================================
echo E-commerce Microservices Deployment
echo ========================================
echo.

echo Checking prerequisites...
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven
    pause
    exit /b 1
)

echo Prerequisites check passed!
echo.

echo Building Product Service...
cd product-service
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Failed to build Product Service
    pause
    exit /b 1
)
echo Product Service built successfully!
echo.

echo Building Order Service...
cd ..\order-service
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Failed to build Order Service
    pause
    exit /b 1
)
echo Order Service built successfully!
echo.

echo Starting Product Service...
start "Product Service" cmd /k "java -jar target\product-service-1.0.0.jar"
timeout /t 10 /nobreak >nul

echo Starting Order Service...
start "Order Service" cmd /k "java -jar target\order-service-1.0.0.jar"
timeout /t 10 /nobreak >nul

echo.
echo ========================================
echo Deployment Complete!
echo ========================================
echo.
echo Services are starting up...
echo Product Service: http://localhost:8081
echo Order Service: http://localhost:8082
echo.
echo Health Check URLs:
echo Product Health: http://localhost:8081/actuator/health
echo Order Health: http://localhost:8082/actuator/health
echo.
echo API Endpoints:
echo Products: http://localhost:8081/api/v1/products
echo Orders: http://localhost:8082/api/v1/orders
echo.
echo Press any key to exit...
pause >nul
