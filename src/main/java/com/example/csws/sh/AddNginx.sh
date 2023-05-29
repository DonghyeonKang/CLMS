#!/usr/bin/bash

. ~/sh/Exception.sh

AddNginx()
{
    local hostName=$1
    local hostIp=$2 

    local serviceName=$3
    local domainName=$4
    local port=$5 
    local ip=$6
    ssh $hostName@$hostIp "sh sh/H_AddNginx.sh $serviceName $domainName $port $ip"    
}
Start AddNginx
AddNginx $1 $2 $3 $4 $5 $6 && CSWSSuccess AddNginx || CSWSFailure AddNginx