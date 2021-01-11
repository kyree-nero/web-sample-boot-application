This is a simple docker example of a java web project 
hosted by an alpine linux distribution

<h3>How to run it</h3>
To run it from docker...  

	1. Change your working directory to the projects root directory  
	2. docker network create --driver bridge app-net
	3. Run    docker build -t app-db -f docker/Dockerfile.db .
	3. Run    docker run \
			--name=app-db  \
			--publish=3306:3306  \
			--network app-net  \
			app-db:latest   
	4. Do a clean install from maven  
	5. Run    docker build -t app -f docker/Dockerfile.app .   
	  (note if you are making an image for kubernetes use docker build -t app -f docker/Dockerfile.kub.app . )
	
	6. Run    docker run \
			--name=app \
			--publish=8080:8080  \
			--network app-net  \
			app:latest  

The project has one dynamic page and one static page you can visit to verify the project is working (see links below)

This project has different branches for angular, vue and plain jquery
 
<h3>urls</h3>

static link <http://localhost:8080>


<h3>other docker commands</h3>

remove it  

	docker stop app
	docker stop app-db
	docker rm app
	docker rm db
	
<h3>the ui</h3>

	There is a simple angular ui and a vue ui to this application just showing the normal crud operations.  Whichever one I've worked on latest will be in the app  
	You can find the code for them in separate npm projects.  
	See web-sample-boot-application-ui-angular  
	See web-sample-boot-application-ui-vue  
	
<h3>https</h3>

keytool -genkeypair -alias app-https -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore app-https.p12 -validity 3650


		