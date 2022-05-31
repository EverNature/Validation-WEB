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
          sh 'mvn sonar:sonar -f evern/ clean verify -D sonar.projectKey=ValidationWeb -D sonar.login=SonarAdminToken -D maven.test.skip=true'
        }
      }
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 10, unit: 'MINUTES') {
          waitForQualityGate true
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