#!/usr/bin/bash

DeleteNginx()
{
    local serviceName=$1

    ## 삭제하려고 하는 서비스가 없습니다.

    if [ ! -f /etc/nginx/conf.d/$serviceName.conf ]; then
        echo "삭제하려고 하는 서비스가 없습니다." 
        exit 1
    fi

    sudo rm /etc/nginx/conf.d/$1.conf
    nginx -s reload
}

DeleteNginx $1