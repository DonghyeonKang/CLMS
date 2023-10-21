#!/usr/bin/bash

Start()
{
    local sh=$1
    echo "start shell - $sh.sh >>>>>> Start" 
}

HostFailure()
{
    local sh=$1
    echo "host - $sh.sh >>>>>> Failure" 
}

HostSuccess()
{
    local sh=$1
    echo "host - $sh.sh >>>>>> Success" 
}
