#Ubuntu Docker file with Ssh 
#VERSION 0.0.1

#Base Image
FROM ubuntu:latest

#Maintainer Info.
LABEL maintainer="juxtap.se@gmail.com"

#Update APT repository & Install OpenSSH

RUN apt-get update 
RUN apt-get install openssh-server -y

#set password
RUN echo 'root:root' |chpasswd

#Allow Root login
RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PubkeyAuthentication\s+.*/PubkeyAuthentication yes/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PasswordAuthentication\s+.*/PasswordAuthentication no/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?AuthorizedKeysFile\s+.*/AuthorizedKeysFile      \/root\/.ssh\/authorized_keys/' /etc/ssh/sshd_config

RUN mkdir /root/.ssh
RUN ["/bin/bash", "-c", "ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa <<< y >/dev/null 2>&1"]
RUN ["/bin/bash", "-c", "cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys"]

RUN apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

#To start service at runtime
CMD ["/usr/sbin/sshd", "-D"]