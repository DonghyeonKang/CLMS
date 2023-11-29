#!/usr/bin/bash

. ~/sh/HostException.sh

H_CreateContainer(){
    local hPort=$1 # 호스트 포트
    local cPort=$2 # 컨테이너 포트
    local userName=$3 # 컨테이너를 실행시키는 유저 이름
    local userCode=$4 # 컨테이너를 실행시키는 유저 이름 뒤에 들어갈 코드
    local storageSize=$5
    local imageName=$6 # 실행시킬 컨테이너의 이미지

    local userDir="Users" # 유저들의 컨테이너 코드 정보를 저장할 디렉토리
    local txtFile="${userName}.txt"

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 6 ]; then
        echo "host - H_CreateContainer >>>>>> 인수가 부족합니다."
        exit 1
    fi

    # 유저 코드가 5자리 수를 넘는지 확인
    if [ $userCode -gt 99999 ]; then
        echo "host - H_CreateContainer >>>>>> 유저 코드는 5자리 이하로 입력해 주세요."
        exit 1
    fi

    cd ~/

    # Users 디렉토리가 존재하지 않으면 생성
    if [ ! -d ${userDir} ]; then
        mkdir ${userDir}
    fi

    # Users 디렉토리로 이동
    cd $userDir

    # 해당하는 유저의 텍스트 파일이 존재하지 않으면 생성
    if [ ! -e "$txtFile" ]; then
        touch "$txtFile"
    fi

    # 유저 코드가 텍스트 파일에 존재하는지 확인
    if grep -qxF "$userCode" "$txtFile"; then
        echo "host - H_CreateContainer >>>>>> 이미 존재하는 유저 코드입니다."
        exit 1
    else
        echo "${userCode}" >> "$txtFile"
    fi

    # 도커 컨테이너 실행
    docker run -d --privileged -p $hPort:$cPort --name ${userName}${userCode} -u csws --storage-opt size=$storageSize $imageName tail -f /dev/null
    # docker run -it -p $hPort:$cPort --name ${userName}${userCode} $imageName || exit
}

# 랜덤 값을 입력받아 csws의 포트를 연다.

OpenPort()
{
    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "host - H_CreateContainer >>>>>> 인수가 부족합니다."
        exit 1
    fi

    # 스프링에서 랜덤으로 지정한 포트 값을 받아옴
    local hPort=$1

    echo `cat ~/etc/pw.txt` | sudo -S iptables -A INPUT -p tcp --dport $hPort -j ACCEPT
    echo `cat ~/etc/pw.txt` | sudo -S netfilter-persistent save
}

SavePort()
{
    local hPort=$1 # 호스트 포트
    local cPort=$2 # 컨테이너 포트

    local userName=$3 # 컨테이너를 실행시키는 유저 이름
    local userCode=$4 # 컨테이너를 실행시키는 유저 이름 뒤에 들어갈 코드

    local portDir="Ports"
    local portTxt="$userName$userCode.txt"

    cd ~/

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 4 ]; then
        echo "host - H_CreateContainer >>>>>> 인수가 부족합니다."
        exit 1
    fi

    # Users 디렉토리가 존재하지 않으면 생성
    if [ ! -d ${portDir} ]; then
        mkdir ${portDir}
    fi

    cd $portDir

    if [ ! -e "$portTxt" ]; then
        touch "$portTxt"
    fi

    # 유저 포트가 텍스트 파일에 존재하는지 확인
    if grep -qxF "$hPort" "$portTxt"; then
        echo "host - H_CreateContainer >>>>>> 이미 사용 중인 유저 포트입니다." 
        exit 1
    else
        echo "$hPort:$cPort" >> "$portTxt"
    fi
}
Start H_CreateContainer
H_CreateContainer $1 $2 $3 $4 $5 $6 && OpenPort $1 && SavePort $1 $2 $3 $4 && HostSuccess H_CreateContainer || HostFailure H_CreateContainer