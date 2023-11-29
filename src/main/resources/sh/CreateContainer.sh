#!/usr/bin/bash

. ~/sh/CSWSException.sh

CreateContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local hPort=$3 # ?��?��?�� ?��?��
    local cPort=$4 # 컨테?��?�� ?��?��
    local userName=$5 # 컨테?��?���? ?��?��?��?��?�� ?��??? ?���?
    local userCode=$6 # 컨테?��?���? ?��?��?��?��?�� ?��??? ?���? ?��?�� ?��?���? 코드
    local storageSize=$7
    local imageName=$8 # ?��?��?��?�� 컨테?��?��?�� ?��미�??


    ssh $hostName@$hostIp -p 9999  "echo '{user} ALL = (root) NOPASSWD:ALL' | bash sh/H_CreateContainer.sh $hPort $cPort $userName $userCode $storageSize $imageName"    
}
Start CreateContainer
CreateContainer $1 $2 $3 $4 $5 $6 $7 $8 && CSWSSuccess CreateContainer || CSWSFailure CreateContainer