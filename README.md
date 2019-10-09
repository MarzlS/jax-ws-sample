# WebSphere Liberty JAX-WS Sample
Sample of a JAX-WS application tjat can be deployed on IBM Cloud Foundry Liberty container

## Before you begin

You'll need a [IBM Cloud account](https://console.ng.bluemix.net/registration/), [Git](https://git-scm.com/downloads), [Cloud Foundry CLI](https://github.com/cloudfoundry/cli#downloads), and [Maven](https://maven.apache.org/download.cgi) installed. If you use [IBM Cloud Private](https://www.ibm.com/cloud-computing/products/ibm-cloud-private/), you need access to the [IBM Cloud Private Cloud Foundry](https://www.ibm.com/support/knowledgecenter/en/SSBS6K_2.1.0/cloud_foundry/overview.html) environment.

## Instructions

### Step 1: Clone the sample app

git clone https://github.com/MarzlS/liberty-jax-ws-sample.git

### Step 2: Run the app locally using command line
Use Maven to build your source code and run the resulting app.
1. On the command line, change the directory to where the sample app is located.

````
cd liberty-jax-ws-sample
````

Use Maven to install dependencies and build the .war file.

````
mvn clean install 
mvn liberty:install-feature
````

Run the app locally on Liberty.

````
mvn install liberty:run-server
````

When you see the message The server defaultServer is ready to run a smarter planet., you can view your app at: 
Webservice:		http://localhost:9080/liberty-jax-ws-sample/HelloWorld
WSDL is at: 	http://localhost:9080/liberty-jax-ws-sample/HelloWorld?wsdl

To stop your app, press Ctrl-C in the command-line window where you started the app.

### Step 3: Prepare the app for deployment
To deploy to IBM Cloud, it can be helpful to set up a manifest.yml file. The manifest.yml includes basic information about your app, such as the name, how much memory to allocate for each instance and the route. We've provided a sample manifest.yml file in the jax-ws-sample directory.
Open the manifest.yml file, and change the name from jax-ws-sample to your app name

  applications:
   - name: liberty-jax-ws-sample
     random-route: true
     path: target/liberty-jax-ws-sample.war
     memory: 512M
     instances: 1

In this manifest.yml file, random-route: true generates a random route for your app to prevent your route from colliding with others. If you choose to, you can replace random-route: true with host: myChosenHostName, supplying a host name of your choice.

### Step 4: Deploy to IBM Cloud
You can use the IBM Cloud CLI to deploy apps.
Log in to your IBM Cloud account, and select an API endpoint (optional, here for Frankfurt data center).

````
ibmcloud api https://api.eu-de.bluemix.net
````
Login either via user/pwd or federated login.

````
ibmcloud login
````

or

````
ibmcloud login --sso
````

Target a Cloud Foundry org and space:

````
ibmcloud target --cf
````

If you don't have an org or a space set up, see Adding orgs and spaces.

Set environment parameter necessary for liberty runtime to contain the JAX-WS feature

````
ibmcloud cf set-env <YOUR_APP_NAME> JBP_CONFIG_LIBERTY "app_archive: {features: [javaee-8.0]}"
````

e.g.

````
ibmcloud cf set-env liberty-jax-ws-sample JBP_CONFIG_LIBERTY "app_archive: {features: [javaee-8.0]}"
````


From within the liberty-jax-ws-sample directory, push your application to IBM Cloud.

````
ibmcloud cf push
````

Deploying your application can take a few minutes. When deployment completes, a message shows that your app is running. View your app at the URL listed in the output of the push command with "/GetStartedJava" appended to the end, or view both the app deployment status and the URL by running the following command:

````
ibmcloud cf apps
````

You can troubleshoot errors in the deployment process by using the ibmcloud cf logs jax-ws-sample --recent command.

Webservice:		http://<your-hostname>/liberty-jax-ws-sample/HelloWorld
e.g. 			http://liberty-jax-ws-sample.eu-de.mybluemix.net/liberty-jax-ws-sample/HelloWorld

WSDL is at: 	http://<your-hostname>/liberty-jax-ws-sample/HelloWorld?wsdl


## References

**IBM Cloud Cloud Foundry**: [Getting started tutorial for Liberty](https://console.bluemix.net/docs/runtimes/liberty/getting-started.html#getting-started-tutorial).

**IBM Cloud Kubernetes Service**: [README_kubernetes.md](README_kubernetes.md)

**IBM Cloud Private**: The starter application for IBM Cloud Private guides you through a similar process. However, instead of hosting both your service and application in the same cloud environment, you use a user-provided service. This guide shows you how to deploy your application to IBM Cloud Private and bind it to a Cloudant Database in IBM Cloud. For the complete procedure, see [Working with user-provided services and the Liberty starter app](https://www.ibm.com/support/knowledgecenter/SSBS6K_2.1.0/cloud_foundry/buildpacks/buildpacks_using_Libertyapp.html).

## 