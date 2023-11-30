#!/usr/bin/bash

. ~/sh/CSWSException.sh

RestartContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local containerName=$3

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_RestartContainer.sh $containerName"    
}
Start RestartContainer
RestartContainer $1 $2 $3 && CSWSSuccess RestartContainer || CSWSFailure RestartContainer