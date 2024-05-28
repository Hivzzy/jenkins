pipeline {
    agent any

    tools {
        maven "jenkins-maven"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Hivzzy/jenkins.git'
            }
        }

        stage('Build Maven') {
            steps {
                bat "mvn clean package"
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('jenkins-sonar') {
                    bat 'mvn clean verify sonar:sonar'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                bat "docker build -t jenkins-sonarqube ."
            }
        }
        
        stage('Push Image to Docker Hub') {
            steps {
                withCredentials([string(credentialsId: 'DOCKER_USER', variable: 'DOCKER_USER_VAR'), string(credentialsId: 'DOCKER_PASS', variable: 'DOCKER_PASS_VAR')]) {
                    bat "docker login -u %DOCKER_USER_VAR% -p %DOCKER_PASS_VAR%"
                }
                
                bat "docker push jenkins-sonarqube"
                
                bat "docker logout"
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
