pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    // Clean and build the project using Maven
                    sh 'mvn clean install'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // Run unit tests
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Deploy to server - replace with your deployment command
                    echo 'Deploying to server...'
                    // Example: sh 'scp target/your-app.jar user@server:/path/to/deploy'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
            // Email notification for success
            mail to: 'your-email@example.com', 
                 subject: 'Build Success: ${env.JOB_NAME} [${env.BUILD_NUMBER}]', 
                 body: "The build was successful.\nJob: ${env.JOB_NAME}\nBuild Number: ${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}"
        }
        failure {
            echo 'Pipeline failed.'
            // Email notification for failure
            mail to: 'your-email@example.com', 
                 subject: 'Build Failure: ${env.JOB_NAME} [${env.BUILD_NUMBER}]', 
                 body: "The build failed.\nJob: ${env.JOB_NAME}\nBuild Number: ${env.BUILD_NUMBER}\nURL: ${env.BUILD_URL}"
        }
    }
}
