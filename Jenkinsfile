#!groovy
node {
     // Mark the code checkout 'stage'.... 
     stage 'Checkout' 
     git url: "https://github.com/sjddrake/sandbox.git"
     
     // Mark the code checkout 'build'.... 
     stage 'Build'      
     def mvnHome = tool 'Maven 3.3.9'
     dir('./mvn-sandbox/sandbox') {
          sh "${mvnHome}/bin/mvn -B verify"
     } 
     
  }