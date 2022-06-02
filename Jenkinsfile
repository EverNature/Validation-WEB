pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'mvn -f evern/ clean compile'
      }
    }

    stage('Integration Test') {
      steps {
        withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
          sh 'mvn -f evern/ clean test -D jasypt.encryptor.password=${JASYPT}'
        }
      }
    }

    stage('Static Analysis') {
      steps {
        withSonarQubeEnv('EvernSonar') {
          withCredentials([string(credentialsId: 'SonarAdminToken', variable: 'SONAR_TOKEN')]) {
            sh 'mvn sonar:sonar -f evern/ -D sonar.projectKey=ValidationWeb -D sonar.host.url=https://sonarqube.evern.eus -D sonar.java.coveragePlugin=jacoco -D sonar.login=${SONAR_TOKEN} -D maven.test.skip=true'
          }
        }
      }
    }

    stage('QualityGate') {
        steps {
            timeout(time: 10, unit: 'SECONDS') {
                waitForQualityGate abortPipeline: true, credentialsId: 'SonarAdminToken'
            }
        }
    }

    stage('Remove docker container and image') {
      when {
        branch 'main'
      }
      steps {
        sh 'docker ps -f name=decklearn -q | xargs --no-run-if-empty docker container stop'
        sh 'docker container ls -a -fname=decklearn -q | xargs -r docker container rm'
        sh 'docker images -q -f dangling=true | xargs --no-run-if-empty docker rmi'
      }
    }

    stage('Create docker image') {
      when {
        branch 'main'
      }
      steps {
        withCredentials(bindings: [usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'pass', usernameVariable: 'name')]) {
          sh 'mvn compile jib:dockerBuild -Djib.to.auth.username=$name -Djib.to.auth.password=$pass'
        }
      }
    }

    stage('Run decklearn container') {
      when {
        branch 'main'
      }
      steps {
        sh 'docker run -d -p 8181:8080 --name decklearn evernature/evern'
      }
    }

    stage('Publish docker image') {
      when {
        branch 'main'
      }
      steps {
        withCredentials(bindings: [usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'pass', usernameVariable: 'name')]) {
          sh 'mvn compile jib:build -Djib.to.auth.username=$name -Djib.to.auth.password=$pass'
        }
      }

  }
  
  tools {
    maven 'Maven 3.8.5'
  }
}
}