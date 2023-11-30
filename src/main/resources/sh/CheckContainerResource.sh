#!/usr/bin/bash
. ~/sh/CSWSException.sh
CheckContainerResource()
{
    local hostName=$1
    local hostIp=$2 
    local container=$3

    ssh $hostName@$hostIp -p 9999 "docker stats $container --no-stream --format 'table {{.MemUsage}}'"
    ssh $hostName@$hostIp -p 9999 "echo =================================================================="
    ssh $hostName@$hostIp -p 9999 "echo "Disk Usage""
    ssh $hostName@$hostIp -p 9999 "docker ps --size --filter "name=$container" --format "{{.Size}}""
    ssh $hostName@$hostIp -p 9999 "echo =================================================================="
    ssh $hostName@$hostIp -p 9999 "docker stats $container --no-stream --format 'table {{.CPUPerc}}'"

}

Start CheckContainerResource
CheckContainerResource $1 $2 $3 && CSWSSuccess CheckContainerResource || CSWSFailure CheckContainerResource