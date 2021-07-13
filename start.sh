#!/usr/bin/env bash

# add oracle dependency
./mvnw install:install-file -Dfile=./ojdbc8-19.3.0.0.jar -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=19.3 -Dpackaging=jar
# update dependencies
./mvnw spring-boot:run
