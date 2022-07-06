node
{
    def mvnHome
    stage('Github Checkout')
    {
        // Send a message to Slack
        slackSend color: '#FFFF00', message: "'Build #'${env.BUILD_NUMBER}' Started'"
        //slackSend color: '#FFFF00', message: 'Build Started'

        // Get code from Github repository
        git 'https://github.com/ahmedetmanjumia/DiscountsAutomationTest.git'

        //slackSend color: '#FF0000', failOnError:true message:"Build failed  - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"

        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'MAVEN_HOME'
    }
    stage('Execute Suites')
    {
        // Run the maven build
        if (isUnix())
        {
            sh "'${mvnHome}/bin/mvn' test -Pregression" //regression is the id of the profile in pom.xml
        }
        else
        {
            bat(/"${mvnHome}\bin\mvn" test -Pregression/)
        }
        slackSend color: '#FF0000', failOnError:true message:"Build failed  - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
    }
    stage('Results')
    {
      junit '**/target/surefire-reports/TEST-*.xml'
      //archive 'target/*.jar'
      slackSend color: 'good', message: "'Build #'${env.BUILD_NUMBER}' ended successfully'"
      slackSend color: '#FF0000', failOnError:true message:"Build failed  - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
    }
}