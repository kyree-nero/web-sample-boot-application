This is a simple docker example of a java web project with a supporting db

<h3>Different ways to run it</h3>

[docker](deploy/docker/README.md)  
[kubernetes](deploy/docker/README.md)  
[kubernetes with helm](deploy/docker/README.md)  

The project has one dynamic page and one static page you can visit to verify the project is working (see links below)

This project has different branches for angular, vue and plain jquery
 
<h3>urls</h3>

static link <http://localhost:8443>


<h3>other docker commands</h3>

	
<h3>the ui</h3>

	There is a simple angular ui and a vue ui to this application just showing the normal crud operations.  Whichever one I've worked on latest will be in the app  
	You can find the code for them in separate npm projects.  
	See web-sample-boot-application-ui-angular  
	See web-sample-boot-application-ui-vue  
	
<h3>https</h3>

keytool -genkeypair -alias app-https -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore app-https.p12 -validity 3650


		