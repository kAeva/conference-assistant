CREATE SCHEMA conference;
CREATE USER 'conference'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON * . * TO 'conference'@'localhost';
FLUSH PRIVILEGES;