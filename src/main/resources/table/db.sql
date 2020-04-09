CREATE SCHEMA conference;
CREATE USER 'conference'@'localhost' IDENTIFIED BY 'cfadmin';
GRANT ALL PRIVILEGES ON * . * TO 'conference'@'localhost';
FLUSH PRIVILEGES;