#!/usr/bin/bash
# ps로 출력한 유저의 모든 컨테이너 IP

. ~/sh/Exception.sh

H_PrintIPforUser(){

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "host - H_PrintIPforUser >>>>>> 인수가 부족합니다."
        exit 1
    fi

    local list=`docker ps -qa`
    if [ -z $list ]; then
        echo "host - H_PrintIPforUser >>>>>> 도커 컨테이너 리스트가 없습니다."
        exit 1
    fi


    local userName=$1 # 컨테이너를 실행시킨 유저 이름
    docker inspect -f "{{ .Name }} -> {{ .NetworkSettings.IPAddress }}" $(docker ps -qa) | grep "$userName[0-99999]" 
}

H_PrintIPforUser $1 && HostSuccess H_PrintIPforUser || HostFailure H_PrintIPforUser