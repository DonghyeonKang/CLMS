#!/usr/bin/bash

. ~/sh/CSWSException.sh

AddInbound()
{
    local hostName=$1
    local hostIp=$2 

    local containerName=$3
    local newPorts=$4 #ex) 4444:45
    local storageSize=$5
    local os=$6 #(csws_ubuntu, csws_centos)

    ssh $hostName@$hostIp -p 9999 "bash ~/sh/H_AddInbound.sh $containerName $newPorts $storageSize $os"
}
Start AddInbound
AddInbound $1 $2 $3 $4 $5 $6 && CSWSSuccess AddInbound || CSWSFailure AddInbound