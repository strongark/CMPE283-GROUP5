#!/bin/bash

#This is a bash script to install mysql. It will detect the OS Version and will install packages on the basis on that.

VERSION=$(cat /etc/*-release | grep "PRETTY_NAME" | sed 's/PRETTY_NAME=//g' | sed 's/["]//g' | awk '{print $1}')
MYSQL_ROOT_PASSWORD='N0T-P4ssw0rd'

echo $VERSION

if [[ "$VERSION" == 'Ubuntu' ]]; then
    echo "Linux Version is Ubuntu"
    #Update repo
	apt-get update

	#Set the password so you don't have to enter it during installation
	echo "mysql-server mysql-server/root_password select $MYSQL_ROOT_PASSWORD" | debconf-set-selections
	echo "mysql-server mysql-server/root_password_again select $MYSQL_ROOT_PASSWORD" | debconf-set-selections

	#Install Mysql
	apt-get install -y mysql-server

	#Print Password
	echo "---- Installation completed ----"
	echo "Your MYsql Version is:"
	mysql -V
	echo "---"
	echo "Your MySQL root password is:"
	echo $MYSQL_ROOT_PASSWORD
fi

if [[ "$VERSION" == 'CentOS Linux' ]]; then
	echo "Linux Version is Centos"

	echo ' -> Installing mysql server....'
	yum -y --enablerepo=webtatic install mysql-server 

	#Print Password
	echo "---- Installation completed ----"
	echo "Your MYsql Version is:"
	mysql -V
	echo "---"
	echo "Your MySQL root password is:"
	echo $MYSQL_ROOT_PASSWORD
	
fi