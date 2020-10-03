
# FILE-STORE

### Reference Documentation
   
   #### Step-1
       
       Already present is the docker-compose.yml
       made use of KOMPOSE to convert docker-compose to
       Kubernetes YAML and deployed in minikube.
   #### Step-2
      NOTE: MAKE SURE TO HAVE kubectl PRESENT IN THE SYSTEM
      * Navigate to location where you have your kube folder
        and open COMMAND PROMPT
      * Apply the kube deployments.
          
      Deploy Service files into minikube env. 
      kubectl create -f   
      kubectl apply
      Thereby, you applciation will running as a pod in minikube env
   
   #### Step-3
   
     SWAGGER URI:  
     
     http://localhost:8181/file-store/swagger-ui.html
        
