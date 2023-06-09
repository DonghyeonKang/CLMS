#!/usr/bin/bash

. ~/sh/CSWSException.sh

CreateContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local hPort=$3 # 호스트 포트
    local cPort=$4 # 컨테이너 포트
    local userName=$5 # 컨테이너를 실행시키는 유저 이름
    local userCode=$6 # 컨테이너를 실행시키는 유저 이름 뒤에 들어갈 코드
    local storageSize=$7
    local imageName=$8 # 실행시킬 컨테이너의 이미지

    ssh -t $hostName@$hostIp "echo '{user} ALL = (root) NOPASSWD:ALL' | bash sh/H_CreateContainer.sh $hPort $cPort $userName $userCode $storageSize $imageName"    
}
Start CreateContainer
CreateContainer $1 $2 $3 $4 $5 $6 $7 $8 && CSWSSuccess CreateContainer || CSWSFailure CreateContainer