#!/usr/bin/bash

. ~/sh/CSWSException.sh

IsConnected()
{
    local hostName=$1
    local hostIp=$2 

    ssh -o StrictHostKeyChecking=no $hostName@$hostIp -p 9999 'cat ~/etc/logo.txt | wall'
}

Start IsConnected
IsConnected $1 $2 && CSWSSuccess IsConnected || CSWSFailure IsConnected