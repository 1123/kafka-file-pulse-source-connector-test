# Application for measuring the latency of the FilePulse Source Connector

The Filepulse Source Connector (https://www.confluent.io/hub/streamthoughts/kafka-connect-file-pulse) by StreamThoughts can be used to ingest files from the file system into Kafka. 
For many applications ingestion latency should be as low as possible. This repository serves as a playground for testing the minimum latency that can be achieved when importing data with this connector. 

Latency is measured between appending the data to a file on disk and reading the data from the Kafka topic where it has been imported by the connector. 
First results show that the latency with a local Kafka cluster is between 6 and 110 milliseconds. 
Latency may be further reduced by tweeking the connector configuration. 

# Prerequisites
* Java 11 or later (JDK required for building this project)
* a recent version of Apache Maven
* Confluent Platform 7.0 or later installed (this will probably also work with earlier versions, but has not been tested)
* bash 
* access to maven central or an artifactory instance for downloading the maven dependencies

# Running the demo
* Download Confluent Platform for quickly setting up a local Kafka and Kafka Connect single node cluster. 
* Clone this repository, and enter the root directory of the repository. 
* Install the connector: `./install-connector.sh`
* Start up Confluent Platform: `confluent local connect start`
* Create a topic for importing the data: `./createTopic.sh eventLog`
* inspect the connector configuration: `cat tail-log.json`
* check that the connector plugin has been loaded successfully: `get-connector-plugins.sh`
* create the connector: `./post-connector.sh tail-log.json`
* check the logs for error messages: `confluent local connect log`
* check the connector status: `./desribe-connector.sh tail-log`
* enter the latency-measure-app directory: `cd latency-measure-app`
* run the app and observer the output: `mvn spring-boot:run`

The output should resemble the following: 

```
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105608039, read: 1643105608059; latency: 20
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105609041, read: 1643105609082; latency: 41
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105610047, read: 1643105610098; latency: 51
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105611048, read: 1643105611112; latency: 64
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105612050, read: 1643105612123; latency: 73
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105613054, read: 1643105613141; latency: 87
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105614059, read: 1643105614161; latency: 102
[scheduling-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - Writing message to file system to be picked up by the connector
[org.springframework.kafka.KafkaListenerEndpointContainer#0-0-C-1] INFO io.confluent.examples.connectorlatencytest.LatencyMeasureService - produced: 1643105615060, read: 1643105615076; latency: 16
```

