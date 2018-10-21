#!/bin/bash

# Remove existing version of Java
yum remove -y java

# Install Java 8
yum install -y java-1.8.0-openjdk