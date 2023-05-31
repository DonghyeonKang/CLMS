#!/usr/bin/bash

. ~/sh/CSWSException.sh

RemoveContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # 컨테이너를 실행시킨 유저 이름
    local userCode=$4 # 컨테이너를 실행시킨 유저 이름 뒤에 들어갈 코드

    ssh $hostName@$hostIp "sh sh/H_RemoveContainer.sh $userName $userCode"    
}
Start RemoveContainer
RemoveContainer $1 $2 $3 $4 && CSWSSuccess RemoveContainer || CSWSFailure RemoveContainer