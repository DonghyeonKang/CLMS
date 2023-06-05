#!/usr/bin/bash

AddNginx()
{
    local serviceName=$1 # 파일 이름
    local domainName=$2 ## aimyon.com (www 제외)
    local port=$3 # 포트포워딩 할 포트
    local ip=$4 # 아이피... 이 서버의 아이피

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 4 ]; then
        echo "3" #인수가 부족합니다.
        exit 1
    fi

    ## 이미 사용 중인 서비스 이름입니다

    if [ -f /etc/nginx/conf.d/$serviceName.conf ]; then
        echo "이미 사용 중인 서비스 이름입니다." 
        exit 1
    fi

    sudo cp ~/sh/nginxTemplet /etc/nginx/conf.d/$serviceName.conf

    ## 도메인 추가 작업
    sudo sed -ri "s/.*server_name/\tserver_name $domainName www.$domainName;/g" /etc/nginx/conf.d/$serviceName.conf
    sudo sed -ri "s/.*proxy_pass/\tproxy_pass http:\/\/$ip:$port;/g" /etc/nginx/conf.d/$serviceName.conf

    sudo nginx -s reload
}

AddNginx $1 $2 $3 $4