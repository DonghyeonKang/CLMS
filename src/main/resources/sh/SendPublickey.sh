#!/usr/bin/bash

. ~/sh/CSWSException.sh

# ì»¨í…Œ?´?„ˆ ?ƒ?„±?•  ?•Œ ?´ê²ƒë„ csws?—?„œ ?‹¤?–‰?˜?–´?•¼ ?•œ?‹¤.
SendPublickey()
{
    local hostName=$1 # ?‚¬?š©? ?„œë²? ?´ë¦?
    local hostIp=$2 # ?‚¬?š©? ?„œë²? IP ì£¼ì†Œ
    local conName=$3
    local keyName=$4

    ssh $hostName@$hostIp -p 9999 "mkdir Keys"

    scp -P 9999 ~/Keys/$hostName/$keyName.pub $hostName@$hostIp:~/Keys/ 

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_SendPublickey.sh $keyName $conName"
}
Start SendPublickey
SendPublickey $1 $2 $3 $4 && CSWSSuccess SendPublickey || CSWSFailure SendPublickey