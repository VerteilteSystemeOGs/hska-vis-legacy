[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)
[![Travis Build Status](https://travis-ci.org/mavogel/hska-vis-legacy.svg?branch=master)](https://travis-ci.org/mavogel/hska-vis-legacy)

# Distributed Information Systems Laboratory aka vis-lab
This project is the quick setup of the legacy webshop of 
the masters course 'Distributed Information Systems' at the University of Applied Sciences (Karlsruhe).

## Table of Contents
- [Prerequisites](#prerequisites)
- [Usage](#usage)
    - [Quick Start](#quick-start)
    - [Built it on your own](#built-it-on-your-own)
- [Database cleanup](#database-cleanup)
- [Setup in Kubernetes](#setup-kubernetes)
- [License](#license)

## <a name="prerequisites"></a>Prerequisites
- [docker](https://docker.com)
- with `docker-compose`

## <a name="usage"></a>Usage
You can run the images from `docker hub` which is preferred or built it on your own.
First: Start Docker daemon and check with `docker ps`

### <a name="quick-start"></a>Quick Start (docker-hub)
- Copy the `docker-compose.yml` locally in a desired folder and run
```bash
$ docker-compose up -d
# to follow the logs
$ docker-compose logs -tf
# to shutdown
$ docker-compose down
```

### <a name="built-it-on-your-own"></a>Built it on your own
- Run `docker-compose -f docker-compose-local.yml up -d` which will
    - It builds the web app `war` in a staged build, packs it into a docker tomcat8 container,
    and sets the user `tomcat` with password `admin` for the Management Console at http://localhost:8888/
    - Initializes the MySQL Database docker container with the db user defined in `hibernate.cfg.xml`
    - Sets up both containers and make the legacy webshop available under http://localhost:8888/EShop-1.0.0
- Follow the logs via `docker-compose -f docker-compose-local.yml logs -tf`
- To shutdown the containers run `docker-compose -f docker-compose-local.yml down`

## <a name="database-cleanup"></a>Database Cleanup
If you change the user and password of the MySQL database, you should run
```bash
$ docker-compose rm -v
$ rm -rf .data
```
Details can be found [here](https://github.com/docker-library/mysql/issues/51)

## <a name="setup-kubernetes"></a>Setup in Kubernetes
### Prerequisites
Make sure to have `minikube`, `kubectl` and `istioctl` installed.
These commands will be used throughout the setup process.

### Setup minikube cluster
Start minikube cluster with at least 3 cpu cores allocated.
Fewer cores can lead to deployments not starting because of to little computing ressources.

```bash
minikube start --cpus 3
```

Start the kubernetes dashboard:
```bash
minikube dashboard
```

#### Setup ingress
To access the deployed services, an ingress needs to be setup.
The nginx-minikube addon will be used in this case.

```bash
minikube addons enable ingress
```

You may have to start the minikube cluster directly with the ingress addon enabled.
In this case recreate the minikube cluster:

```bash
minikube delete
minikube start --cpus 3 --addons ingress
```

#### Start the kubernetes dashboard

```bash
minikube dashboard
```

### Deploy services
Deploy all containers and services as well as the database using `kubectl`.
All used images are public, so no authentication is required.

```bash
kubectl apply -f kubernetes/kubernetes.yaml
```

In the dashboard you can see the deployments created, which will start the necessary pods and configure the services and ingress.

#### Access services
Using the ingress, the services can be reached on their respective paths `/user`, `/category` and `/product`.
The base ip can be retrieved with minikube.

```bash
minikube ip
```

#### Show load balancing

The load balancing can be demonstrated using a special endpoint built into the user service.
The endpoint `/user/v1/pod/` returns the hostname -- and thus the pod name.

### Istio

#### Installation
To install Istio, issue the following command:

```bash
istioctl install
```

Confirm the question with `y` and wait for the processing to complete.

#### Add Istio to kubernetes deployments

```bash
kubectl label namespace default istio-injection=enabled
```

This commands instructs Istio to install the sidecar next to the deployments of the EShop.
However, this only takes affect on new deployments.
So redeploy using the following commands:

```bash
kubectl delete -f kubernetes/kubernetes.yaml
kubectl apply -f kubernetes/kubernetes.yaml
```

After deployment is finished, you should see two containers per pod in the Kubernetes Dashboard.

### Istio addons
Kiali, Prometheus and Grafana are already provided as addons by Istio.
These addons are preconfigured, so only the deployment using the provided files is necessary.

#### Kiali

```bash
kubectl apply -f samples/addons/kiali.yaml
```

To access Kiali, set up a port forwarding:

```bash
kubectl port-forward svc/kiali -n istio-system 20001:20001
```

#### Prometheus

```bash
kubectl apply -f samples/addons/prometheus.yaml
```

#### Grafana

Grafana uses the prometheus metrics to display statistics.
The endpoints as well as the Grafana Dashboard are already configured by default.

```bash
kubectl apply -f samples/addons/grafana.yaml
kubectl port-forward svc/grafana -n istio-system 3000:3000
```

### Show data in the monitoring systems

To issue multiple requests to the services, a bash script may be used.

```bash
./call_every_service.sh
```

## <a name="license"></a>License
Copyright (c) 2017-2018 Manuel Vogel
Source code is open source and released under the MIT license.
