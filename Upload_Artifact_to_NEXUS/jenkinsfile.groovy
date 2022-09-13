pipeline {
    agent any
    
     tools {
        maven "mvn"
    }

    stages {
        stage('Git_clone') {
            steps {
                git branch: 'main', url: 'https://github.com/iamkishore0/maven_project.git'
            }
        }
        stage ('mvn') {
            steps {
                sh 'mvn install'
            }
        }
        stage ('upload_to_nexus') {
            steps {
                nexusArtifactUploader artifacts: [[artifactId: '$BUILD_TIMESTAMP', classifier: '', file: '/var/lib/jenkins/workspace/CI1/webapp/target/webapp.war', type: 'war']], credentialsId: 'tomcat_credentials', groupId: 'mygroup', nexusUrl: '3.134.93.135:8081/nexus', nexusVersion: 'nexus2', protocol: 'http', repository: 'mygroup', version: '$BUILD_ID'
                
            }
        }
    }
}
