#!/bin/bash

# Agileboard app as a Linux Service

case "$1" in
start)
   nohup java -Dspring.profiles.active=aws -jar /apps/agileboard/agileboard-0.1.jar >> /apps/agileboard/logs/agileboard.log 2>&1&
   echo $!>/var/run/agileboard/agileboard.pid
   echo agileboard is running, pid=`cat /var/run/agileboard/agileboard.pid`
   ;;
stop)
   kill `cat /var/run/agileboard/agileboard.pid`
   rm /var/run/agileboard/agileboard.pid
   echo agileboard is stopped
   ;;
restart)
   $0 stop
   $0 start
   ;;
status)
   if [ -e /var/run/agileboard/agileboard.pid ]; then
      echo agileboard is running, pid=`cat /var/run/agileboard/agileboard.pid`
   else
      echo agileboard is NOT running
      exit 1
   fi
   ;;
*)
   echo "Usage: $0 {start|stop|status|restart}"
esac

echo Request processed successfully
exit 0
