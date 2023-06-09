#!/user/bin/bash
#bash

. ~/sh/HostException.sh

H_RemoveContainer(){
    
    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 2 ]; then
        echo "host - H_RemoveContainer >>>>>> 인수가 부족합니다."
        exit 1
    fi

    local userName=$1 # 컨테이너를 실행시킨 유저 이름
    local userCode=$2 # 컨테이너를 실행시킨 유저 이름 뒤에 들어갈 코드

    # 삭제할 도커 컨테이너 이름
    local containerName=${userName}${userCode}

    # 유저 텍스트 파일
    local txtFile="${userName}.txt"

    # 유저들의 컨테이너 코드 정보를 저장할 디렉토리
    local userDir="Users" 

    # 컨테이너가 존재하는지 확인하고 삭제
    if [ -z `docker ps -qa -f name=$containerName` ]; then
        echo "8" ; exit 1
    else
        docker rm -f ${containerName}
    fi

   # Users 디렉토리로 이동
    cd ~/$userDir || exit 1

    # 사용자 텍스트 파일에서 해당 컨테이너의 코드 삭제
    ##  유저 코드가 텍스트 파일에 존재하는지 확인
    if  grep -qxF "$userCode" "$txtFile" ; then
        local remove=$(grep -nxF "$userCode" "$txtFile" | cut -d':' -f1)
        sed -i "${remove}d" "$txtFile" 
        echo "${containerName} 삭제"
    else
        echo "5" ; exit 1
    fi

    cd ~/

    # 방화벽도 닫음
    local PORTS=$(awk -F ':' '{ print $1 }' Ports/$containerName.txt)
    IFS=$'\n'
    for port in $PORTS
    do 
        echo `cat ~/etc/pw.txt` | sudo -S iptables -D INPUT -p tcp --dport $port -j ACCEPT
        echo "$port 삭제"
    done

    echo `cat ~/etc/pw.txt` | sudo -S netfilter-persistent save


    # # 개인키 존재 확인 후 삭제 csws에서 할 것
    # cd ~/Keys
    # if [ -e ~/Keys/$hostName/$containerName.pem ]; then
    #     rm ~/Keys/$hostName/$containerName.pem
    # else
    #     echo "9"; exit 1
    # fi

    cd ~/Ports
    if [ -e ~/Ports/$containerName.txt ]; then
        rm ~/Ports/$containerName.txt
    else
        echo "9"; exit 1
    fi

}

# 아마 없으면 인수 안 줘도 될 거임
# DeleteIPs() 
# {
#     local Port=$1 #3,3,3
#     local Ip=$2 #4,4,4

#     # local exe="$(for i in $Port[@]; do echo "sudo ufw "; done)"
#     # eval $exe
#     # IFS=$IFS_OLD

#     IFS=$',' read -r -a arr1 <<< "$Port"
#     IFS=$',' read -r -a arr2 <<< "$Ip"
#     # output="${arr1[@]} ${arr2[@]}"
#     # echo "$output $output1"
#     for i in "${!arr1[@]}"; do 
#         port="${arr1[i]}"
#         ip="${arr2[i]}"
#         local exe=`echo "sudo ufw delete allow from $ip to any port $port"`
#         eval $exe
#     done
#     IFS=$IFS_OLD
# }

Start H_RemoveContainer
H_RemoveContainer $1 $2 && HostSuccess H_RemoveContainer || HostFailure H_RemoveContainer
# $5 = 포트 $6 =ip 의 쌍