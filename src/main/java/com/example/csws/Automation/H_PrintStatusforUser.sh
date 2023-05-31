#!/usr/bin/bash
# ps로 출력한 유저의 모든 컨테이너 상태

. ~/sh/Exception.sh

H_PrintStatusforUser(){

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "host - H_PrintStatusforUser >>>>>> 인수가 부족합니다."
        exit 1
    fi

    local userName=$1 # 컨테이너를 실행시킨 유저 이름
    docker ps -a --format "table {{.Names}}\t{{.Status}}\t" | grep "$userName[0-99999]"
}

Start H_PrintStatusforUser
H_PrintStatusforUser $1 && HostSuccess H_PrintStatusforUser || HostFailure H_PrintStatusforUser