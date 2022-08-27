pipeline {
    agent none
    stages {
        stage ('Build') {
            agent {
                docker {
                    image 'openjdk:17'
                }
            }
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew'
                sh './gradlew bootJar'
            }
        }

        stage ('Deploy') {
            steps {
                sh 'docker compose up --build -d'
                sh 'docker compose ps'
            }
        }
    }
}