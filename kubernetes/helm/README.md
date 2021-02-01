Helm

The app chart depends on the app-db chart.  

The database takes some time to come up so be patient.


update dependencies

	helm dependency update ./app     
	
	
install charts

	helm install sample-web ./app      

remove charts

	helm delete sample-web  