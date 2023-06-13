#!/usr/bin/bash

. ~/sh/CSWSException.sh

IsConnected()
{
    local hostName=$1
    local hostIp=$2 

    ssh -o StrictHostKeyChecking=no $hostName@$hostIp 'cat ~/etc/logo2.txt | wall'
    scp ~/Automation/Automation.tar $hostName@$hostIp:~/
}

Start IsConnected
IsConnected $1 $2 && CSWSSuccess IsConnected || CSWSFailure IsConnected