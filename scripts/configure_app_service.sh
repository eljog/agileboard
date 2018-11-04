#!/bin/bash

# Make the service script executable
chmod 500 /apps/agileboard/agileboard.sh

# Register the the app as a Service
ln -s /apps/agileboard/agileboard.sh /etc/init.d/agileboard
