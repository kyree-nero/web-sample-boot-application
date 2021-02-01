Docker




Use this to run in docker standalone
To run it from docker...  
	
	1. Change your working directory to the projects root directory  
	2. docker network create --driver bridge app-net
	3. Run    docker build -t app-db -f deploy/docker/Dockerfile.db .
	3. Run    docker run -d \
			--name=app-db  \
			--publish=3306:3306  \
			--network app-net  \
			app-db:latest   
	4. Do a clean install from maven  
	5. Run    docker build -t app -f deploy/docker/Dockerfile.app .   
	  		  (note if you are making an image for kubernetes  
	  		  use docker build -t app.k8 -f deploy/docker/Dockerfile.kub.app .  
	  		  )
	6. Run    docker run -d \
			--name=app \
			--publish=8443:8443  \
			--network app-net  \
			app:latest  





Stop and Remove

	docker container stop app
	docker container stop app-db
	docker container rm app
	docker container rm app-db

<h3>urls</h3>

static link <http://localhost:8443>