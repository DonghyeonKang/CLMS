#Ubuntu Docker file with Ssh 
#VERSION 0.0.1

#Base Image
FROM ubuntu:latest

#Maintainer Info.
LABEL maintainer="juxtap.se@gmail.com"

#Update APT repository & Install OpenSSH
 
RUN apt-get update -y
RUN apt-get install -y openssh-server
RUN apt-get install -y wget
RUN apt-get install -y sudo 
RUN useradd -m csws
RUN usermod -aG sudo csws
RUN echo "csws:csws" | chpasswd

#set password
RUN echo 'root:root' |chpasswd

#Allow Root login
# RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PubkeyAuthentication\s+.*/PubkeyAuthentication yes/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PasswordAuthentication\s+.*/PasswordAuthentication no/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?AuthorizedKeysFile\s+.*/AuthorizedKeysFile      \/home\/csws\/.ssh\/authorized_keys/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?Port\s+.*/Port 22/' /etc/ssh/sshd_config

RUN mkdir -p /home/csws/.ssh
RUN touch /home/csws/.ssh/authorized_keys

RUN apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

#To start service at runtime
CMD ["/usr/sbin/sshd", "-D"]