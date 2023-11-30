#!/usr/bin/bash

. ~/sh/CSWSException.sh

StopContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local containerName=$3

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_StopContainer.sh $containerName"    
}
Start StopContainer
StopContainer $1 $2 $3 && CSWSSuccess StopContainer || CSWSFailure StopContainer