pipeline {
    agent none

    stages {
        stage ('Build') {
            agent {
                docker {
                    image 'openjdk:11'
                    args '-v "$PWD":/app'
                    reuseNode true
                }
            }
            steps {
                sh './gradlew bootjar'
            }
        }

        stage ('Deploy') {
            steps {
                sh 'docker compose up -d'
                sh 'docker compose ps'
            }
        }
    }
}