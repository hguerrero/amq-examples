# Using ksqlDB with Strimzi

[Apache Kafka](https://kafka.apache.org/) has become the leading platform for building real-time data pipelines. Today, Kafka is heavily used for developing [event-driven applications](https://developers.redhat.com/topics/event-driven/), where it lets services communicate with each other through events. Using [Kubernetes](https://developers.redhat.com/topics/kubernetes) for this type of workload requires adding specialized components such  as Kubernetes Operators and connectors to bridge the rest of your  systems and applications to the Kafka ecosystem.

## Kafka on Kubernetes with Strimzi

[Strimzi](https://strimzi.io/) is an open source project that is part of the [Cloud Native Computing Foundation](https://www.cncf.io/) (CNCF) that makes it easier to move Apache Kafka workloads to the  cloud. Strimzi relies on the abstraction layer provided by Kubernetes  and the [Kubernetes Operator](https://developers.redhat.com/topics/kubernetes/operators/) pattern. Its main focus is running [Apache Kafka on Kubernetes](https://developers.redhat.com/topics/kafka-kubernetes) while providing [container](https://developers.redhat.com/topics/containers/) images for Kafka, Zookeeper, and other components that are part of the Strimzi ecosystem.

## ksqlDB - Streaming SQL for Apache Kafka

[ksqlDB](https://ksqldb.io/) is a streaming SQL engine for Apache Kafka. It provides an interactive SQL interface for stream processing based on the Kakfa Streams library.

It is an open core project licensed under the [Confluent Community License](https://github.com/confluentinc/ksql/blob/master/LICENSE).

## Setup

First you need to install Strimzi in your Kubernetes cluster. I recommend to use the default install instructions from the Strimzi QuickStart

1. Create a namespace named `kafka`

   ```shell
   kubectl create namespace kafka
   ```

2. Deploy Strimzi in the `kafka` namespace

   ```sh
   kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka
   ```

3. Create a Kafka cluster using Strimzi. You can use the single node with ephemeral storage

   ```sh
   kubectl create -f kafka-single-ephemeral.yaml
   ```

4. Deploy the ksql container using the provided file

   ```sh
   kubectl create -f ksql.yaml
   ```

   If you are connecting to different Kafka cluster be sure to change the `KSQL_BOOTSTRAP_SERVERS`environment variable in the deployment yaml file to match your cluster bootstrap service:

   ```yaml
   - name: KSQL_BOOTSTRAP_SERVERS
     value: PLAINTEXT://my-cluster-kafka-bootstrap:9092
   ```

## Usage

Finally, you can run the ksqldb-cli container and connect to the ksqldb server

```sh
kubectl run ksq-cli -it --image confluentinc/ksqldb-cli:0.19.0 --rm --restart Never -- /usr/bin/ksql http://ksql:8088
```

You should see the welcome screen and the prompt ready:

```sh
If you don't see a command prompt, try pressing enter.

                  ===========================================
                  =       _              _ ____  ____       =
                  =      | | _____  __ _| |  _ \| __ )      =
                  =      | |/ / __|/ _` | | | | |  _ \      =
                  =      |   <\__ \ (_| | | |_| | |_) |     =
                  =      |_|\_\___/\__, |_|____/|____/      =
                  =                   |_|                   =
                  =        The Database purpose-built       =
                  =        for stream processing apps       =
                  ===========================================

Copyright 2017-2021 Confluent Inc.

CLI v0.19.0, Server v0.19.0 located at http://ksql:8088
Server Status: RUNNING

Having trouble? Type 'help' (case-insensitive) for a rundown of how things work!

ksql>
```

Your environment is ready to start using ksqlDB with Strimzi! You can follow the [quickstart](https://ksqldb.io/quickstart.html) available in the project site.

