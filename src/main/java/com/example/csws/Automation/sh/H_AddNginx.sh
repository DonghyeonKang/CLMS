#!/usr/bin/bash

. ~/sh/HostException.sh

H_AddNginx()
{
    local containerName=$1
    local domain=$2 ## aimyon
    local port=$3 # 프록시패스 할 포트
    local ip=$4 # 호스트의 ip

    if [ $# -lt 4 ]; then
        echo "host - H_AddNginx >>>>>> 인수가 부족합니다." 
        exit 1
    fi

    # 같은 이름의 파일이 이미 존재할 경우
    if [ -f /etc/nginx/conf.d/$containerName.conf ]; then
        echo "host - H_AddNginx >>>>>> 같은 이름의 conf 파일이 이미 존재합니다." 
        exit 1
    fi

    echo `cat ~/etc/pw.txt` | sudo -S cp ~/etc/nginxTemplet /etc/nginx/conf.d/$containerName.conf

  
    echo `cat ~/etc/pw.txt` | sudo -S sed -ri "s/.*server_name/\tserver_name $domain www.$domain;/g" /etc/nginx/conf.d/$containerName.conf
    echo `cat ~/etc/pw.txt` | sudo -S sed -ri "s/.*proxy_pass/\tproxy_pass http:\/\/$ip:$port;/g" /etc/nginx/conf.d/$containerName.conf

    echo `cat ~/etc/pw.txt` | sudo -S nginx -s reload
}
Start H_AddNginx
H_AddNginx $1 $2 $3 $4 && HostSuccess H_AddNginx || HostFailure H_AddNginx