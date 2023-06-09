#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintIPforManager()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp "sh ~/sh/H_PrintIPforManager.sh"    
}
Start PrintIPforManager
PrintIPforManager $1 $2 && CSWSSuccess PrintIPforManager || CSWSFailure PrintIPforManager