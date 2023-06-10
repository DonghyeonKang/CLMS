#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforUser()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # 컨테이너를 실행시킨 유저 이름

    ssh $hostName@$hostIp "sh ~/sh/H_PrintStatusforUser.sh $userName"   

     # 디렉토리가 존재하지 않으면 생성
    if [ ! -d ~/info/$hostName/$userName ]; then
        mkdir -p ~/info/$hostName/$userName
    fi
 
    scp $hostName@$hostIp:~/etc/info/$userName/PrintStatusforUser.txt ~/info/$hostName/$userName/
}
Start PrintStatusforUser
PrintStatusforUser $1 $2 $3 && CSWSSuccess PrintStatusforUser || CSWSFailure PrintStatusforUser