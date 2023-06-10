#!/usr/bin/bash

. ~/sh/CSWSException.sh

PrintStatusforManager()
{
    local hostName=$1
    local hostIp=$2 

    ssh $hostName@$hostIp "sh ~/sh/H_PrintStatusforManager.sh" 

    # hostName ���丮�� �������� ������ ����
    if [ ! -d ~/info/$hostName ]; then
        mkdir -p ~/info/$hostName
    fi

    scp $hostName@$hostIp:~/etc/info/PrintStatusforManager.txt ~/info/$hostName/   
}
Start PrintStatusforManager
PrintStatusforManager $1 $2 && CSWSSuccess PrintStatusforManager || CSWSFailure PrintStatusforManager