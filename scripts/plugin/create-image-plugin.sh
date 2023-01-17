#!/bin/bash

IMAGE_NAME=german/recipe-default-image

pushd ../../

./gradlew bootBuildImage --imageName=$IMAGE_NAME

docker image ls

/bin/bash