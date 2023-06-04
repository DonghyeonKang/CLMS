#!/user/bin/bash
. ~/sh/HostException.sh

H_StartContainer(){

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 1 ]; then
        echo "host - H_StartContainer >>>>>> 인수가 부족합니다."
        exit 1
    fi

    # 컨테이너가 존재하는지 검사
    if [ -z `docker ps -qa -f name=$conianerName` ]; then
        echo "host - H_StartContainer >>>>>> 컨테이너가 존재하지 않습니다." ; exit 1
    fi

    # 실행을 시작할 도커 컨테이너 이름
    local containerName=$1

    docker start ${containerName} || exit 1
}

Start H_StartContainer
H_StartContainer $1 && HostSuccess H_StartContainer || HostFailure H_StartContainer