## ISA Json to BioSample and ENA SRA converter and submitter command line tool

### Run locally
#### Prerequisites
1. Java 11 (to run locally)
2. Gradle (to build the executable)

#### Steps to set up
##### Download and install Java 11
Oracle's OpenJDK JDK binary for Windows and Linux is available on release-specific pages of jdk.java.net as .zip archive.

    Navigate in Web Browser to: https://jdk.java.net/java-se-ri/11

Look for the RI Binary (build 11+28) under the GNU General Public License version 2 section.

    Click on the zip link right next to Windows/x64/ Linux.

At the time of writing the latest Oracle OpenJDK release was version: 11.0.28

    Download and extract the file

Select an extract destination for the JDK files.

##### Setup JAVA_HOME and PATH environment variables
###### Setup JAVA_HOME on a Windows System

Right-click My Computer and select Properties.

On the Advanced tab, select Environment Variables, and then edit JAVA_HOME to point to where the JDK software is located, for example, C:\Program Files\Java\jdk1.6.0_02.

###### Setup JAVA_HOME on  LINUX/ UNIX system

To set JAVA_HOME, do one of the following:

For Korn and bash shells, run the following commands:

    export JAVA_HOME=jdk-install-dir

    export PATH=$JAVA_HOME/bin:$PATH

For the bourne shell, run the following commands:

    JAVA_HOME=jdk-install-dir

    export JAVA_HOME

    PATH=$JAVA_HOME/bin:$PATH

    export PATH

For the C shell, run the following commands:

    setenv JAVA_HOME jdk-install-dir

    setenv PATH $JAVA_HOME/bin:$PATH

    export PATH=$JAVA_HOME/bin:$PATH

##### Gradle setup

Please follow - https://gradle.org/install/

#### Gradle build

./gradlew build -x test 

A JAR (Java archive) by the name ISASRAProject27-0.0.1-SNAPSHOT.jar will get created in the project directory
biohackathon-projects-2022\27\ISASRAProject27\build\libs\ISASRAProject27-0.0.1-SNAPSHOT.jar

#### Command line arguments

The tool needs 4 command line arguments:
1. webinJwt
2. webinUserName
3. webinPassword
4. isaJsonFilePath

Command line arguments values:
1. webinJwt - get the JWT token using the below curl:

TOKEN=$(curl --location --request POST 'https://www.ebi.ac.uk/ena/submit/webin/auth/token' --header 'Content-Type: application/json' --data-raw '{
"authRealms": [
"ENA"
],
"password": "EBIN_PASSWORD",
"username": "WEBIN-ID"
}')

2. webinUserName: Webin user name
3. webinPassword: Webin account password
4. isaJsonFilePath: File path of the ISA Json file

#### Running the application
java -jar ISASRAProject27-0.0.1-SNAPSHOT.jar --webinJwt=webin_token --webinUserName=webin_username --webinPassword=webin_password --isaJsonFilePath=file_path_in_local_system
