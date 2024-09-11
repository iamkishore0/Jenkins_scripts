

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
        stage('sonar_code_analysis') {
            environment {
                scannerHome = tool 'sonarqube'
            }
            steps {
             withSonarQubeEnv('sonarqube'){
                 sh "${scannerHome}/bin/sonar-scanner \
                  -Dsonar.login=57bca5c03f220d0ad4df6925468cbb2f3928a0c2 \
                  -Dsonar.host.url=https://sonarcloud.io \
                  -Dsonar.organization=myorganization1234 \
                  -Dsonar.projectKey=myorganization1234 \
                  -Dsonar.java.binaries=./ "
                }
            }
        }
        stage ('Build_with_Maven') {
            steps {
                sh 'mvn install'
            }
        }
        
        stage ('Nexus_Artifact_uploader') {
            steps {
                nexusArtifactUploader artifacts: [[artifactId: '$BUILD_TIMESTAMP', classifier: '', file: '/var/lib/jenkins/workspace/Complete_Pipeline/webapp/target/webapp.war', type: 'war']], credentialsId: 'nexus_tomcat', groupId: 'srinivas', nexusUrl: '54.226.245.6:8081/nexus', nexusVersion: 'nexus2', protocol: 'http', repository: 'srinivas', version: '$BUILD_ID'
            }
        }
        stage ('Deploy_to_container') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'nexus_tomcat', path: '', url: 'http://54.236.101.249:8080')], contextPath: null, war: '**/*.war'
            }
        }
    }
}
