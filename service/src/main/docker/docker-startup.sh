#!/bin/sh
#-----------------------------------------------------------------------------------------------------------------------
# File:  docker-startup.sh
#
# Desc:  Start the tds-aspl.jar with the appropriate properties.
#
#-----------------------------------------------------------------------------------------------------------------------

java $JAVA_OPTS -jar /tds-aspl.jar
