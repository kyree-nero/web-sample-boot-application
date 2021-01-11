Simple Kubernetes  



An off-shoot of docker-simple this builds off the docker-simple project and uses it in kubernetes.  
It assumes you have that docker image in your local repository so if you dont have it... get it.  
	
	0. kubectl create secret generic app-https-secret --from-file=../keystore/app-https.p12
	1. Go to root of project  
	2. kubectl apply -f  .  
	3. Go to https://localhost:8080  
	
Note:  You can directly connect with the database by using the specified node port (32306)  

Note:  Useful commands

	kubectl exec --stdin --tty &lt;pod-name&gt; -- /bin/bash  


	kubectl get events  --sort-by='.metadata.creationTimestamp'  


Install the kubenetes front end and use it... (optional)  

	1. Grab it  
	   
	   kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-rc3/aio/deploy/recommended.yaml  
	   
	2. Start a proxy  
	
	   kubectl proxy  
	   
	3. Go to the page  
	
		http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy  
		
	4. Get token and put it in the page when it prompts you  
	
		kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | awk '/^deployment-controller-token-/{print $1}') | awk '$1=="token:"{print $2}'  

	