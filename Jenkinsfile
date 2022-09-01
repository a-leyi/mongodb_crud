pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package -f /home/leyi/下载/apache-maven-3.8.4-bin/apache-maven-3.8.4/conf/settings.xml' 
            }
        }
    }
}
