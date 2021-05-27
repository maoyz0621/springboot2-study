#!/bin/bash

prog=quartz

pidfile=/tmp/${prog}.pid

bin=$(dirname $0)
bin=$(cd "$bin"; pwd)

[ -f /etc/init.d/functions ] && source /etc/init.d/functions

for i in $bin/../lib/*.jar
do
  lib=$lib:$i
done

for i in $bin/../conf/*.xml
do
  lib=$lib:$i
done

for i in $bin/../conf/*.properties; 
do
  lib=$lib:$i
done

for i in $bin/../conf
do
  lib=$lib:$i
done

logsdir=/data/logs/$prog
[ ! -d $logsdir ] && (mkdir -pv ${logsdir}>/dev/null; chmod 777 ${logsdir})

function start() {
  daemon "java -cp $lib -DconfigDir=$bin/../conf/ com.asiainfo.manager.QuartzMain >${logsdir}/quartz.log 2>&1 &"

  for ((i=0;i<30;i++))
  do
    pid=$(ps -ef | grep -v grep | grep java | grep $prog | awk '{print $2}')

    if [ "x$pid" != "x" ]
    then
      echo $pid > $pidfile
      retval=0
      break
    fi
    retval=1
    sleep 1
  done

  if [ $retval -eq 0 ] 
  then 
    action "Starting $prog pid: $pid" /bin/true
  else
    action "Starting $prog " /bin/false
    retval=1
  fi
}

function stop()
{
  if [ ! -f $pidfile ] 
  then
    action "Stopping $prog " /bin/true
    return
  fi

  pid=$(cat $pidfile)
  kill $pid >/dev/null
  
  for ((i=0;i<30;i++))
  do
    c=$(ps -ef | grep -v grep | grep $pid | wc -l)

    if [ $c -eq 0 ]
    then
      retval=0
      break
    fi
    sleep 1
    retval=2
  done
  
  if [ $retval -eq 0 ] 
  then 
    action "Stopping $prog pid: $pid" /bin/true
    rm -rf $pidfile
  else
    action "Stopping $prog " /bin/false
    retval=1
  fi
}

rh_status() {
  status -p $pidfile $prog
}

rh_status_q() {
  rh_status >/dev/null 2>&1
}

case "$1" in
  start)
    rh_status_q && retval=0
    $1
    ;;
  stop)
    rh_status_q || retval=0
    $1
    ;;
  restart)
    stop
    start
    ;;
  status)
  rh_status
  ;;
  debug)
    java -cp $lib -DconfigDir=$bin/../conf/ com.asiainfo.manager.QuartzMain
    ;;
  *)
    echo $"Usage: $0 {start|stop|status|restart|debug}"
    retval=2
    ;;
esac

exit $retval