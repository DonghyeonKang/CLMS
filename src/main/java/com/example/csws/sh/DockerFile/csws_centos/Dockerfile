#CentOS Docker file with Ssh 
#VERSION 0.0.1

#Base Image
FROM centos:centos7

#Maintainer Info.
LABEL maintainer="juxtap.se@gmail.com"

#Update APT repository & Install OpenSSH

RUN echo "test..."
RUN useradd -m csws
RUN usermod -aG wheel csws
RUN echo "csws:csws" | chpasswd
RUN yum install openssh-server -y 
RUN yum install wget -y
RUN yum install -y sudo 

#set password
RUN echo "root:root" | chpasswd

#Allow Password && Root login
#Allow Root login
#RUN sed -ri 's/^#?PermitRootLogin\s+.*/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -ri 's/UsePAM yes/#UsePAM yes/g' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PubkeyAuthentication\s+.*/PubkeyAuthentication yes/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?PasswordAuthentication\s+.*/PasswordAuthentication no/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?AuthorizedKeysFile\s+.*/AuthorizedKeysFile      \/home\/csws\/.ssh\/authorized_keys/' /etc/ssh/sshd_config
RUN sed -ri 's/^#?Port\s+.*/Port 22/' /etc/ssh/sshd_config

RUN mkdir -p /home/csws/.ssh
RUN touch /home/csws/.ssh/authorized_keys

#To start service at runtime
CMD ["/usr/sbin/sshd", "-D"]