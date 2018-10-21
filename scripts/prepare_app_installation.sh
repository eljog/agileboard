#!/bin/bash

# Create Directories required
mkdir -p /apps/agileboard/logs

# Remove current jar
rm -f /apps/agileboard/agileboard-0.1.jar

# Unregister existing service
rm -rf /etc/init.d/agileboard