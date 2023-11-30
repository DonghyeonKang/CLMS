#!/usr/bin/bash

. ~/sh/CSWSException.sh

CreateContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local hPort=$3 # ?˜¸?Š¤?Š¸ ?¬?Š¸
    local cPort=$4 # ì»¨í…Œ?´?„ˆ ?¬?Š¸
    local userName=$5 # ì»¨í…Œ?´?„ˆë¥? ?‹¤?–‰?‹œ?‚¤?Š” ?œ ??? ?´ë¦?
    local userCode=$6 # ì»¨í…Œ?´?„ˆë¥? ?‹¤?–‰?‹œ?‚¤?Š” ?œ ??? ?´ë¦? ?’¤?— ?“¤?–´ê°? ì½”ë“œ
    local storageSize=$7
    local imageName=$8 # ?‹¤?–‰?‹œ?‚¬ ì»¨í…Œ?´?„ˆ?˜ ?´ë¯¸ì??


    ssh $hostName@$hostIp -p 9999  "echo '{user} ALL = (root) NOPASSWD:ALL' | bash sh/H_CreateContainer.sh $hPort $cPort $userName $userCode $storageSize $imageName"    
}
Start CreateContainer
CreateContainer $1 $2 $3 $4 $5 $6 $7 $8 && CSWSSuccess CreateContainer || CSWSFailure CreateContainer