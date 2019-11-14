#!/bin/bash

ng build
docker build -t eggs:0.0.1 .
