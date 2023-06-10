#!/usr/bin/bash
. ~/sh/CSWSException.sh
CheckContainerResource()
{
    local hostName=$1
    local hostIp=$2 
    local container=$3

    ssh $hostName@$hostIp "mkdir -p ~/etc/info/con"
    ssh $hostName@$hostIp "touch ~/etc/info/con/$container.txt"

    ssh $hostName@$hostIp "docker stats $container --no-stream --format 'table {{.MemUsage}}' > ~/etc/info/con/$container.txt"
    ssh $hostName@$hostIp "echo ================================================================== >> ~/etc/info/con/$container.txt"
    ssh $hostName@$hostIp "echo "Disk Usage" >> ~/etc/info/con/$container.txt"
    ssh $hostName@$hostIp "docker ps --size --filter "name=$container" --format "{{.Size}}" >> ~/etc/info/con/$container.txt"
    ssh $hostName@$hostIp "echo ================================================================== >> ~/etc/info/con/$container.txt"
    ssh $hostName@$hostIp "docker stats $container --no-stream --format 'table {{.CPUPerc}}' >> ~/etc/info/con/$container.txt"


    # info 디렉토리가 존재하지 않으면 생성
    if [ ! -d ~/info/$hostName/con ]; then
        mkdir -p ~/info/$hostName/con
    fi

    if [ -f ~/info/con/$hostName/$container.txt ]; then
        rm ~/info/$hostName/con/$container.txt
    fi

    scp $hostName@$hostIp:~/etc/info/con/$container.txt ~/info/$hostName/con/  
}

Start CheckContainerResource
CheckContainerResource $1 $2 $3 && CSWSSuccess CheckContainerResource || CSWSFailure CheckContainerResource