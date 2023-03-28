#CentOS Docker file with Ssh 
#VERSION 0.0.1

#Base Image
FROM centos:centos7

#Maintainer Info.
LABEL maintainer="juxtap.se@gmail.com"

#Update APT repository & Install OpenSSH
RUN yum install openssh-server -y 

#set password
RUN echo "root:root" | chpasswd

#Allow Password && Root login
#Allow Root login
RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PubkeyAuthentication\s+.*/PubkeyAuthentication yes/' /etc/ssh/sshd_config
RUN sed -ri 's/#PasswordAuthentication yes/PasswordAuthentication no/g' /etc/ssh/sshd_config
RUN sed -ri 's/^#?AuthorizedKeysFile\s+.*/AuthorizedKeysFile      \/root\/.ssh\/authorized_keys/' /etc/ssh/sshd_config

#create keypairs
RUN mkdir /root/.ssh
RUN ["/bin/bash", "-c", "ssh-keygen -q -t rsa -N '' -f /root/.ssh/id_rsa <<< y >/dev/null 2>&1"]
RUN ["/bin/bash", "-c", "cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys"]

#To start service at runtime
CMD ["/usr/sbin/sshd", "-D"]