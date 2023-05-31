#!/usr/bin/bash
. ~/sh/CSWSException.sh

IsConnected()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp "cat logo.txt"
}

Start IsConnected
IsConnected $1 $2 && CSWSSuccess IsConnected || CSWSFailure IsConnected