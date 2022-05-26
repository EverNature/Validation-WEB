pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'mvn -f evern/ clean compile'
      }
    }

    stage('Build Project') {
      steps {
        sh 'mvn -f evern/ clean install'
      }
    }

    stage('Static Analysis') {
      steps {
        withSonarQubeEnv('SonarEvern') {
          sh 'mvn -f evern/ clean verify sonar:sonar -Dsonar.projectKey=evern_validation_web'
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
          sh 'mvn -f evern/ clean test -Dspring.profiles.active=ci \ -Djasypt.encryptor.password=${JASYPT}'
        }
      }
    }
  }
  
  tools {
    maven 'Maven 3.8.5'
  }
}