pipeline {
	agent any
  	stages {
    	stage('Build Artifact - Maven.') {
			agent {
				docker {
					image 'manrala/all_in_one:v3'
					args '-v /root/.m2:/root/m2'
				}
			}
			steps {
				deleteDir()
				sh 'apk add --no-cache git'
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