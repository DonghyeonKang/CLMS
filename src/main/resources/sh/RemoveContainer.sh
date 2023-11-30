#!/usr/bin/bash

. ~/sh/CSWSException.sh

RemoveContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # ì»¨í…Œ?´?„ˆë¥? ?‹¤?–‰?‹œ?‚¨ ?œ ??? ?´ë¦?
    local userCode=$4 # ì»¨í…Œ?´?„ˆë¥? ?‹¤?–‰?‹œ?‚¨ ?œ ??? ?´ë¦? ?’¤?— ?“¤?–´ê°? ì½”ë“œ

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_RemoveContainer.sh $userName $userCode"    
}
Start RemoveContainer
RemoveContainer $1 $2 $3 $4 && CSWSSuccess RemoveContainer || CSWSFailure RemoveContainer