#!/bin/bash

# Make the jar file executable
chmod 500 /apps/agileboard/agileboard-0.1.jar

# Register the jar as a Service
ln -s /apps/agileboard/agileboard-0.1.jar /etc/init.d/agileboard
