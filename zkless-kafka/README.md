# Zookeeperless Kafka Deployments

This is a Proof-Of-Concept for running the new ZK-less preview of Apache Kafka

We will be using the Kafka 2.8.1 version of the [Strimzi](https://strimzi.io/) project.

## Docker or Podman run

First option is to just run the single container with [Docker](https://docker.io) or Podman(https://podman.io):

```sh
docker run -it --name kafka-zkless -p 9092:9092 -e LOG_DIR=/tmp/logs quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

## Docker Compose

Next option is the [docker-compose.yaml](docker-compose.yaml) file that addresses a way to use a single container instance of Kafka.

It exposes the port 9092 of the host system for the main bootstrap server. If you need to access it as part of a larger compose deployment you can use `kafka:29092` instead.

## Kubernetes Deployment

The following example shows a simple [kubernetes.yaml](kubernetes.yaml) resource to deploy an ephemeral deployment and service to expose the Kafka broker. 

This deployment is limited to be accessed from the same namespace. 