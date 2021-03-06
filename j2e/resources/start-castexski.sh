#!/bin/bash

# step #1: configure the bank.propoerties file
mkdir -p ./WEB-INF/classes/
touch ./WEB-INF/classes/bank.properties
echo "bankHostName=$bank_host" >> ./WEB-INF/classes/bank.properties
echo "bankPortNumber=$bank_port" >> ./WEB-INF/classes/bank.properties
echo "##################################################################"
echo "bankHostName=$bank_host and bankPortNumber=$bank_port"
echo "##################################################################"

# step #2: update the webapp to load the right properties
jar uvf ./webapps/webservices-module.war ./WEB-INF/classes/bank.properties

# step #3: start the TomEE engine
catalina.sh run
