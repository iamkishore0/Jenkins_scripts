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
        stage('Upload_Artifact_to_Nexus') {
            steps {
                nexusArtifactUploader artifacts: [[artifactId: '$BUILD_TIMESTAMP', classifier: '', file: 'helloworld/target/helloworld.war', type: 'war']], credentialsId: 'nexus', groupId: 'mygroup', nexusUrl: 'localhost:8081/nexus', nexusVersion: 'nexus2', protocol: 'http', repository: 'helloworld', version: '$BUILD_ID'
            }
        }
    }
}
