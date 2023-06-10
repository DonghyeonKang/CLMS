#!/usr/bin/bash
#bash

. ~/sh/HostException.sh

H_DeleteInbound()
{
    local containerName=$1

    cd ~/

    local LIST_PORT_txt="Ports/$containerName.txt"

    local delPorts=$2 #ex) 4444:45
    local storageSize=$3
    local os=$4 #(csws_ubuntu, csws_centos)

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 4 ]; then
        echo "host - H_DeleteInbound >>>>>> 인수가 부족합니다."
        exit 1
    fi

    cd ~/

    if  grep -qxF "$delPorts" "$LIST_PORT_txt"
    then
        local remove=$(grep -nxF "$delPorts" "$LIST_PORT_txt" | cut -d':' -f1)
        sed -i "${remove}d" "$LIST_PORT_txt" 
        echo "host - H_DeleteInbound >>>>>> ${delPorts} 삭제"
    else
        echo "host - H_DeleteInbound >>>>>> 해당 포트는 열리지 않았습니다."
        exit 1
    fi


    local LIST_PORT=`cat "Ports/$containerName.txt"`

    IFS=':' read -r Port _ <<< $LIST_PORT
    local Hport=$Port
    IFS=$IFS_OLD

    docker stop $containerName
    docker commit $containerName $os$Hport # 새로운 이미지 이름(운영체제 + 열 포트 번호)
    docker rm $containerName

    IFS=$'\n'
    local exe="docker run -it --privileged -d $(for port in $LIST_PORT; do echo "-p $port "; done)--name $containerName --storage-opt size=$storageSize $os$Hport /sbin/init"
    eval $exe
    IFS=$IFS_OLD

    IFS=':' read -r delh _ <<< $delPorts
    local delHport=$delh
    IFS=$IFS_OLD


    # if [ -n "$IpAddress" ]; then
    # ip만 막으려면... docker-user에서 아이피를 막아야 할 듯

    #     sudo ufw delete allow from $IpAddress to any port $delHport 
    #     sudo ufw disable
    #     sudo ufw enable
    
    echo `cat ~/etc/pw.txt` | sudo -S iptables -D INPUT -p tcp --dport $delHport -j ACCEPT
    echo `cat ~/etc/pw.txt` | sudo -S netfilter-persistent save
}
Start H_DeleteInbound
H_DeleteInbound $1 $2 $3 $4 && HostSuccess H_DeleteInbound || HostFailure H_DeleteInbound