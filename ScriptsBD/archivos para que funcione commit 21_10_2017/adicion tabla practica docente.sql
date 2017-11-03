
-- DROP TABLE IF EXIST `practica_docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `practica_docente`(
	`pub_identificador` int(11) NOT NULL,
	`fecha_inicio` date NOT NULL ,
	`fecha_terminacion` date NOT NULL,
	`lugar_practica` varchar(200) NOT NULL,

	PRIMARY KEY (`pub_identificador`),
	KEY `fk_practica_publicacion_idx` (`pub_identificador`),
	CONSTRAINT `fk_practica_publicacion` FOREIGN KEY (`pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



alter table tipo_documento add column max_creditos int(4) default null;
alter table tipo_documento add column min_creditos int(4) default null;

START TRANSACTION;
INSERT INTO tipo_documento (nombre, creditos ) values ('libro',0);
INSERT INTO tipo_documento (nombre, creditos ) values ('capitulo libro',0);
INSERT INTO tipo_documento (nombre, creditos ) values ('congreso',0);
INSERT INTO tipo_documento (nombre, creditos ) values ('revista',0);
INSERT INTO tipo_documento (nombre, creditos, max_creditos, min_creditos) values ('Practica docente',1,3,6);
COMMIT;

START TRANSACTION;
INSERT INTO tipo_documento (nombre, creditos, max_creditos, min_creditos) values ('Pasantía de investigación',0,16,19);
INSERT INTO tipo_documento (nombre, creditos, max_creditos, min_creditos) values ('Seminario II',3,3,3);
INSERT INTO tipo_documento (nombre, creditos, max_creditos, min_creditos) values ('Tesis I',9,9,9);
INSERT INTO tipo_documento (nombre, creditos, correquisitos, max_creditos, min_creditos) values ('Tesis II',35,8,35,35);
INSERT INTO tipo_documento (nombre, creditos) values ('Idioma',0);
INSERT INTO tipo_documento (nombre, creditos) values ('Adicionales',0);
COMMIT;