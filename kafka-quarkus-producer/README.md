# Using Quarkus Reactive Messaging with Apache Kafka

This example shows how to send events to [Apache Kafka](https://kafka.apache.org/) using [Quarkus](https://quarkus.io/) and the [SmallRye's extension](https://smallrye.io/smallrye-reactive-messaging/) of the [Eclipse MicroProfile Reactive Messaging](https://github.com/eclipse/microprofile-reactive-messaging).

### Using Local Environment

To start the local development Kafka cluster:

1. Be sure Docker Deskop or Podman is running.

2. Open a terminal window and execute the following command:
    ```sh
    docker-compose -f ../strimzi-all-in-one/docker-compose.yaml up
    ```

3. Wait until all the components are up and running.

## Start Application

The application is based on Quarkus so it can be started executing the following command:

```sh
./mvnw compile quarkus:dev
```

This will start the application sending _uber_ vehicle information every 5 seconds to the `uber` topic in the Kafka cluster.