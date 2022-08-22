pipeline {
    agent none
    stages {
        stage ('Build') {
            agent {
                docker {
                    image 'gradle:jdk17-alpine'
                }
            }
            steps {
                sh 'gradle bootJar'
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