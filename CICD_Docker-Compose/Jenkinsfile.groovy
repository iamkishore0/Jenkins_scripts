pipeline {
    agent any
    
     tools {
        maven "mvn"
    }

    stages {
        stage('git_clone') {
            steps {
                git branch: 'main', url: 'https://github.com/iamkishore0/maven_project.git'
            }
        }
        stage('Build_with_maven') {
            steps {
                sh 'mvn install'
            }
        }
        stage ('removing_existing_war_file_in_remote_server') {
            steps {
                sshagent(['centos_pvtkey']) {
                    sh 'ssh -o StrictHostKeyChecking=no centos@52.14.207.143 rm -f /home/centos/Devops_Tools_Setup/Apache_Tomcat/webapp.war'
                }
            }
        }
        stage ('Transfering_') {
            steps {
                sshagent(['centos_pvtkey']) {
                    sh 'ssh -o StrictHostKeyChecking=no centos@52.14.207.143'
                    sh ' scp /home/centos/workspace/CICD_Docker/webapp/target/*.war centos@52.14.207.143:/home/centos/Devops_Tools_Setup/Apache_Tomcat'
                }
            }
        }
        stage ('docker_compose_down') {
            steps {
                sshagent(['centos_pvtkey']) {
                    sh 'ssh -o StrictHostKeyChecking=no centos@52.14.207.143 "cd /home/centos/Devops_Tools_Setup/Apache_Tomcat && sudo docker compose down"'
                }
            }
        }
        stage ('docker_compose_up') {
            steps {
                sshagent(['centos_pvtkey']) {
                    sh 'ssh -o StrictHostKeyChecking=no centos@52.14.207.143 "cd /home/centos/Devops_Tools_Setup/Apache_Tomcat && sudo docker compose up -d"'
                }
            }
        }
        
    }
}

