#!/usr/bin/bash

Start()
{
    local sh=$1
    echo "start shell - $sh.sh >>>>>> Start" 
}

CSWSFailure()
{
    local sh=$1
    echo "csws - $sh.sh >>>>>> Failure" 
}

CSWSSuccess()
{
    local sh=$1
    echo "csws - $sh.sh >>>>>> Success" 
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
