#!/bin/bash

echo "========================================"
echo "E-commerce Microservices Deployment"
echo "========================================"
echo

# Check prerequisites
echo "Checking prerequisites..."
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed"
    echo "Please install Java 17 or higher"
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed"
    echo "Please install Maven"
    exit 1
fi

echo "Prerequisites check passed!"
echo

# Build Product Service
echo "Building Product Service..."
cd product-service
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to build Product Service"
    exit 1
fi
echo "Product Service built successfully!"
echo

# Build Order Service
echo "Building Order Service..."
cd ../order-service
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to build Order Service"
    exit 1
fi
echo "Order Service built successfully!"
echo

# Start services
echo "Starting Product Service..."
nohup java -jar target/product-service-1.0.0.jar > product-service.log 2>&1 &
PRODUCT_PID=$!
echo "Product Service started with PID: $PRODUCT_PID"

sleep 10

echo "Starting Order Service..."
nohup java -jar target/order-service-1.0.0.jar > order-service.log 2>&1 &
ORDER_PID=$!
echo "Order Service started with PID: $ORDER_PID"

sleep 10

echo
echo "========================================"
echo "Deployment Complete!"
echo "========================================"
echo
echo "Services are running..."
echo "Product Service: http://localhost:8081"
echo "Order Service: http://localhost:8082"
echo
echo "Health Check URLs:"
echo "Product Health: http://localhost:8081/actuator/health"
echo "Order Health: http://localhost:8082/actuator/health"
echo
echo "API Endpoints:"
echo "Products: http://localhost:8081/api/v1/products"
echo "Orders: http://localhost:8082/api/v1/orders"
echo
echo "Log files:"
echo "Product Service: product-service.log"
echo "Order Service: order-service.log"
echo
echo "PIDs:"
echo "Product Service: $PRODUCT_PID"
echo "Order Service: $ORDER_PID"
echo

# Save PIDs to file for stopping
echo "$PRODUCT_PID" > product-service.pid
echo "$ORDER_PID" > order-service.pid

echo "Deployment completed successfully!"
