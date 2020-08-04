#!/bin/bash
#########################################
# description: 启动主线�?
#########################################

BASEDIR=$(dirname "$0")

source /etc/profile
source ${BASEDIR}/utils.sh
CLASSPATH=""
##########################################
# description: expandClassPath
# call by: main
# return: 0
#
##########################################
# description: 主函数方法用来启动进�?
# call by: main
# return: 0
#
##########################################
function main(){
	
    log_info "begin to start the kill process...."
    # check the process is exist
    code=`ps -ef|grep -v  "grep" |grep -c "${PROCESS_NAME}"` || code=0
    if [ 0 -lt ${code} ]; then
        process_id=`ps aux | grep "${PROCESS_NAME}" | grep -v "grep" | awk '{print $2}'`
        log_info "kill the exist ${PROCESS_NAME}, the process_id is :${process_id}"
        kill -9 ${process_id}

        # sleep 3 second
        sleep 3        
    fi
    #继续判断 是否已经停止�?当停止时结束 
	while [ 0 -lt ${code} ]
	do
		code=`ps -ef|grep -v  "grep" |grep -c "${PROCESS_NAME}"`|grep -c "$YML" || code=0
		echo ${code}
   		sleep 1  
	done   
	if [ 0 -lt ${code} ]; then
    log_info "stop the fail!"
    fi
}
main $@ || exit 1
