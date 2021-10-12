pipeline {

    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                updateGitlabCommitStatus name: 'Build', state: 'pending'
                sh 'mvn clean install -B -DskipTests'
                 echo 'Notify GitLab'
             updateGitlabCommitStatus name: 'Build', state: 'success'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                updateGitlabCommitStatus name: 'Test', state: 'pending'
                sh 'mvn test'
                 echo 'Notify GitLab'
             updateGitlabCommitStatus name: 'Test', state: 'success'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                 updateGitlabCommitStatus name: 'Deploy', state: 'pending'
                 echo 'Notify GitLab'

             updateGitlabCommitStatus name: 'Deploy', state: 'success'
            }
        }
    }
}
