# docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
# connect to mysql and run as root user

#Create Databases
CREATE DATABASE petclinic_dev;

#Create database service accounts
CREATE USER 'my_dev_user'@'localhost' IDENTIFIED BY 'super_password';
CREATE USER 'my_dev_user'@'%' IDENTIFIED BY 'super_password';

#Database grants
GRANT ALL PRIVILEGES ON petclinic_dev.* to 'my_dev_user'@'localhost';
GRANT ALL PRIVILEGES ON petclinic_dev.* to 'my_dev_user'@'%';

#Database grants (the right way)
#GRANT SELECT ON petclinic_dev.* to 'my_dev_user'@'localhost';
#GRANT INSERT ON petclinic_dev.* to 'my_dev_user'@'localhost';
#GRANT DELETE ON petclinic_dev.* to 'my_dev_user'@'localhost';
#GRANT UPDATE ON petclinic_dev.* to 'my_dev_user'@'localhost';
#GRANT SELECT ON petclinic_dev.* to 'my_dev_user'@'%';
#GRANT INSERT ON petclinic_dev.* to 'my_dev_user'@'%';
#GRANT DELETE ON petclinic_dev.* to 'my_dev_user'@'%';
#GRANT UPDATE ON petclinic_dev.* to 'my_dev_user'@'%';