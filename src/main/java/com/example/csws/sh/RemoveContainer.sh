#!/user/bin/bash

RemoveContainer(){

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 3 ]; then
        echo "3" #인수가 부족합니다.
        exit 1
    fi

    local userName=$1 # 컨테이너를 실행시킨 유저 이름
    local userCode=$2 # 컨테이너를 실행시킨 유저 이름 뒤에 들어갈 코드
    local hPort=$3

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
    cd ~/sh/$userDir || exit 1

    # 사용자 텍스트 파일에서 해당 컨테이너의 코드 삭제
    ##  유저 코드가 텍스트 파일에 존재하는지 확인
    if  grep -qxF "$userCode" "$txtFile" ; then
        local remove=$(grep -nxF "$userCode" "$txtFile" | cut -d':' -f1)
        sed -i "${remove}d" "$txtFile" 
        echo "${containerName} 삭제"
    else
        echo "5" ; exit 1
    fi

    # 방화벽도 닫음
    sudo ufw deny $hPort
    sudo ufw deny $hPort/tcp
    sudo ufw delete deny $hPort
    sudo ufw delete deny $hPort/tcp

    # 개인키 존재 확인 후 삭제
    cd ~/Keys
    if [ -e ~/Keys/${containerName} ]; then
        rm ~/Keys/${containerName}
    else
        echo "9"; exit 1
    fi
}

RemoveContainer $1 $2 $3 && echo "99"