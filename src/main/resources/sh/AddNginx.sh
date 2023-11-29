#!/usr/bin/bash

. ~/sh/CSWSException.sh

AddNginx()
{
    local hostName=$1
    local hostIp=$2 

    local containerName=$3
    local domain=$4
    local port=$5 

    ssh $hostName@$hostIp -p 9999 "bash ~/sh/H_AddNginx.sh $containerName $domain $port $hostIp"    
}
Start AddNginx
AddNginx $1 $2 $3 $4 $5 && CSWSSuccess AddNginx || CSWSFailure AddNginx
