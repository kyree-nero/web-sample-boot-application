Simple Kubernetes

An off-shoot of docker-simple this builds off the docker-simple project and uses it in kubernetes.
It assumes you have that docker image in your local repository so if you dont have it... get it.  

	1. Go to root of project
	2. kubectl apply -f  .
	3. Go to https://localhost:8080
	
Note:  You can directly connect with the database by using the specified node port (32306)