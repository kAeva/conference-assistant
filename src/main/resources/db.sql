CREATE SCHEMA conference;
CREATE USER 'cfadmin'@'localhost' IDENTIFIED BY 'cfadmin';
GRANT ALL PRIVILEGES ON * . * TO 'cfadmin'@'localhost';
FLUSH PRIVILEGES;