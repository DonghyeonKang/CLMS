#!/usr/bin/bash

. ~/sh/CSWSException.sh

# 컨테?��?�� ?��?��?�� ?�� ?��것도 csws?��?�� ?��?��?��?��?�� ?��?��.
SendPublickey()
{
    local hostName=$1 # ?��?��?�� ?���? ?���?
    local hostIp=$2 # ?��?��?�� ?���? IP 주소
    local conName=$3
    local keyName=$4

    ssh $hostName@$hostIp -p 9999 "mkdir Keys"

    scp -P 9999 ~/Keys/$hostName/$keyName.pub $hostName@$hostIp:~/Keys/ 

    ssh $hostName@$hostIp -p 9999 "sh ~/sh/H_SendPublickey.sh $keyName $conName"
}
Start SendPublickey
SendPublickey $1 $2 $3 $4 && CSWSSuccess SendPublickey || CSWSFailure SendPublickey