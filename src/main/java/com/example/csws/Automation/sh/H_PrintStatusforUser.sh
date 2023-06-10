#!/usr/bin/bash
# ps로 출력한 유저의 모든 컨테이너 상태

. ~/sh/HostException.sh

H_PrintStatusforUser(){

    local userName=$1 # 컨테이너를 실행시킨 유저 이름

    # info 디렉토리가 존재하지 않으면 생성
    if [ ! -d ~/etc/info/$userName ]; then
        mkdir -p ~/etc/info/$userName
    fi

    # 파일이 존재하지 않으면 생성
    if [ ! ~/etc/info/$userName/PrintStatusforUser.txt ]; then
        touch ~/etc/info/$userName/PrintStatusforUser.txt
    fi

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "host - H_PrintStatusforUser >>>>>> 인수가 부족합니다."
        exit 1
    fi

    docker ps -a --format "table {{.Names}}\t{{.Status}}\t" | grep "$userName[0-99999]" > ~/etc/info/$userName/PrintStatusforUser.txt 2>&1
}

Start H_PrintStatusforUser
H_PrintStatusforUser $1 && HostSuccess H_PrintStatusforUser || HostFailure H_PrintStatusforUser