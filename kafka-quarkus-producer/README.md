# Using Quarkus Reactive Messaging with Apache Kafka

This example shows how to send events to Apache Kafka using Quarkus and SmallRye's implementation of the Eclipse MicroProfile Reactive Messaging.

### Using Local Environment

To start the local development Kafka cluster:

1. Be sure Docker Deskop or Podman is running.

2. Open a terminal window and execute the following command:
    ```sh
    docker-compose -f docker-compose.yaml up
    ```

3. Wait until all the components are up and running.

## Start Application

The application is based on Quarkus so it can be started executing the following command:

```sh
./mvnw quarkus:dev
```

This will start the application sending _uber_ vehicle information every 5 seconds to the `uber` topic in the Kafka cluster.