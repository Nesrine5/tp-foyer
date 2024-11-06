pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                echo 'Pulling...'
                git branch: 'Nesrine_Kattoussi_SAE08', url: 'https://github.com/Nesrine5/tp-foyer.git'
            }
        }

        stage('MVN CLEAN') {
            steps {
                echo "Running Maven Clean"
                sh 'mvn clean'
            }
        }

        stage('MVN COMPILE') {
            steps {
                echo "Running Maven Compile"
                sh 'mvn compile'
            }
        }
        stage('Run Mockito Tests') {
    steps {
        echo 'Running Mockito Tests...';
        sh 'mvn -Dtest=FoyerServiceImplMockTest test';
    }
}

        stage('SonarQube') {
            steps {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
                }
            }
        }
          stage('Nexus') {
            steps {
                echo "Deploying to Nexus"
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USER', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh 'mvn deploy -Dmaven.test.skip=true -Dusername=$NEXUS_USER -Dpassword=$NEXUS_PASSWORD'
                }
            }
        }
              stage('DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin'
                }
            }
        }

      stage('Build Docker Image') {
            steps {
                echo "Building Docker Image for the application"
                sh 'docker build -t tp-foyer:5.0.0 . '

            }
        }
        stage('Push to Docker Hub') {
            steps {
                echo "Pushing Docker Image to Docker Hub"
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker tag tp-foyer:5.0.0 $DOCKER_USERNAME/tp-foyer:5.0.0'
                    sh 'docker push $DOCKER_USERNAME/tp-foyer:5.0.0'
                }
            }
        }
             stage('Docker-Compose') {
            steps {
                echo "Deploying with Docker Compose"
                dir("${WORKSPACE}") { // Use the current workspace directory
                    sh 'docker compose up -d --build'
                }
            }
        }

stage('Monitoring Services G/P') {
            steps {
                script {
                    sh 'docker start prometheus'
                    sh 'docker start grafana'
                }
            }

        }




    }
}