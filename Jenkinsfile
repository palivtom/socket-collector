pipeline {
    agent none
    stages {
        stage ('Build') {
            agent {
                docker {
                    image 'openjdk:17'
                    args '-v $PWD:/app'
                }
            }
            steps {
                sh './gradlew bootjar'
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