#!/bin/bash
#
# Start a Postgres client guessing the server IP from docker interface
#

DOCKER_IP=$(ip -4 addr show docker0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}')
docker run -it --rm jbergknoff/postgresql-client postgresql://tech:test@$DOCKER_IP:5432/payments
