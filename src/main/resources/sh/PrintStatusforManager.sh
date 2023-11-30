#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforManager()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp -p 9999 "docker ps -a --format 'table {{.Names}}\t{{.Status}}\t'" 
}
Start PrintStatusforManager
PrintStatusforManager $1 $2 && CSWSSuccess PrintStatusforManager || CSWSFailure PrintStatusforManager