#!/usr/bin/bash

. ~/sh/CSWSException.sh

RemoveContainer()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # 컨테?��?���? ?��?��?��?�� ?��??? ?���?
    local userCode=$4 # 컨테?��?���? ?��?��?��?�� ?��??? ?���? ?��?�� ?��?���? 코드

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_RemoveContainer.sh $userName $userCode"    
}
Start RemoveContainer
RemoveContainer $1 $2 $3 $4 && CSWSSuccess RemoveContainer || CSWSFailure RemoveContainer