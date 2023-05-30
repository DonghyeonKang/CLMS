#!/usr/bin/bash

. ~/sh/HostException.sh
H_DeleteNginx()
{
    local serviceName=$1

    # Nginx conf 파일을 삭제합니다.

    if [ ! -f /etc/nginx/conf.d/$serviceName.conf ]; then
        echo "host - H_DeleteNginx >>>>>> 인수가 부족합니다." 
        exit 1
    fi

    sudo rm /etc/nginx/conf.d/$1.conf
    nginx -s reload
}

Start H_DeleteNginx
H_DeleteNginx $1 && HostSuccess H_DeleteNginx || HostFailure H_DeleteNginx