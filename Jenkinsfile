pipeline {
    agent none
    stages {
        stage ('Build') {
            agent {
                docker {
                    image 'gradle:jdk17-alpine'
                    args '-u 0:0'
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