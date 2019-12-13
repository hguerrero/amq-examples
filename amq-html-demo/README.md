# AMQ HTML Javascript Client Demo

This application show a simple demo on how to use the AMQ Javascript Client to connect to AMQ Online through WebSockets.

## Installation

To make installation easier, we provide an openshift template to install the web application. Follow these steps to get a running instance:

1. Create a new project

    oc new-project html-demo

## Deployment

1. Add application to your project using the template

    ```bash
    oc process -f https://raw.githubusercontent.com/hguerrero/amq-examples/master/amq-html-demo/amq-html-demo-template.json -p AMQ_URL=wss://<ADDRESSSPACE_HOSTNAME>:443 -p DESTINATION=<ADDRESS_NAME> -p MQ_USERNAME=<MESSAGINGUSER_USERNAME> -p MQ_PASSWORD=<MESSAGINGUSER_PASSWORD> | oc apply -f -
    ```

    Replace the variables acording to the AMQ online address space, address and messaging user.

    If you are using a self-signed certificate, be sure to navigate first to the `https://<ADDRESSSPACE_HOSTNAME>` to accept it.

## Usage

Login into the application by opening a tab in your web browser and navigate to:

  ```bash
  http://www-html-demo.<APPS_HOSTNAME>
  ```

## Support & Ownership

Feel free to ask [Hugo Guerrero](hguerrer@redhat.com) if you need some support when there are any questions left or if you need some support.