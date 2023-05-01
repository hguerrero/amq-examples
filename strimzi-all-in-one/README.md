# Strimzi - Container Environment

This folder contains a [docker-compose.yaml](docker-compose.yaml) file that can be used to configure the [Strimzi](https://strimzi.io/) [Cloud Native Computing Foundation](https://www.cncf.io/) sandbox project containers to run on localhost. This environment is suitable for running [Apache Kafka](http://kafka.apache.org/)-related examples.

## Components

It includes the following containers:

* Apache Zookeeper
* Apache Kafka Broker 3.4
* Strimzi HTTP Bridge 0.26
* [Apicurio Service Registry](https://github.com/Apicurio/apicurio-registry#apicurio-registry) 2.4.2
