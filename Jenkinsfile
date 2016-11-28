#!groovy
node {
     git url: "https://github.com/sjddrake/sandbox.git"
     def mvnHome = tool 'Maven 3.3.9'
     sh "${mvnHome}/bin/mvn -B verify"
  }