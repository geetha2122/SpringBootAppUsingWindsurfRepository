pipeline {
    agent any

    environment {
        AWS_REGION = 'us-east-1'
        EB_APPLICATION_NAME = 'ecommerce-microservices'
        EB_ENVIRONMENT_NAME = 'EcommerceMicroservices-env'
        // Requires 'aws-credentials' (Username/Password type) in Jenkins:
        // Username = AWS Access Key ID, Password = AWS Secret Access Key
        AWS_CREDENTIALS_ID = 'aws-credentials'
        // Requires 'aws-account-id' (Secret Text type) in Jenkins
        AWS_ACCOUNT_ID_CREDENTIAL_ID = 'aws-account-id' 
    }

    tools {
        // Assumes you have configured a Maven tool named 'Maven 3' in Global Tool Configuration
        maven 'Maven 3'
        // Assumes you have configured a JDK tool named 'JDK 17'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build & Push Docker Images') {
            steps {
                script {
                    withCredentials([
                        usernamePassword(credentialsId: env.AWS_CREDENTIALS_ID, usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: env.AWS_ACCOUNT_ID_CREDENTIAL_ID, variable: 'AWS_ACCOUNT_ID')
                    ]) {
                        
                        def registry = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                        def tag = "${env.BUILD_NUMBER}"
                        
                        // Login to ECR
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${registry}"
                        
                        // List of services to build
                        def services = ['product-service', 'order-service', 'employee-service', 'department-service']
                        
                        services.each { service ->
                            echo "Building and pushing ${service}..."
                            sh "docker build -t ${registry}/${service}:${tag} ./${service}"
                            sh "docker push ${registry}/${service}:${tag}"
                        }
                        
                        // Build and Push Nginx (requires custom context)
                        echo "Building and pushing Nginx..."
                        sh 'echo "FROM nginx:alpine" > nginx.Dockerfile'
                        sh 'echo "COPY nginx.conf /etc/nginx/nginx.conf" >> nginx.Dockerfile'
                        sh "docker build -f nginx.Dockerfile -t ${registry}/ecommerce-nginx:${tag} ."
                        sh "docker push ${registry}/ecommerce-nginx:${tag}"
                    }
                }
            }
        }

        stage('Deploy to Elastic Beanstalk') {
            steps {
                script {
                    withCredentials([
                        usernamePassword(credentialsId: env.AWS_CREDENTIALS_ID, usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY'),
                        string(credentialsId: env.AWS_ACCOUNT_ID_CREDENTIAL_ID, variable: 'AWS_ACCOUNT_ID')
                    ]) {
                        
                        def registry = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
                        def tag = "${env.BUILD_NUMBER}"
                        
                        // Generate docker-compose.yml for deployment
                        writeFile file: 'docker-compose.yml', text: """
version: '3.8'
services:
  product-service:
    image: ${registry}/product-service:${tag}
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    restart: always

  order-service:
    image: ${registry}/order-service:${tag}
    ports:
      - "8082:8082"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    restart: always

  employee-service:
    image: ${registry}/employee-service:${tag}
    ports:
      - "8083:8083"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    restart: always

  department-service:
    image: ${registry}/department-service:${tag}
    ports:
      - "8084:8084"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    restart: always

  nginx:
    image: ${registry}/ecommerce-nginx:${tag}
    ports:
      - "80:80"
    depends_on:
      - product-service
      - order-service
      - employee-service
      - department-service
    restart: always
"""
                        // Prepare deployment package
                        sh "zip deploy.zip docker-compose.yml"
                        
                        // Upload to S3 (Standard EB bucket naming convention)
                        def bucket = "elasticbeanstalk-${AWS_REGION}-${AWS_ACCOUNT_ID}"
                        def s3Key = "${EB_APPLICATION_NAME}/${tag}-deploy.zip"
                        
                        echo "Uploading deployment package to S3..."
                        sh "aws s3 cp deploy.zip s3://${bucket}/${s3Key}"
                        
                        echo "Creating application version..."
                        sh """
                            aws elasticbeanstalk create-application-version \
                                --application-name ${EB_APPLICATION_NAME} \
                                --version-label ${tag} \
                                --source-bundle S3Bucket=${bucket},S3Key=${s3Key} \
                                --region ${AWS_REGION}
                        """
                        
                        echo "Updating environment..."
                        sh """
                            aws elasticbeanstalk update-environment \
                                --application-name ${EB_APPLICATION_NAME} \
                                --environment-name ${EB_ENVIRONMENT_NAME} \
                                --version-label ${tag} \
                                --region ${AWS_REGION}
                        """
                    }
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
    }
}
