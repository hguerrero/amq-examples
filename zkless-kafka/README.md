# Zookeeperless Kafka Deployments

This is a Proof-Of-Concept for running the new ZK-less preview of Apache Kafka

We will be using the Kafka 2.8.1 version of the [Strimzi](https://strimzi.io/) project.

## Docker Compose

The first option is the [docker-compose.yaml](docker-compose.yaml) file that addresses a way to use a single container instance of Kafka.

It exposes the port 9092 of the host system for the main bootstrap server. If you need to access it as part of a larger compose deployment you can use `kafka:29092` instead.

## Kubernetes Deployment

The followinge example shows a simple [kubernetes.yaml](kubernetes.yaml) resource to deploy an ephemeral deployment and service to expose the Kafka broker. 

This deployment is limited to be accessed from the same namespace. 