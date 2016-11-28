#!groovy
node {
     git url: "https://github.com/sjddrake/sandbox.git"
     def mvnHome = tool 'M3'
     sh "${mvnHome}/bin/mvn -B verify"
  }