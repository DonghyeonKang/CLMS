#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforUser()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # 컨테이너를 실행시킨 유저 이름

    ssh $hostName@$hostIp "sh sh/H_PrintStatusforUser.sh $userName"    
}
Start PrintStatusforUser
PrintStatusforUser $1 $2 $3 && CSWSSuccess PrintStatusforUser || CSWSFailure PrintStatusforUser