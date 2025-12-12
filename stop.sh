#!/bin/bash

echo "========================================"
echo "Stopping E-commerce Microservices"
echo "========================================"
echo

# Stop services using saved PIDs
if [ -f "product-service.pid" ]; then
    PRODUCT_PID=$(cat product-service.pid)
    echo "Stopping Product Service (PID: $PRODUCT_PID)..."
    kill $PRODUCT_PID 2>/dev/null
    rm product-service.pid
else
    echo "Product Service PID file not found, searching for process..."
    pkill -f "product-service-1.0.0.jar"
fi

if [ -f "order-service.pid" ]; then
    ORDER_PID=$(cat order-service.pid)
    echo "Stopping Order Service (PID: $ORDER_PID)..."
    kill $ORDER_PID 2>/dev/null
    rm order-service.pid
else
    echo "Order Service PID file not found, searching for process..."
    pkill -f "order-service-1.0.0.jar"
fi

# Wait for processes to stop
sleep 3

# Force kill if still running
echo "Checking for remaining processes..."
pkill -9 -f "product-service-1.0.0.jar" 2>/dev/null
pkill -9 -f "order-service-1.0.0.jar" 2>/dev/null

echo "All services stopped!"
