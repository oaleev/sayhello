pipeline {
    agent {
        kubernetes {
            yaml '''
                apiVersion: v1
                kind: Pod
                metadata:
                  labels:
                    component: ci
                spec:
                  containers:
                  - name: maven
                    image: manrala/all_in_one:v3
                    command:
                    - cat
                    tty: true
                    volumeMounts:
                    - mountPath: "/root/.m2"
                      name: m2
                  volumes:
                  - name: m2
                    persistentVolumeClaim:
                      claimName: m2-pvc
            '''
            defaultContainer 'maven'
        }
    }
	environment {
		DOCKER_REPO = 'manrala/sayhello'
        CONFIG_REPO_URL = "https://github.com/oaleev/sayhello_config.git"
		CONFIG_ORG = 'oaleev/sayhello.git'
		CONFIG_FOLDER = "${env.WORKSPACE}/config"
	}
    stages {
        stage('Build') {
            steps {
                container('maven') {
                    sh 'mvn clean package -DskipTests=true'
					archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
					stash includes: 'target/*.jar', name: 'buildJar'
                }
            }
        }
        stage('Test') {
            steps {
                container('maven') {
                    sh 'mvn test'
                }
            }
			post {
				always {
					junit 'target/surefire-reports/*.xml'
					jacoco execPattern: 'target/jacoco.exec'
				}
			}
        }
		stage('Build the Image and Push to repo...') {
			steps {
				 container('maven') {
                    withDockerRegistry(credentialsId: 'DOCKER', url: 'https://index.docker.io/v1/') {
					sh """
					ls -ls
    				docker build -t ${DOCKER_REPO}:""$GIT_COMMIT"" .
					docker push ${DOCKER_REPO}:""$GIT_COMMIT""
					"""
                	}
				}
			}
    	}
    }
}