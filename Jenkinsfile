pipeline {
    agent any

    environment {
        COMPOSE_FILE = 'SetUpSeleniumGridLatest.yml'
    }

    stages {

        stage('Start Selenium Grid') {
            steps {
                script {
                    // Windows ortamı için bat komutunu kullan
                    bat 'docker-compose -f SetUpSeleniumGridLatest.yml up -d'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Windows ortamı için bat komutunu kullan
                    bat 'mvn clean test'
                }
            }
        }

        stage('Tear Down') {
            steps {
                script {
                    // Windows ortamı için bat komutunu kullan
                    bat 'docker-compose down'
                }
            }
        }
    }
}
