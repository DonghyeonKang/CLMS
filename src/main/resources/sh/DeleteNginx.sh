#!/usr/bin/bash

. ~/sh/CSWSException.sh

DeleteNginx()
{
    local hostName=$1
    local hostIp=$2 
    
    local serviceName=$3

    ssh $hostName@$hostIp -p 9999 "bash ~/sh/H_DeleteNginx.sh $serviceName"    
}
Start DeleteNginx
DeleteNginx $1 $2 $3 && CSWSSuccess DeleteNginx || CSWSFailure DeleteNginx