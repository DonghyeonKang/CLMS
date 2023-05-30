#!/usr/bin/bash
# ps -a로 출력한 모든 컨테이너의 상태
. ~/sh/HostException.sh

H_PrintStatusforManager()
{
    docker ps -a --format "table {{.Names}}\t{{.Status}}\t"
}

Start H_PrintStatusforManager
H_PrintStatusforManager && HostSuccess H_PrintStatusforManager || HostFailure H_PrintStatusforManager