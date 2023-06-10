#!/usr/bin/bash

. ~/sh/CSWSException.sh

AddNginx()
{
    local hostName=$1
    local hostIp=$2 

    local serviceName=$3
    local domainName=$4
    local port=$5 

    ssh $hostName@$hostIp "bash ~/sh/H_AddNginx.sh $serviceName $domainName $port $hostIp"    
}
Start AddNginx
AddNginx $1 $2 $3 $4 $5 && CSWSSuccess AddNginx || CSWSFailure AddNginx