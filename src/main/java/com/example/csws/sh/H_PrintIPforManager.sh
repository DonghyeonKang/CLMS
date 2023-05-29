#!/usr/bin/bash
# ps -a로 출력한 모든 컨테이너의 IP

. ~/sh/Exception.sh

H_PrintIPforManager()
{
    local list=`docker ps -qa`
    if [ -z $list ]; then
        echo "host - H_PrintIPforManager >>>>>> 도커 컨테이너 리스트가 없습니다."
        exit 1
    fi

    docker inspect -f "{{ .NetworkSettings.IPAddress }}" $(docker ps -qa) 
}

H_PrintIPforManager && HostSuccess H_PrintIPforManager || HostFailure H_PrintIPforManager