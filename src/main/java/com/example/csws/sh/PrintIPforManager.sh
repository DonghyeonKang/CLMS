#!/usr/bin/bash
# ps -a로 출력한 모든 컨테이너의 IP

PrintIPforManager()
{
    local list=`docker ps -qa`
    if [ -z $list ]; then
        echo "7" #도커 컨테이너 리스트가 없습니다.
        exit 1
    fi

    docker inspect -f "{{ .NetworkSettings.IPAddress }}" $(docker ps -qa) 
}

PrintIPforManager && echo "99"