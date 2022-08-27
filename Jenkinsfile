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
                sh 'sudo chmod +x ./gradlew'
                sh 'sudo ./gradlew'
                sh 'sudo ./gradlew bootJar'
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