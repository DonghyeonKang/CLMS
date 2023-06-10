#!/usr/bin/bash
. ~/sh/CSWSException.sh
CheckServerResource()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp "mkdir -p ~/etc/info/server"
    ssh $hostName@$hostIp "touch ~/etc/info/server/info.txt"
    ssh $hostName@$hostIp "echo =================================Memory================================= > ~/etc/info/server/info.txt"
    ssh $hostName@$hostIp "cat /proc/meminfo | grep Mem >> ~/etc/info/server/info.txt"
    ssh $hostName@$hostIp "echo =================================Disk================================= >> ~/etc/info/server/info.txt"
    ssh $hostName@$hostIp "df -h >> ~/etc/info/server/info.txt"
    ssh $hostName@$hostIp "echo =================================CPU================================= >> ~/etc/info/server/info.txt"
    ssh $hostName@$hostIp "mpstat | tail -1 | awk '{print 100-\$NF}' >> ~/etc/info/server/info.txt"

    # info 디렉토리가 존재하지 않으면 생성
    if [ ! -d ~/info/$hostName/server ]; then
        mkdir -p ~/info/$hostName/server
    fi

    if [ -f ~/info/$hostName/server/info.txt ]; then
        rm ~/info/$hostName/server/info.txt
    fi

    scp $hostName@$hostIp:~/etc/info/server/info.txt ~/info/$hostName/server 
}

Start CheckServerResource
CheckServerResource $1 $2 && CSWSSuccess CheckServerResource || CSWSFailure CheckServerResource