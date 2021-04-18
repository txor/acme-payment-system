#!/bin/bash
#
# Start a Kafka console consumer to view messages produced the given topic
#

DOCKER_CONTAINER=acme-payment-system_kafka-server_1

if [ $# == 1 ]; then
	docker exec -ti $DOCKER_CONTAINER /opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic $1 --from-beginning
else
	echo "Usage: $0 topic"
	echo "       view messages produced for topic"
fi

