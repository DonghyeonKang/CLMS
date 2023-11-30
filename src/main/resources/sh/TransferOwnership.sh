#!/usr/bin/bash

##bash

. ~/sh/CSWSException.sh

TransferOwnersip()
{
    local hostName=$1
    local hostIp=$2 
    
    local owner=$3
    local code=$4
    local newOwner=$5
    local newCode=$6
    local storageSize=$7
    local containerKeyName=$8

    local containerName=$owner$code

    ssh $hostName@$hostIp -p 9999 "bash ~/sh/H_TransferOwnership.sh $3 $4 $5 $6 $7 $8 "    
}
Start TransferOwnersip
TransferOwnersip $1 $2 $3 $4 $5 $6 $7 $8 && CSWSSuccess TransferOwnersip || CSWSFailure TransferOwnersip