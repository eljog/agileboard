#!/bin/bash

# Stop App Service if running
if (( $(ps -ef | grep -v grep | grep "java -Dspring.profiles.active=aws -jar /apps/agileboard/agileboard-0.1.jar" | wc -l) > 0 ))
then
service agileboard stop
else
echo agileboard is not running
fi
