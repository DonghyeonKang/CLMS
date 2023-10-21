#!/user/bin/bash
. ~/sh/HostException.sh

H_TransferOwnership()
{
    local owner=$1
    local code=$2
    local newOwner=$3
    local newCode=$4
    local storageSize=$5
    local containerKeyName=$6

    cd ~/
    local containerName=$owner$code
    local txtFile="Users/$owner.txt"

    local LIST_PORT=`cat Ports/$containerName.txt`

    # 인수가 잘 들어왔는지 확인
    if [ $# -lt 6 ]; then
        echo "host - H_TransferOwnership.sh >>>>>> 인수가 부족합니다."
        exit 1
    fi

    # 컨테이너 커밋
    docker stop $containerName
    docker commit $containerName $newOwner$newCode # 새로운 이미지 이름(운영체제 + 열 포트 번호)
    docker rm $containerName

    # Split 후 도커 런 
    IFS=$'\n'
    local exe="docker run --privileged -d $(for port in $LIST_PORT; do echo "-p $port "; done)--name $newOwner$newCode --storage-opt size=$storageSize $newOwner$newCode tail -f /dev/null"
    eval $exe
    IFS=$IFS_OLD

    # 각종 파일 소유권도 넘김
    mv ~/Ports/$containerName.txt ~/Ports/$newOwner$newCode.txt

    if  grep -qxF "$code" "$txtFile" ; then
        local remove=$(grep -nxF "$code" "$txtFile" | cut -d':' -f1)
        sed -i "${remove}d" "$txtFile" 
        echo "${containerName} 삭제"
    fi

    if [ ! -f ~/Users/$newOwner.txt ]; then
        touch ~/Users/$newOwner.txt
        echo $newCode >> ~/Users/$newOwner.txt
    fi

    rm ~/Keys/$containerKeyName.pub
}

Start H_TransferOwnership
H_TransferOwnership $1 $2 $3 $4 $5 $6 && HostSuccess H_TransferOwnership || HostFailure H_TransferOwnership