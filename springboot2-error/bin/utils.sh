#!/bin/bash
#########################################
# description: 公共工具类
#########################################

# import the configuration file
BASEDIR=$(dirname "$0")
source ${BASEDIR}/config_properties.cfg
#########################################
# description: 记录日志方法
# call by:
# return: 0
#
#########################################
function log(){
    # checkDir
    checkDir || (echo "the Log dir is not exist" ; exit 0 )

    # check the disk is full
    checkDiskSpace || ( echo " the disk is full" ; exit 0 )

    # check file is exist
    if [ 2 -gt $# ] ; then
            echo "parameter not right in zc_log function"
            return
    fi
    local _OrgSeq=0
    if [ -f $LOG_FILE ];then
            LogFileLen=`ls -l ${LOG_FILE} | awk '{print $5}'`
            if [ $LogFileLen -gt $FILE_SIZE ]; then
                if [ ${LOG_SEQ} -gt 0 ] ; then
                    _OrgSeq=${LOG_SEQ}
                    if [ $_OrgSeq -gt $LOG_FILE_COUNT ];then
                        LogFileSeq=0
                    else
                        LogFileSeq=${_OrgSeq}
                    fi
                    _OrgSeq=`expr ${LogFileSeq} + 1`
                else
                    LogFileSeq=0
                    _OrgSeq=1
                fi
                sed  -i "s/LOG_SEQ=.*/LOG_SEQ=${_OrgSeq}/g" ${BASEDIR}/plugin_properties.cfg
                mv $LOG_FILE ${LOG_FILE}.${LogFileSeq}
            fi
     else
        createFile
     fi
     _LogInfo=$1
     echo `date +20'%y-%m-%d %H:%M:%S'`" [${_LogInfo}] $$ $0 $2" >> ${LOG_FILE} 2>&1
     echo "$2"
}

#########################################
# description: 记录info日志
# call by:
# return: 0
#
#########################################
function log_info(){
    log info "$1"
}

#########################################
# description: 记录error日志
# call by:
# return: 0
#
#########################################
function log_error(){
    log error "$1"
}

#########################################
# description: create file if the file
# call by: log
# return: 0 create success
#         1 create fail
#
#########################################
function createFile(){
    # if the file is not exist
    local iRet=0

    touch ${LOG_FILE} 2>&1 >/dev/null  || iRet=1
    #chmod 700 ${LOG_FILE} 2>&1 >/dev/null  || iRet=1
    #chown ${USER}:${USER_GROUP} ${LOG_FILE} 2>&1 >/dev/null  || iRet=1

    return $iRet
}

#########################################
# description: create file if the directory is not exist
# call by: log
# return: 0 create success
#         1 create fail
#
#########################################
function checkDir(){
    local iRet=0
    # if the log dir is exist
    if [ -d $LOG_DIR ] ;then
        mkdir -p ${LOG_DIR} 2>&1 >/dev/null  || iRet=1
        #chmod 700 ${LOG_DIR} -R 2>&1 >/dev/null  || iRet=1
        #chown ${USER}:${USER_GROUP} ${LOG_DIR} -R  2>&1 >/dev/null || iRet=1
    fi
    return $iRet
}

#########################################
# description: check the log file is full
# call by: log
# return: 0 can be logged
#         1 false disk is more than 90%
#
#########################################
function checkDiskSpace(){
    local code=0
    code="`df -h ${LOG_DIR}| grep -c -E \(9[1-9]\%\)\|\(100\%\)`"
    return $code
}