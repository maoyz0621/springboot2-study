#!/bin/bash
#########################################
# description: \u542f\u52a8\u4e3b\u7ebf\ufffd?
#########################################

BASEDIR=$(dirname "$0")

source /etc/profile
source $(readlink -f $(dirname $0))/../../env.sh
source ${BASEDIR}/utils.sh
CLASSPATH=""
##########################################
# description: expandClassPath
# call by: main
# return: 0
#
##########################################
# description: \u4e3b\u51fd\u6570\u65b9\u6cd5\u7528\u6765\u542f\u52a8\u8fdb\ufffd?
# call by: main
# return: 0
#
##########################################
function main(){	
    log_info "begin to start the kill process...."
    
    cd "${BASEDIR}"/
    cd ../
    
    # check the process is exist
    code=`ps -ef|grep -v  "grep" |grep -c "${PROCESS_NAME}"` || code=0
    if [ 0 -lt ${code} ]; then
        process_id=`ps aux | grep "${PROCESS_NAME}" | grep -v "grep" | awk '{print $2}'`
        log_info "kill the exist ${PROCESS_NAME}, the process_id is :${process_id}"
        kill -9 ${process_id}
    fi
    
    #\u7ee7\u7eed\u5224\u65ad \u662f\u5426\u5df2\u7ecf\u505c\u6b62\ufffd?\u5f53\u505c\u6b62\u65f6\u7ed3\u675f 
	while [ 0 -lt ${code} ]
	do
		code=`ps -ef|grep -v  "grep" |grep -c "${PROCESS_NAME}"` || code=0
		echo ${code}
   		sleep 1  
	done   
       
    # sleep 1 second
    sleep 1        

    # GC options
    GC_LOG_OPTS=""
    if [ "x${GC_LOG_ENABLED}" = "xtrue" ]; then
      GC_LOG_OPTS="-Xloggc:$LOG_DIR/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps "
    fi

    JAVA_OPTIONS=" -Xss256k -XX:-UseCompressedClassPointers -XX:+AggressiveOpts -XX:+UseBiasedLocking -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:InitiatingHeapOccupancyPercent=50 -XX:+DisableExplicitGC -Djava.awt.headless=true"

    nohup java ${JAVA_MEM_OPTIONS} ${JAVA_OPTIONS} ${GC_LOG_OPTS} -jar ${PROCESS_NAME}  2>&1 > /dev/null &
    
    runing=`netstat -an | grep ${PORT} | awk '{print $4}'`
	index=30
	while [ "${runing}" == "" ] && [ 0 -lt ${index} ]
        do
                runing=`netstat -an | grep ${PORT}  | awk '{print $4}'`
                index=$[$index-1]
                echo "$index $runing"
                sleep 1
        done
    if [ "${runing}" == "" ]; then
    log_info "start the fail!"
    exit 1
    fi
}
main $@ || exit 1
