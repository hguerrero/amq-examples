# JavaScript Client for AMQP using RHEA

**rhea** is a reactive JavaScript library for the [AMQP](http://amqp.org/) protocol, for easy development of clients and servers. 

In the following example we will use it to create a simple Node application to send and receive messages with the following structure:

```json
{"id":1,"text":"Hello World!","timestamp":1605903126142}
```

## Run locally using AMQ Broker container image

To run the example locally, you need to have a container runtime like [Docker](https://www.docker.com/) or [Podman](https://podman.io/) installed to run the container image and [Node](https://nodejs.org/en/) to run the application.

>For this example we will use Docker

1. Run the AMQ broker container

   ```bash
   docker run -d --name broker --rm -p 5672:5672 -p 8161:8161 -e AMQ_USER=user -e AMQ_PASSWORD=password registry.redhat.io/amq7/amq-broker
   ```

   >This will start the broker in the background, give it a few seconds to be fully initialized.

1. Start the application

   ```bash
   node app.js
   ```

You should see a similar output after a few seconds:

```bash
Message received: {"id":1,"text":"Hello World!","timestamp":1605903126142}
Message received: {"id":2,"text":"Hello World!","timestamp":1605903136147}
Message received: {"id":3,"text":"Hello World!","timestamp":1605903146151}
Message received: {"id":4,"text":"Hello World!","timestamp":1605903156156}
Message received: {"id":5,"text":"Hello World!","timestamp":1605903166159}
Message received: {"id":6,"text":"Hello World!","timestamp":1605903176162}
Message received: {"id":7,"text":"Hello World!","timestamp":1605903186165}
Message received: {"id":8,"text":"Hello World!","timestamp":1605903196167}
Message received: {"id":9,"text":"Hello World!","timestamp":1605903206168}
Message received: {"id":10,"text":"Hello World!","timestamp":1605903216173}
```

## Run locally using AMQ Interconnect

Similar to the AMQ broker you can run the AMQ Interconnect router instead. In this case the router does not store the messages, but provides increased performance.

You can run the interconnect router using the following command:

```bash
docker run -d --rm --name interconnect -p 5672:5672 -p 8672:8672 registry.redhat.io/amq7/amq-interconnect
```

### Troubleshooting

You will need to be authenticated to the Red Hat registry in order to being able to pull the container images from the registry. You can follow the instructions on how to login using [Red Hat Container Registry authentication](https://access.redhat.com/RegistryAuthentication).
