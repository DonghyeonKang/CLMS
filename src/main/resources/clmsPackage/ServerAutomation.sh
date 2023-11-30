#!/usr/bin/bash

Automation(){
    
    local passwd=$1 
    touch ~/etc/pw.txt
    echo $passwd >> ~/etc/pw.txt

    echo "$passwd" | sudo -S sed -i -r -e '/# User privilege specification/a\'"$USER"'\tALL=NOPASSWD:\tALL' /etc/sudoers

    # xfs Ï∞æÏïÑ?Ñú fstabÍ≥? grub ?åå?ùº?ùÑ pquotaÎ°? Î∞îÍæ∏Í∏?
    #fstab
    sudo sed -ie 's@/home xfs defaults 0 1@/home xfs pquota 0 1@g' /etc/fstab
    # grub
    sudo sed -ie 's@GRUB_CMDLINE_LINUX=""@GRUB_CMDLINE_LINUX="pquota"@g' /etc/default/grub

    sudo apt-get update -y

    # open ssh server ÍπîÍ≥† ?è¨?ä∏ 22Î≤? ?ó¥Í≥? ?Ñ§?†ï ?åå?ùº Î∞îÍæ∏Í∏?
    sudo apt install -y openssh-server
    sudo sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
    sudo sed -ri 's/^#?PubkeyAuthentication\s+.*/PubkeyAuthentication yes/' /etc/ssh/sshd_config
    sudo sed -ri 's/^#?PasswordAuthentication\s+.*/PasswordAuthentication no/' /etc/ssh/sshd_config
    sudo sed -ri "s/^#?AuthorizedKeysFile\s+.*/AuthorizedKeysFile      \/home\/$USER\/.ssh\/authorized_keys/" /etc/ssh/sshd_config
    sudo sed -ri "s/^#?Port\s+.*/Port 9999/" /etc/ssh/sshd_config
    sudo cat ~/etc/id_rsa.pub >> /home/$USER/.ssh/authorized_keys
    sudo systemctl restart sshd

    # ?ÑúÎ≤? ?ûê?õê ?Ç¨?ö©?üâ?ùÑ ?ïåÍ∏? ?úÑ?ï¥ ?Ñ§Ïπ?
    sudo apt-get install -y sysstat

    # ?èÑÏª? ÍπîÍ∏∞
    sudo apt-get install -y ca-certificates curl gnupg lsb-release
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    sudo apt-get update -y
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io 

    # ?èÑÏª? Í≤ΩÎ°ú Î∞îÍæ∏Í∏?
    sudo systemctl start docker
    sudo sed -i '13s@$@ --data-root=/home/docker@' /lib/systemd/system/docker.service
    sudo systemctl daemon-reload
    sudo systemctl stop docker
    sudo mkdir /home/docker
    sudo cp -rp /var/lib/docker /home/
    sudo usermod -a -G docker $USER
    sudo systemctl start docker

    # iptables ÍπîÍ≥† 22 ?ó¥Í∏?
    sudo DEBIAN_FRONTEND=noninteractive apt-get install -y iptables-persistent
    sudo systemctl start iptables
    sudo systemctl enable iptables

    sudo iptables -P INPUT DROP
    sudo iptables -A INPUT -p tcp --dport 22 -j ACCEPT #ssh ?è¨?ä∏ ?óà?ö©
    sudo iptables -A INPUT -p tcp --dport 80 -j ACCEPT #http ?è¨?ä∏ ?óà?ö©
    sudo iptables -A INPUT -p tcp --dport 443 -j ACCEPT #https ?è¨?ä∏ ?óà?ö©
    sudo iptables -A INPUT -p udp --dport 53 -j ACCEPT # DNS ?è¨?ä∏ ?óà?ö©
    sudo iptables -A INPUT -p tcp --dport 25 -j ACCEPT # SMTP ?è¨?ä∏ ?óà?ö©
    sudo iptables -A INPUT -p icmp -j ACCEPT # ICMP ?óà?ö©
    sudo iptables -A INPUT -p udp --sport 53 -j ACCEPT
    sudo iptables -A INPUT -p tcp --sport 80 -j ACCEPT
    sudo iptables -A INPUT -p tcp --sport 443 -j ACCEPT
    sudo iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

    sudo netfilter-persistent save

    # nginx ?Ñ§Ïπ?
    sudo apt-get install -y nginx
    sudo systemctl start nginx
    sudo systemctl enable nginx
    
    sudo reboot
}

Automation $1