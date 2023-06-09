#!/user/bin/bash

. ~/sh/HostException.sh

H_SendPublickey()
{
    local keyName=$1
    local conName=$2

    docker cp ~/Keys/$keyName.pub $conName:/home/csws/.ssh/
    docker exec $conName bash -c "echo csws | sudo -S chmod 777 /home/csws/.ssh/authorized_keys"
    docker exec $conName bash -c "echo csws | sudo -S cat /home/csws/.ssh/$keyName.pub >> /home/csws/.ssh/authorized_keys"
    docker exec $conName bash -c "echo csws | sudo -S chmod 644 /home/csws/.ssh/authorized_keys"
    docker exec $conName bash -c "echo csws | sudo -S service ssh restart"
}

Start H_SendPublickey
H_SendPublickey $1 $2 && HostSuccess H_SendPublickey || HostFailure H_SendPublickey