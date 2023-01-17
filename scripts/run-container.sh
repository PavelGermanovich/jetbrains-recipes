#!/bin/bash

IMAGE_KEY="recipe-manual"
IMAGE_NAME="german/$IMAGE_KEY-image"
CONTAINER_NAME="application"

docker container stop $CONTAINER_NAME
docker container rm $CONTAINER_NAME
docker run --name $CONTAINER_NAME -p 8881:8881 -d $IMAGE_NAME

/bin/bash