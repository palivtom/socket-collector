pipeline {
    agent none
    stages {
        stage ('Build') {
            agent {
                docker {
                    image 'gradle:jdk17'
                }
            }
            steps {
                sh 'gradle bootJar'
            }
        }

        stage ('Deploy') {
            agent any
            steps {
                sh 'docker compose up --build -d'
                sh 'docker compose ps'
            }
        }
    }
}