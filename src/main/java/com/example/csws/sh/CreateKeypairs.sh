#!/usr/bin/bash

. ~/sh/CSWSException.sh

CreateKeypairs()
{
    local hostName=$1
    local keyName=$2

    # Users 디렉토리가 존재하지 않으면 생성
    if [ ! -d ~/Keys/$hostName ]; then
        mkdir -p ~/Keys/$hostName
    fi

    ssh-keygen -m PEM -t rsa -b 2048 -f ~/Keys/$hostName/$keyName -N '' || exit
    openssl rsa -in ~/Keys/$hostName/$keyName -outform PEM -out ~/Keys/$hostName/$keyName.pem || exit 2
    # openssl rsa -in ~/Keys/pika/su1 -outform PEM -out ~/Keys/pika/su1.pem
    rm ~/Keys/$hostName/$keyName
}
Start CreateKeypairs
CreateKeypairs $1 $2&& CSWSSuccess CreateKeypairs || CSWSFailure CreateKeypairs