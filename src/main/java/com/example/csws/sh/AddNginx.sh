#!/usr/bin/bash

. ~/sh/CSWSException.sh

AddNginx()
{
    local hostName=$1
    local hostIp=$2 

    local domainName=$3
    local port=$4 

    ssh $hostName@$hostIp "bash ~/sh/H_AddNginx.sh $domainName $port $hostIp"    
}
Start AddNginx
AddNginx $1 $2 $3 $4 && CSWSSuccess AddNginx || CSWSFailure AddNginx