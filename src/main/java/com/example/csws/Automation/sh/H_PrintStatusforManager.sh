#!/usr/bin/bash
# ps -a로 출력한 모든 컨테이너의 상태
. ~/sh/HostException.sh

H_PrintStatusforManager()
{
    # info 디렉토리가 존재하지 않으면 생성
    if [ ! -d ~/etc/info ]; then
        mkdir -p ~/etc/info
    fi

    # 파일이 존재하지 않으면 생성
    if [ ! ~/etc/info/PrintStatusforManager.txt ]; then
        touch ~/etc/info/PrintStatusforManager.txt
    fi


    docker ps -a --format "table {{.Names}}\t{{.Status}}\t" > ~/etc/info/PrintStatusforManager.txt 2>&1
}

Start H_PrintStatusforManager
H_PrintStatusforManager && HostSuccess H_PrintStatusforManager || HostFailure H_PrintStatusforManager