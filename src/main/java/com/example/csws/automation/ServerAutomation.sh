#!/usr/bin/bash

Automation(){
    
    local passwd=$1 

    echo "$passwd" | sudo -S sed -i -r -e '/# User privilege specification/a\'"$USER"'\tALL=NOPASSWD:\tALL' /etc/sudoers

    # xfs 찾아서 fstab과 grub 파일을 pquota로 바꾸기
    #fstab
    sudo sed -ie 's@/home xfs defaults 0 1@/home xfs pquota 0 1@g' /etc/fstab
    # grub
    sudo sed -ie 's@GRUB_CMDLINE_LINUX=""@GRUB_CMDLINE_LINUX="pquota"@g' /etc/default/grub

    sudo apt-get update

    # open ssh server 깔고 포트 22번 열고 설정 파일 바꾸기
    echo "y" | sudo apt install openssh-server
    sudo sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
    sudo sed -ri 's/^#?PubkeyAuthentication\s+.*/PubkeyAuthentication yes/' /etc/ssh/sshd_config
    sudo sed -ri 's/^#?PasswordAuthentication\s+.*/PasswordAuthentication no/' /etc/ssh/sshd_config
    sudo sed -ri "s/^#?AuthorizedKeysFile\s+.*/AuthorizedKeysFile      \/home\/$USER\/.ssh\/authorized_keys/" /etc/ssh/sshd_config
    sudo sed -ri "s/^#?Port\s+.*/Port 22/" /etc/ssh/sshd_config
    sudo cat ~/id_rsa.pub >> /home/$USER/.ssh/authorized_keys
    sudo systemctl restart ssh

    # 도커 깔기
    sudo apt-get install ca-certificates curl gnupg lsb-release
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    sudo apt-get update
    echo "y" | sudo -S apt-get install docker-ce docker-ce-cli containerd.io 

    # 도커 경로 바꾸기
    sudo systemctl start docker
    sudo sed -i '13s@$@ --data-root=/home/docker@' /lib/systemd/system/docker.service
    sudo systemctl daemon-reload
    sudo systemctl stop docker
    sudo mkdir /home/docker
    sudo cp -rp /var/lib/docker /home/
    sudo usermod -a -G docker $USER
    sudo systemctl start docker

    # ufw 깔고 22 열기
    sudo apt-get install ufw
    sudo ufw allow ssh
    sudo ufw allow 22
    
    sudo reboot
}

Automation $1