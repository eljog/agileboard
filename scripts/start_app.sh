#!/bin/bash

# Set spring profile as aws
export SPRING_PROFILES_ACTIVE="aws"

# Start App Service
service agileboard start
