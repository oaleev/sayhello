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
                //     volumeMounts:
                //     - mountPath: "/root/.m2"
                //       name: m2
                //   volumes:
                //   - name: m2
                //     persistentVolumeClaim:
                //       claimName: m2
            '''
            defaultContainer 'maven'
        }
    }
    stages {
        stage('Build') {
            steps {
                container('maven') {
                    sh 'mvn package -DskipTests'
                }
            }
        }
        stage('Test') {
            steps {
                container('maven') {
                    sh 'mvn test'
                }
            }
        }
    }
}