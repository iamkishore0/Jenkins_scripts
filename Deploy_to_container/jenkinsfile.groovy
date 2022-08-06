pipeline {
    agent any

    stages {
        stage('git_clone') {
            steps {
                git branch: 'main', url: 'https://github.com/devopslife999/HelloWorld.git'
            }
        }
        stage('Build_with_maven') {
            steps {
                sh 'mvn install'
            }
        }
        stage('Deploy_to_tomcat_container') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'tomcat', path: '', url: 'http://localhost:8082')], contextPath: null, war: '**/*.war'
            }
        }
    }
}
