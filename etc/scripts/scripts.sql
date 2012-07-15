CREATE DATABASE  IF NOT EXISTS app;

CREATE USER 'app-site'@'localhost' IDENTIFIED BY 'site';
GRANT ALL PRIVILEGES ON *.* TO 'app-site'@'localhost' WITH GRANT OPTION;
CREATE USER 'app-site'@'%' IDENTIFIED BY 'site';
GRANT ALL PRIVILEGES ON *.* TO 'app-site'@'%' WITH GRANT OPTION;

use app;