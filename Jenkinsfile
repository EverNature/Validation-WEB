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
        withSonarQubeEnv('SonarWebEvern') {
          sh 'mvn sonar:sonar -f evern/ clean verify sonar:sonar -D sonar.projectKey=evern_validation_web'
        }
      }
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 10, unit: 'SECONDS') {
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