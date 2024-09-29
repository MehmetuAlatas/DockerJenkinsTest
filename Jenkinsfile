pipeline {
    agent any
    stages {
        stage('Set Permissions') {
            steps {
                sh 'chmod 777 ./mvnw' // mvnw dosyasına tam izin veriliyor
            }
        }
        stage('Build and Test') {
            steps {
                sh './mvnw test' // mvnw dosyası ile testler çalıştırılıyor
            }
        }
    }
}