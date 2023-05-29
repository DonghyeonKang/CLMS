#!/usr/bin/bash
# ps -a로 출력한 모든 컨테이너의 상태
. ~/sh/Exception.sh

docker ps -a --format "table {{.Names}}\t{{.Status}}\t" && HostSuccess H_PrintStatusforManager || HostFailure H_PrintStatusforManager