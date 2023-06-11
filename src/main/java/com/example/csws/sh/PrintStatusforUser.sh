#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforUser()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # 컨테이너를 실행시킨 유저 이름

    ssh $hostName@$hostIp " docker ps -a --format 'table {{.Names}}\t{{.Status}}\t' | grep $userName[0-99999]"   

}
Start PrintStatusforUser
PrintStatusforUser $1 $2 $3 && CSWSSuccess PrintStatusforUser || CSWSFailure PrintStatusforUser