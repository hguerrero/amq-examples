# Using Kafka Streams extension for Quarkus

This example shows how to process events from [Apache Kafka](https://kafka.apache.org/) using [Quarkus](https://quarkus.io/) extension for [Kafka Streams](https://kafka.apache.org/documentation/streams/).

### Using Local Environment

To start the local development Kafka cluster:

1. Be sure Docker Deskop or Podman is running.

2. Open a terminal window and execute the following command:
    ```sh
    docker-compose -f ../strimzi-all-in-one/docker-compose.yaml up
    ```

3. Wait until all the components are up and running.

## Start Kafka Streams Application

The application is based on Quarkus so it can be started executing the following command:

```sh
mvn compile quarkus:dev
```

This will start the application to process _ridesahre_ vehicle information.

## Start RideShare Producer

Follow the instructions of the `kafka-quarkus-producer` example to start sending events into Kafka.

