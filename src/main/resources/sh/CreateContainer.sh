#!/usr/bin/bash

. ~/sh/CSWSException.sh

CreateContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local hPort=$3 # ?Έ?€?Έ ?¬?Έ
    local cPort=$4 # μ»¨ν?΄? ?¬?Έ
    local userName=$5 # μ»¨ν?΄?λ₯? ?€???€? ? ??? ?΄λ¦?
    local userCode=$6 # μ»¨ν?΄?λ₯? ?€???€? ? ??? ?΄λ¦? ?€? ?€?΄κ°? μ½λ
    local storageSize=$7
    local imageName=$8 # ?€???¬ μ»¨ν?΄?? ?΄λ―Έμ??


    ssh $hostName@$hostIp -p 9999  "echo '{user} ALL = (root) NOPASSWD:ALL' | bash sh/H_CreateContainer.sh $hPort $cPort $userName $userCode $storageSize $imageName"    
}
Start CreateContainer
CreateContainer $1 $2 $3 $4 $5 $6 $7 $8 && CSWSSuccess CreateContainer || CSWSFailure CreateContainer