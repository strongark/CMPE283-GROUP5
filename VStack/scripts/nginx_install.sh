#!/bin/bash

#This is a bash script to install Nginx server. It will detect the OS Version and will install packages on the basis on that.

VERSION=$(cat /etc/*-release | grep "PRETTY_NAME" | sed 's/PRETTY_NAME=//g' | sed 's/["]//g' | awk '{print $1}')

echo $VERSION

if [[ "$VERSION" == 'Ubuntu' ]]; then
    echo "Linux Version is Ubuntu"
    #Update repo
	apt-get update

	#Install Nginx
	apt-get install -y nginx

	#Print Password
	echo "---- Installation completed ----"
	echo "Your Nginx Version is:"
	nginx -v
	echo "---"
fi

if [[ "$VERSION" == 'CentOS Linux' ]]; then
    echo "Linux Version is CentOS"

    #Add Nginx Repo
    yum install epel-release

    #Install Nginx
	yum install nginx

	#Start Server
	systemctl start nginx

	#Print Password
	echo "---- Installation completed ----"
	echo "Your Nginx Version is:"
	nginx -v
	echo "---"
fi