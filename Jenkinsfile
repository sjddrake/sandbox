#!groovy
node {
     git url: "https://github.com/sjddrake/sandbox/tree/master/mvn-sandbox/sandbox"
     def mvnHome = tool 'M3'
     sh "${mvnHome}/bin/mvn -B verify"
  }