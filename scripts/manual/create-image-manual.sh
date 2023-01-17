#!/bin/bash

IMAGE_NAME=german/recipe-manual-image

pushd ../../

gradle build

docker build -t $IMAGE_NAME .

docker image ls | grep $IMAGE_NAME

/bin/bash