#!/usr/bin/bash
. ~/sh/CSWSException.sh
CheckContainerResource()
{
    local hostName=$1
    local hostIp=$2 
    local container=$3

    ssh $hostName@$hostIp "docker stats $container --no-stream --format 'table {{.MemUsage}}'"
    ssh $hostName@$hostIp "echo =================================================================="
    ssh $hostName@$hostIp "echo "Disk Usage""
    ssh $hostName@$hostIp "docker ps --size --filter "name=$container" --format "{{.Size}}""
    ssh $hostName@$hostIp "echo =================================================================="
    ssh $hostName@$hostIp "docker stats $container --no-stream --format 'table {{.CPUPerc}}'"

}

Start CheckContainerResource
CheckContainerResource $1 $2 $3 && CSWSSuccess CheckContainerResource || CSWSFailure CheckContainerResource