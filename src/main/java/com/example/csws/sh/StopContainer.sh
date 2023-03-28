#!/user/bin/bash

StopContainer()
{

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "3" #인수가 부족합니다.
        exit 1
    fi

    # 컨테이너가 존재하는지 검사
    if [ -z `docker ps -qa -f name=$conianerName` ]; then
        echo "8" ; exit 1
    fi

    # 실행을 중지할 도커 컨테이너 이름
    local containerName=$1

    docker stop ${containerName}
}

StopContainer $1 && echo "99"