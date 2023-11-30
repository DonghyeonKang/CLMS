#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforUser()
{
    local hostName=$1
    local hostIp=$2 
    
    local userName=$3 # Ïª®ÌÖå?ù¥?ÑàÎ•? ?ã§?ñâ?ãú?Ç® ?ú†??? ?ù¥Î¶?

    ssh $hostName@$hostIp -p 9999 " docker ps -a --format 'table {{.Names}}\t{{.Status}}\t' | grep $userName[0-99999]"   

}
Start PrintStatusforUser
PrintStatusforUser $1 $2 $3 && CSWSSuccess PrintStatusforUser || CSWSFailure PrintStatusforUser