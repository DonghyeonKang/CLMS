#!/usr/bin/bash

. ~/sh/CSWSException.sh

StartContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    # ?‹¤?–‰?„ ?‹œ?‘?•  ?„ì»? ì»¨í…Œ?´?„ˆ ?´ë¦?
    local containerName=$3

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_StartContainer.sh $containerName"    
}
Start StartContainer
StartContainer $1 $2 $3 && CSWSSuccess StartContainer || CSWSFailure StartContainer