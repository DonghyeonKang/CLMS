#!/usr/bin/bash

. ~/sh/HostException.sh
H_DeleteNginx()
{
    local serviceName=$1

    # Nginx conf 파일을 삭제합니다.

    if [ ! -f /etc/nginx/conf.d/$serviceName.conf ]; then
        echo "host - H_DeleteNginx >>>>>> 파일이 존재하지 않습니다." 
        exit 1
    fi

    echo `cat ~/etc/pw.txt` | sudo -S rm /etc/nginx/conf.d/$1.conf
    nginx -s reload
}

Start H_DeleteNginx
H_DeleteNginx $1 && HostSuccess H_DeleteNginx || HostFailure H_DeleteNginx