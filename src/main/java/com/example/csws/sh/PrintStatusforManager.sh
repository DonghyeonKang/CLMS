#!/usr/bin/bash
# ps -a로 출력한 모든 컨테이너의 상태

docker ps -a --format "table {{.Names}}\t{{.Status}}\t" && echo "99"