-- create database
CREATE DATABASE recipe_db_dev;
CREATE DATABASE recipe_db_test;

-- create users
CREATE USER 'recipe_dev'@'localhost' IDENTIFIED BY 'devpwd';
CREATE USER 'recipe_test'@'localhost' IDENTIFIED BY 'testpwd';

GRANT SELECT ON recipe_db_dev.* TO 'recipe_dev'@'localhost';
GRANT INSERT ON recipe_db_dev.* TO 'recipe_dev'@'localhost';
GRANT UPDATE ON recipe_db_dev.* TO 'recipe_dev'@'localhost';
GRANT DELETE ON recipe_db_dev.* TO 'recipe_dev'@'localhost';

GRANT SELECT ON recipe_db_test.* TO 'recipe_test'@'localhost';
GRANT INSERT ON recipe_db_test.* TO 'recipe_test'@'localhost';
GRANT UPDATE ON recipe_db_test.* TO 'recipe_test'@'localhost';
GRANT DELETE ON recipe_db_test.* TO 'recipe_test'@'localhost';