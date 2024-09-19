pipeline {
    agent none

    environment {
        DOCKER_REGISTRY = 'my-docker-registry.com'
        DOCKER_CREDENTIALS_ID = 'docker-creds'
        IMAGE_NAME = 'my-app'
    }

    stages {
        stage('Build, Test, and Package with Maven') {
            agent {
                kubernetes {
                    label 'maven-agent'
                    yamlFile 'kubernetes/maven-pod.yaml'  // Reference the external Maven pod YAML file
                }
            }
            steps {
                container('maven') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            agent {
                kubernetes {
                    label 'docker-agent'
                    yamlFile 'kubernetes/docker-dind-pod.yaml'  // Reference the external Docker DinD pod YAML file
                }
            }
            steps {
                container('docker') {
                    script {
                        sh '''
                        docker build -t $DOCKER_REGISTRY/$IMAGE_NAME:latest .
                        '''
                    }
                }
            }
        }

        stage('Push Docker Image') {
            agent {
                kubernetes {
                    label 'docker-agent'
                    yamlFile 'kubernetes/docker-dind-pod.yaml'
                }
            }
            steps {
                container('docker') {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin $DOCKER_REGISTRY
                        docker push $DOCKER_REGISTRY/$IMAGE_NAME:latest
                        '''
                    }
                }
            }
        }
    }
}