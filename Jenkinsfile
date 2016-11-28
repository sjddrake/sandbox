#!groovy
 node {
  stage 'Build and Test'
  env.PATH = "${tool 'Maven 3'}/bin:${env.PATH}"
  git url: "https://github.com/sjddrake/sandbox/tree/master/mvn-sandbox/sandbox"
  sh 'mvn clean package'
 }