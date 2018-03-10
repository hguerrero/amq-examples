# AMQ HTML Javascript Client Demo

This application show a simple demo on how to use the AMQ Javascript Client to connect to AMQ Online.

## Installation

To make installation easier, we provide an openshift template to install the web application. Follow these steps to get a running instance of the accident alert web application:

1. Install Openshift template

        oc create -f amq-html-demo-template.json -n openshift

## Deployment

1. Add application to your project

        oc new-app amq-html-demo

## Usage

Login into the application opening a web browser and navigating to \<hostname\>

## Support & Ownership

Feel free to ask [Hugo Guerrero](hguerrer@redhat.com) if you need some support when there are any questions left or if you need some support.