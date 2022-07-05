node
{
    def mvnHome
    stage('Checkout')
    {
        // Get code from Github repository
        git 'https://github.com/ahmedetmanjumia/DiscountsAutomationTest.git'

        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'MAVEN_HOME'
    }
    stage('Build')
    {
        // Run the maven build
        if (isUnix())
        {
            sh "'${mvnHome}/bin/mvn' test -pom.xml"
        }
        else
        {
            bat(/"${mvnHome}\bin\mvn" test -pom.xml/)
        }
    }
    stage('Results')
    {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
}
