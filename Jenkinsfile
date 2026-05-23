pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK21'
    }

    environment {
        IMAGE_NAME = "expense-tracker"
        CONTAINER_NAME = "expense-tracker"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Padmanabha187/open-ended-1-.git',
                    credentialsId: 'github-token'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Remove Old Container') {
            steps {
                sh '''
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true
                '''
            }
        }

        stage('Remove Old Image') {
            steps {
                sh '''
                docker rmi -f ${IMAGE_NAME} || true
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build -t ${IMAGE_NAME}:latest .
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                sh '''
                docker run -dit \
                  -p 9090:8080 \
                  --name ${CONTAINER_NAME} \
                  ${IMAGE_NAME}:latest
                '''
            }
        }

        stage('Check Running Container') {
            steps {
                sh 'docker ps -a'
            }
        }
    }

    post {

        success {
            emailext (
                subject: "SUCCESS: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
Build succeeded!

Application URL:
http://localhost:9090

Build URL:
${BUILD_URL}
""",
                to: "padmanabha462@gmail.com"
            )
        }

        failure {
            emailext (
                subject: "FAILED: ${JOB_NAME} #${BUILD_NUMBER}",
                body: """
Build failed!

Check:
${BUILD_URL}
""",
                to: "padmanabha462@gmail.com"
            )
        }
    }
}
