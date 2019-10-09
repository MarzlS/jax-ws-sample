# Deploy to IBM Cloud Kubernetes Service

Follow these instructions to deploy this application to a Kubernetes cluster and connect it with a Cloudant database.



## Build Docker Image

1. Find your container registry **namespace** by running `ibmcloud cr namespaces`. If you don't have any, create one using `ibmcloud cr namespace-add <name>`

2. Identify your **Container Registry** by running `ibmcloud cr info` (Ex: registry.ng.bluemix.net)

3. Build and tag (`-t`)the docker image by running the command below replacing REGISTRY and NAMESPACE with he appropriate values.

   ```sh
   docker build . -t <REGISTRY>/<NAMESPACE>/myapp:v1.0.0
   ```
   Example: `docker build . -t registry.ng.bluemix.net/mynamespace/myapp:v1.0.0`

4. Push the docker image to your Container Registry on IBM Cloud

   ```sh
   docker push <REGISTRY>/<NAMESPACE>/myapp:v1.0.0
   ```

## Deploy

#### Create a Kubernetes cluster

1. [Creating a Kubernetes cluster in IBM Cloud](https://console.bluemix.net/docs/containers/container_index.html#clusters).
2. Follow the instructions in the Access tab to set up your `kubectl` cli.

#### Create the deployment

1. Replace `<REGISTRY>` and `<NAMESPACE>` with the appropriate values in `kubernetes/deployment.yaml`
2. Create a deployment:
  ```shell
  kubectl create -f kubernetes/deployment.yaml
  ```
- **Paid Cluster**: Expose the service using an External IP and Loadbalancer
  ```
  kubectl expose deployment liberty-jax-ws-sample --type LoadBalancer --port 9080 --target-port 9080
  ```

- **Free Cluster**: Use the Worker IP and NodePort
  ```bash
  kubectl expose deployment liberty-jax-ws-sample --type NodePort --port 9080 --target-port 9080
  ```

### Access the application

Verify **STATUS** of pod is `RUNNING`

```shell
kubectl get pods -l app=liberty-jax-ws-sample
```

**Standard (Paid) Cluster:**

1. Identify your LoadBalancer Ingress IP using `kubectl get service get-started-java`
2. Access your application at t `http://<EXTERNAL-IP>:9080/liberty-jax-ws-sample/HelloWorld`

**Free Cluster:**

1. Identify your Worker Public IP using `ibmcloud cs workers YOUR_CLUSTER_NAME`
2. Identify the Node Port using `kubectl describe service get-started-java`
3. Access your application at `http://<WORKER-PUBLIC-IP>:<NODE-PORT>/liberty-jax-ws-sample/HelloWorld`


## Clean Up
```bash
kubectl delete deployment,service -l app=liberty-jax-ws-sample
```
