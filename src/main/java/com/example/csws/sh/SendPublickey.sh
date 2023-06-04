#!/usr/bin/bash

. ~/sh/CSWSException.sh

# 컨테이너 생성할 때 이것도 csws에서 실행되어야 한다.
SendPublickey()
{
    local hostName=$1 # 사용자 서버 이름
    local hostIp=$2 # 사용자 서버 IP 주소
    local conName=$3
    local keyName=$4

    ssh $hostName@$hostIp "mkdir Keys"
    scp ~/Keys/$hostName/$keyName.pub $hostName@$hostIp:~/Keys/ 
    ssh $hostName@$hostIp "sh H_SendPublickey.sh $keyName $conName"
}
Start SendPublickey
SendPublickey $1 $2 $3 $4 && CSWSSuccess SendPublickey || CSWSFailure SendPublickey