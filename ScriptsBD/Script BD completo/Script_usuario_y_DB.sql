--
-- CREAR USUARIO Y BASE DE DATOS en localhost (127.0.0.1)
-- Para ejecutar el script por consola se debe ingrear como root (linux) o administrador (windows) y ejecutar los siguiente:
-- mysql -u root -p < Script_usuario_y_DB.sql
--

CREATE DATABASE doctorado;
CREATE USER 'Doctorado'@'localhost' IDENTIFIED BY 'Doc2017_I';
GRANT ALL ON doctorado.* TO 'Doctorado'@'localhost';
FLUSH PRIVILEGES;