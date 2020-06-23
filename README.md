# Red Hat AMQ Examples

This repository contains a set of examples to be used with Red Hat AMQ messaging suite components.

* [AMQP HTML Websockets Demo](amq-html-demo): Stock Quote application showcasing `rhea` javascript client, websockets and TLS connections from the browser
* [Javascript Client](amq-js-demo): Simple `Hello World` nodejs application featuring `rhea` javascript client
* [AMQ Online Apache Camel ActiveMQ Client](amq-online-client): Apache Camel route using ActiveMQ component and `JmsSSLConnectionFactoy` for AMQ Online TLS connection
* [Spring SSL AMQP JMS Pool](amqp-jms-ssl): Spring application showing the AMQ JMS Pool Library with TLS connection
<!--* [AMQP Quarkus Client](amqp-quarkus)-->
* [Apache Camel SSL AMQP JMS Pool](camel-amqp-demo): Apache Camel producer and consumer routes using AMQ JMS Pool and TLS connection
* [Apache Camel AMQP to Kafka Bridge](camel-amqp-kafka-bridge): Apache Camel implementation of an AMQP to Kafka Bridge
* [Apache Camel AMQP TLS Consumer](camel-amqp-tls-consumer): Apache Camel AMQP component consumer using TLS connection
* [Apache Camel AMQP TLS Producer](camel-amqp-tls-producer): Apache Camel AMQP component producer using TLS connection
* [Apache Camel Kafka Component](camel-kafka-demo): Apache Camel Kafka component using TLS connection
* [Spring for Apache Kafka](kafka-demo): Spring application using the Spring for Apache Kafka project
* [Quarkus Apache Kafka Extension](kafka-quarkus-producer): Quarkus application using the Kafka extension and MicroProfile messaging
* [Quarkus Apache Kafka Streams Extension](kafka-streams-quarkus-rideshare): Quarkus application using the Kafka Streams extension and, Kogito for event processing
* [Apicurio service registry - Avro](registry-example-avro): Quarkus application using Apache Kafka with MicroProfile Reactive Messaging with the Apicuro service registry
* [Apicurio service registry - JSON Schema](registry-example-jsonschema): Quarkus application using Apache Kafka with MicroProfile Reactive Messaging with the Apicuro service registry to validate JSON payloads.
<!--* [MQTT HTML Demo](mqtt-html-demo)-->

## System Requirements

These applications are designed to be run with Red Hat AMQ 7 components. To compile and run these projects you will need:

* JDK 8 or 11+
* Maven 3
* GraalVM Community
