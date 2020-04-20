# Apicurio Service Registry Example - JSON Schema

This project shows how to use the Apicurio Service Registry to manage Apache Kafka data schemas. This example uses JSON schemas to validate messages to and from a local Apache Kafka cluster.

## Apache Kafka Cluster and Apicurio Service Registry

First you need an Apache Kafka cluster. You can follow the instructions from the [Apache Kafka website](https://kafka.apache.org/quickstart) or use the `docker-compose.yaml` file in this project.

### Using Local Environment

To start the local development cluster and registry:

1. Be sure Docker Desktop is running.
1. Open a terminal window and execute the following command:

    ```bash
    docker-compose -f docker-compose.yaml up
    ```

1. Wait until all the components are up and running.

## Register the JSONSchema

After you started the Apicurio registry is necessary to register the schema we will be using. We can register it using the Apicurio Maven plugin. Execute the following command:

```bash
mvn clean apicurio-registry:register -P schema
```

This will POST the schema into the registry.

## Start Application

The application is based on Quarkus so it can be started executing the following command:

```bash
./mvnw compile quarkus:dev
```

This will start the application sending and receiveing messages every 10 seconds.

### Validate producer

* Stop the running application by pressing `Ctrl + C` or `Cmd + C`.

* Add the validation to the schema to not accept negative prices. Open the `stockprice.json` file under resources. Add the validation under *price*. Make it look like the following:

    ```json
            "price": {
                "$id": "#/properties/price",
                "type": "number",
                "minimum": 0,
                "title": "The Price Schema",
                "description": "An explanation about the purpose of this instance."
            }
    ```

* Register the new version of the schema by running:

    ```bash
    mvn clean apicurio-registry:register -P schema
    ```

* Start again the application, you will notice the validation errors:

    ```log
    ERROR [io.sma.rea.mes.kaf.imp.KafkaSink] (vert.x-eventloop-thread-1) Unable to write to Kafka: java.io.UncheckedIOException: com.fasterxml.jackson.databind.JsonMappingException: [Validation Failure
    ------------------
    Rule:     properties
    Property: price
    Message:  Property validation failed
    Location: at
    Details:
        Rule:     minimum
        Message:  Value -52.9134246726087 is smaller than minimum 0
        Location: at
    ```

### Validate consumer

We will reuse the events we sent with the incorrect schema by simulating a different process.

* Stop the running application by pressing `Ctrl + C` or `Cmd + C`.

* Stop the docker-compose cluster, this will reset the registry as we are using the in-memory persistance.

* Start again the registry:

    ```bash
    docker-compose -f docker-compose.yaml up
    ```

* Register the version of the schema that validates negatives by running:

    ```bash
    mvn clean apicurio-registry:register -P schema
    ```

    This time the version includes the price validation, we will try to consume the messages written before we added it.

* Uncomment the code to consumer the events.

    ```java
        @Incoming("price-in")
        public CompletionStage<Void> receive(KafkaRecord<String,StockPrice> record) throws IOException {
            return CompletableFuture.runAsync(() -> {
                LOGGER.info("=====> {}", record.getPayload());
            });
        }
    ```

* Run again the application:

    ```bash
    ./mvnw compile quarkus:dev
    ```

* You will notice this time the consumer sent the validation error:

    ```log
    ERROR [io.sma.rea.mes.kaf.imp.KafkaSource] (vert.x-kafka-consumer-thread-0) Unable to read a record from Kafka topic 'prices-json': org.apache.kafka.common.errors.SerializationException: Error deserializing key/value for partition prices-json-0 at offset 30. If needed, please seek past the record to continue consumption.
    Caused by: com.worldturner.medeia.api.ValidationFailedException: [Validation Failure
    ------------------
    Rule:     properties
    Property: price
    Message:  Property validation failed
    Location: at 1:44 in [B@7422a9f7
    Details:
        Rule:     minimum
        Message:  Value -67.32549827618678 is smaller than minimum 0
        Location: at 1:44 in [B@7422a9f7
    ```
