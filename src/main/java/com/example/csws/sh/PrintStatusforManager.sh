#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforManager()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp "sh sh/H_PrintStatusforManager.sh"    
}
Start PrintStatusforManager
PrintStatusforManager $1 $2 && CSWSSuccess PrintStatusforManager || CSWSFailure PrintStatusforManager