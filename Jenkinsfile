pipeline {
	agent any

	environment {
		DOCKER_REPO = 'manrala/sayhello'
        CONFIG_REPO_URL = "https://github.com/oaleev/sayhello_config.git"
		CONFIG_ORG = 'oaleev/sayhello.git'
		CONFIG_FOLDER = "${env.WORKSPACE}/config"
	}
  	stages {
    	stage('Build Artifact - Maven.') {
			agent {
				docker {
					image 'maven:3.9.9-eclipse-temurin-21-alpine'
					args '-u root'
				}
			}
			steps {
         			sh "mvn clean package -DskipTests=true"
					archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
					stash includes: 'target/*.jar', name: 'buildJar'
			}
    	}
		
    }
	post {
		always {
			cleanWs()
		}
	}
}