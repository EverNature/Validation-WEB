pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'mvn -f evern/ clean compile'
      }
    }

    stage('Static Analysis') {
      steps {
        withSonarQubeEnv('EvernSonar') {
          withCredentials([string(credentialsId: 'SonarAdminToken', variable: 'SONAR_TOKEN')]) {
            sh 'mvn sonar:sonar -f evern/ -D sonar.projectKey=ValidationWeb -D sonar.host.url=https://sonarqube.evern.eus -D sonar.login=14057a128f38d6c666a58201ececcb265faf0b35 -D maven.test.skip=true'
          }
        }
      }
    }

    stage('Integration Test') {
      steps {
        withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
          sh 'mvn -f evern/ clean test -Djasypt.encryptor.password=${JASYPT}'
        }
      }
    }
  }
  
  tools {
    maven 'Maven 3.8.5'
  }
}