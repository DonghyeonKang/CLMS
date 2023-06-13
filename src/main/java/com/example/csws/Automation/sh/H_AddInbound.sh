#!/usr/bin/bash
###bash
. ~/sh/HostException.sh

H_AddInbound()
{
    local containerName=$1

    cd ~/

    local LIST_PORT=`cat "Ports/$containerName.txt"`
    local LIST_PORT_txt="Ports/$containerName.txt"
    local newPorts=$2 #ex) 4444:45
    local storageSize=$3
    local os=$4 #(csws_ubuntu, csws_centos)


    IFS=':' read -r Port _ <<< $LIST_PORT
    local Hport=$Port
    IFS=$IFS_OLD
   
    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 4 ]; then
        echo "host - H_AddInbound >>>>>> 인수가 부족합니다."
        exit 1
    fi

    if grep -qxF "$newPorts" "$LIST_PORT_txt"; then
        echo "host - H_AddInbound >>>>>> 이미 열린 포트입니다."
    else
        echo "${newPorts}" >> "$LIST_PORT_txt"
    fi

    docker stop $containerName
    docker commit $containerName $os$Hport # 새로운 이미지 이름(운영체제 + 열 포트 번호)
    docker rm $containerName

    # Split
    IFS=$'\n'
    local exe="docker run --privileged -d -p $newPorts$(for port in $LIST_PORT; do echo " -p $port"; done) --name $containerName --storage-opt size=$storageSize $os$Hport tail -f /dev/null"
    eval $exe
    IFS=$IFS_OLD

    IFS=':' read -r newh _ <<< $newPorts
    local newHport=$newh
    IFS=$IFS_OLD

    echo `cat ~/etc/pw.txt` | sudo -S iptables -A INPUT -p tcp --dport $newHport -j ACCEPT
    echo `cat ~/etc/pw.txt` | sudo -S netfilter-persistent save

}
Start H_AddInbound
H_AddInbound $1 $2 $3 $4 && HostSuccess H_AddInbound || HostFailure H_AddInbound