pipeline {
    agent any
    
    tools {
        maven 'mvn'
    }

    stages {
        stage('GIT_CLONE') {
            steps {
                git branch: 'main', url: 'https://github.com/iamkishore0/maven_project.git'
            }
        }
        stage('MAVEN_VALIDATE') {
            steps {
                sh 'mvn validate'
            }
        }
        stage ('MAVEN_COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }
        stage ('MAVEN_TEST') {
            steps {
                sh 'mvn test'
            }
        }
        stage ('MAVEN_PACKAGE') {
            steps {
                sh 'mvn package'
            }
        }
        stage ('INTEGRATION_TEST') {
            steps {
                sh 'mvn integration-test'
            }
        }
        stage ('VERIFY') {
            steps {
                sh 'mvn verify'
            }
        }
        stage ('INSTALL') {
            steps{
                sh 'mvn install'
            }
        }
    }
}
