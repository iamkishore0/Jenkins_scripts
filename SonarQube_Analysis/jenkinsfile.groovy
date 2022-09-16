

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

        stage ('Build_with_Maven') {

            steps {

                sh 'mvn install'

            }

        }

        stage('sonar_code_analysis') {

            environment {

                scannerHome = tool 'sonarqube'

            }

            steps {

             withSonarQubeEnv('sonarqube'){

                 sh "${scannerHome}/bin/sonar-scanner \
                  -Dsonar.login=<sonar_token> \
                  -Dsonar.host.url=https://sonarcloud.io \
                  -Dsonar.organization=<organisation_name> \
                  -Dsonar.projectKey=<organisation_key> \
                  -Dsonar.java.binaries=./ "

                }

            }

        }

    }
}
