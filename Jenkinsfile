pipeline {
    agent any

    environment {
        COMPOSE_FILE = 'SetUpSeleniumGridLatest.yml'
    }

    stages {

        stage('Install Dependencies') {
            steps {
                script {
                    // Bağımlılıkların kurulu olup olmadığını kontrol et ve kur
                    sh '''
                        echo "Checking if Maven is installed..."
                        if ! mvn -version &> /dev/null; then
                            echo "Maven is not installed. Installing Maven..."
                            curl -L -o apache-maven-3.9.6-bin.tar.gz https://downloads.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
                            tar -xzf apache-maven-3.9.6-bin.tar.gz -C /opt/
                            export M2_HOME=/opt/apache-maven-3.9.6
                            export PATH=$M2_HOME/bin:$PATH
                            echo "Maven installed successfully!"
                        fi

                        echo "Checking if JDK is installed..."
                        if ! java -version &> /dev/null; then
                            echo "JDK is not installed. Installing JDK..."
                            apt-get update && apt-get install -y openjdk-11-jdk
                            export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
                            export PATH=$JAVA_HOME/bin:$PATH
                            echo "JDK installed successfully!"
                        fi

                        # Kurulumu doğrulamak için sürüm kontrolü yap
                        mvn -version
                        java -version
                    '''
                }
            }
        }

        stage('Start Selenium Grid') {
            steps {
                script {
                    // Selenium Grid'i başlat
                    sh 'docker-compose -f SetUpSeleniumGridLatest.yml up -d'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Maven ile testleri çalıştır
                    sh 'mvn clean test'
                }
            }
        }

        stage('Tear Down') {
            steps {
                script {
                    // Selenium Grid'i durdur
                    sh 'docker-compose down'
                }
            }
        }
    }
}
