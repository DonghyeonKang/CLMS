#!/user/bin/bash

. ~/sh/HostException.sh

H_RestartContaienr()
{

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "host - H_RestartContaienr >>>>>> 인수가 부족합니다."
        exit 1
    fi

    # 재시작할 도커 컨테이너 이름
    local containerName=$1

    # 컨테이너가 존재하는지 검사
    if [ -z `docker ps -qa -f name=$conianerName` ]; then
        echo "8" ; exit 1
    fi
    docker restart ${containerName} 

}

Start H_RestartContaienr
H_RestartContaienr $1 && HostSuccess H_RestartContaienr || HostFailure H_RestartContaienr