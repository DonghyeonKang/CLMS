#!/usr/bin/bash
. ~/sh/CSWSException.sh
CheckServerResource()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp "echo =================================Memory=================================" 
    ssh $hostName@$hostIp "cat /proc/meminfo | grep Mem" 
    ssh $hostName@$hostIp "echo =================================Disk================================="
    ssh $hostName@$hostIp "df -h"
    ssh $hostName@$hostIp "echo =================================CPU=================================" 
    ssh $hostName@$hostIp "mpstat | tail -1 | awk '{print 100-\$NF}'"

}

Start CheckServerResource
CheckServerResource $1 $2 && CSWSSuccess CheckServerResource || CSWSFailure CheckServerResource