--
-- CREAR  BASE DE DATOS 
-- Para ejecutar el script por consola se debe ingrear como root (linux) o administrador (windows) y ejecutar los siguiente:
-- mysql -u root -p < Script_openkm.sql
--

CREATE DATABASE okmdb DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;
CREATE USER openkm@localhost IDENTIFIED BY 'QucroUV9Gb';
GRANT ALL ON okmdb.* TO openkm@localhost WITH GRANT OPTION;
