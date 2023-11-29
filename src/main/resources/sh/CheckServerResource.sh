#!/usr/bin/bash
. ~/sh/CSWSException.sh
CheckServerResource()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp -p 9999 "echo =================================Memory=================================" 
    ssh $hostName@$hostIp -p 9999 "cat /proc/meminfo | grep Mem" 
    ssh $hostName@$hostIp -p 9999  "echo =================================Disk================================="
    ssh $hostName@$hostIp -p 9999  "df -h"
    ssh $hostName@$hostIp -p 9999  "echo =================================CPU=================================" 
    ssh $hostName@$hostIp -p 9999  "mpstat | tail -1 | awk '{print 100-\$NF}'"

}

Start CheckServerResource
CheckServerResource $1 $2 && CSWSSuccess CheckServerResource || CSWSFailure CheckServerResource