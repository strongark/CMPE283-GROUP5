#!/bin/bash

#This is a bash script to install Apache server. It will detect the OS Version and will install packages on the basis on that.

VERSION=$(cat /etc/*-release | grep "PRETTY_NAME" | sed 's/PRETTY_NAME=//g' | sed 's/["]//g' | awk '{print $1}')

echo $VERSION

if [[ "$VERSION" == 'Ubuntu' ]]; then
    echo "Linux Version is Ubuntu"
    #Update repo
	apt-get update

	#Install Apache
	apt-get install -y apache2

	#Print Password
	echo "---- Installation completed ----"
	echo "Your Apache Version is:"
	apachectl -v|grep version
	echo "---"
fi

if [[ "$VERSION" == 'CentOS Linux' ]]; then
    echo "Linux Version is CentOS"

    #Install Apache
	yum install httpd

	#Start Server
	service httpd start

	#Print Password
	echo "---- Installation completed ----"
	echo "Your Apache Version is:"
	httpd -v
	echo "---"
fi