-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: doctorado
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Para ejecutar el script debe haber corrido el script de creación de usuario y base de datos (Script_usuario_y_DB.sql), luego por consola se debe ingrear con privilegios root (linux) o administrador (windows) y ejecutar los siguiente:
-- mysql -u Doctorado -p doctorado < Script_BD_doctorado.sql
--

--
-- Table structure for table `actividad_pd`
--

DROP TABLE IF EXISTS `actividad_pd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actividad_pd` (
  `id_actividad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_actividad` varchar(100) NOT NULL,
  `horas_asignadas` int(3) DEFAULT NULL,
  `fase_ajuste` float DEFAULT NULL,
  `maximoHoras` int(3) DEFAULT NULL,
  PRIMARY KEY (`id_actividad`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actividad_pd`
--

LOCK TABLES `actividad_pd` WRITE;
/*!40000 ALTER TABLE `actividad_pd` DISABLE KEYS */;
insert into actividad_pd (nombre_actividad,horas_asignadas,fase_ajuste,maximoHoras) values
("Diseño curricular de cursos teóricos/prácticos nuevos",null,1,72),
("Preparación de cursos teóricos/prácticos nuevos",null,2.5,null),
("Docencia curso pregrado",null,2.5,null),
("Docencia curso maestría",null,3.5,null),
("Curso corto (seminario actualización)",null,2.5,null),
("Monitorias cursos",36,1,null),
("Elaboración de material de apoyo",null,2.5,null),
("Dirección trabajo de grado en pregrado/Maestría",null,2,176),
("Asesoría de pasantía (*)",44,1,88),
("Jurado trabajo de grado de pregrado",20,1,80),
("Evaluación como jurado de pasantía (*)",20,1,80),
("Jurado trabajo de tesis de maestría",30,1,90),
("Evaluación de anteproyecto (Departamentos)",4,1,24),
("Evaluación de plan de trabajo para pasantía(*)",4,1,24),
("Evaluación productividad intelectual",8,1,48),
("Evaluación informe año sabático - Libros",30,1,30),
("Participación en el Comité de Programa",48,1,2),
("Otrás actividades de apoyo al departamento",null,1,30);
/*!40000 ALTER TABLE `actividad_pd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archivo`
--

DROP TABLE IF EXISTS `archivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archivo` (
  `arc_identificador` int(11) NOT NULL AUTO_INCREMENT,
  `arc_pub_identificador` int(11) NOT NULL,
  `arc_tipoPDF_cargar` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`arc_identificador`),
  KEY `fk_archivo_publicacion1_idx` (`arc_pub_identificador`),
  CONSTRAINT `fk_archivo_publicacion1` FOREIGN KEY (`arc_pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archivo`
--

LOCK TABLES `archivo` WRITE;
/*!40000 ALTER TABLE `archivo` DISABLE KEYS */;
INSERT INTO `archivo` VALUES (1,113,'tipoPublicacion'),(2,113,'cartaAprobacion'),(3,114,'practicaDocente');
/*!40000 ALTER TABLE `archivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `capitulo_libro`
--

DROP TABLE IF EXISTS `capitulo_libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `capitulo_libro` (
  `pub_identificador` int(11) NOT NULL,
  `caplib_titulo_libro` varchar(200) NOT NULL,
  `caplib_titulo_capitulo` varchar(80) NOT NULL,
  `caplib_isbn` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`pub_identificador`),
  KEY `fk_capitulo_libro_publicacion1_idx` (`pub_identificador`),
  CONSTRAINT `fk_capitulo_libro_publicacion1` FOREIGN KEY (`pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `capitulo_libro`
--

LOCK TABLES `capitulo_libro` WRITE;
/*!40000 ALTER TABLE `capitulo_libro` DISABLE KEYS */;
/*!40000 ALTER TABLE `capitulo_libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ciudad` (
  `ciud_id` int(11) NOT NULL AUTO_INCREMENT,
  `ciud_nombre` varchar(40) DEFAULT NULL,
  `pais_id` int(11) NOT NULL,
  PRIMARY KEY (`ciud_id`),
  KEY `fk_pais_id` (`pais_id`),
  CONSTRAINT `fk_pais_id` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`pais_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudad`
--

LOCK TABLES `ciudad` WRITE;
/*!40000 ALTER TABLE `ciudad` DISABLE KEYS */;
INSERT INTO `ciudad` VALUES (1,'BUENOS AIRES',2),(2,'CATAMARCA',2),(3,'CHACO',2),(4,'CÓRDOBA',2),(5,'CORRIENTES',2),(6,'MENDOZA',2),(7,'MADRID',3),(8,'SEVILLA',3),(9,'ZARAGOZA',3),(10,'VALENCIA',3),(11,'PAMPLONA',3),(12,'LAS PALMAS DE GRAN CANARIA',3),(13,'ALICANTE',3),(14,'BOGOTA',1),(15,'MEDELLIN',1),(16,'CALI',1),(17,'CARTAGENA',1),(18,'BARRANQUILLA',1),(19,'BUCARAMANGA',1),(20,'SANTA MARTA',1),(21,'MANIZALES',1),(22,'CÚCUTA',1),(23,'PASTO',1),(24,'PEREIRA',1),(25,'POPAYAN',1),(26,'ARMENIA',1);
/*!40000 ALTER TABLE `ciudad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `congreso`
--

DROP TABLE IF EXISTS `congreso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `congreso` (
  `pub_identificador` int(11) NOT NULL,
  `cong_titulo_ponencia` varchar(200) NOT NULL,
  `cong_nombre_evento` varchar(100) NOT NULL,
  `cong_tipo_congreso` varchar(30) NOT NULL,
  `cong_doi` varchar(30) DEFAULT NULL,
  `cong_issn` varchar(30) DEFAULT NULL,
  `ciudad_id` int(11) DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  PRIMARY KEY (`pub_identificador`),
  KEY `fk_congreso_publicacion1_idx` (`pub_identificador`),
  KEY `FK_congreso_ciudad` (`ciudad_id`),
  CONSTRAINT `FK_congreso_ciudad` FOREIGN KEY (`ciudad_id`) REFERENCES `ciudad` (`ciud_id`),
  CONSTRAINT `fk_congreso_publicacion1` FOREIGN KEY (`pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `congreso`
--

LOCK TABLES `congreso` WRITE;
/*!40000 ALTER TABLE `congreso` DISABLE KEYS */;
/*!40000 ALTER TABLE `congreso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinador`
--

DROP TABLE IF EXISTS `coordinador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinador` (
  `coo_identificador` int(11) NOT NULL AUTO_INCREMENT,
  `coo_nombre` varchar(45) DEFAULT NULL,
  `coo_contrasena` varchar(40) DEFAULT NULL,
  `coo_correo` varchar(30) DEFAULT NULL,
  `coo_usuario` varchar(20) DEFAULT NULL,
  `usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`coo_identificador`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `coordinador_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinador`
--

LOCK TABLES `coordinador` WRITE;
/*!40000 ALTER TABLE `coordinador` DISABLE KEYS */;
/*!40000 ALTER TABLE `coordinador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctorado`
--

DROP TABLE IF EXISTS `doctorado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctorado` (
  `doc_identificador` int(11) NOT NULL AUTO_INCREMENT,
  `doc_coo_identificador` int(11) NOT NULL,
  `doc_est_identificador` int(11) NOT NULL,
  PRIMARY KEY (`doc_identificador`,`doc_coo_identificador`,`doc_est_identificador`),
  KEY `fk_doctorado_coordinador1_idx` (`doc_coo_identificador`),
  KEY `fk_doctorado_estudiante1_idx` (`doc_est_identificador`),
  CONSTRAINT `fk_doctorado_coordinador1` FOREIGN KEY (`doc_coo_identificador`) REFERENCES `coordinador` (`coo_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_doctorado_estudiante1` FOREIGN KEY (`doc_est_identificador`) REFERENCES `estudiante` (`est_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctorado`
--

LOCK TABLES `doctorado` WRITE;
/*!40000 ALTER TABLE `doctorado` DISABLE KEYS */;
/*!40000 ALTER TABLE `doctorado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estudiante` (
  `est_identificador` int(11) NOT NULL AUTO_INCREMENT,
  `est_codigo` varchar(20) DEFAULT NULL,
  `est_nombre` varchar(20) DEFAULT NULL,
  `est_apellido` varchar(20) DEFAULT NULL,
  `est_correo` varchar(30) DEFAULT NULL,
  `est_cohorte` int(4) DEFAULT NULL,
  `est_tutor` varchar(45) DEFAULT NULL,
  `est_semestre` int(2) DEFAULT NULL,
  `est_estado` varchar(12) DEFAULT NULL,
  `est_usuario` varchar(20) DEFAULT NULL,
  `est_creditos` int(11) DEFAULT '0',
  `usuario_id` int(11) NOT NULL,
  PRIMARY KEY (`est_identificador`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `estudiante_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupo_tipo_usuario`
--

DROP TABLE IF EXISTS `grupo_tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupo_tipo_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_tipo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(30) NOT NULL,
  PRIMARY KEY (`id`,`id_tipo`,`id_usuario`),
  KEY `fk_tipo_usuario_idx` (`id_tipo`),
  KEY `fk_usuario_idx` (`id_usuario`),
  CONSTRAINT `fk_tipo_usuario_idx` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_idx` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupo_tipo_usuario`
--

LOCK TABLES `grupo_tipo_usuario` WRITE;
/*!40000 ALTER TABLE `grupo_tipo_usuario` DISABLE KEYS */;
INSERT INTO `grupo_tipo_usuario` VALUES (2,3,2,'alvaro');
/*!40000 ALTER TABLE `grupo_tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libro` (
  `pub_identificador` int(11) NOT NULL,
  `lib_titulo_libro` varchar(200) NOT NULL,
  `lib_isbn` varchar(30) DEFAULT NULL,
  `editorial` varchar(25) DEFAULT NULL,
  `ciudad_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pub_identificador`),
  KEY `fk_libro_publicacion1_idx` (`pub_identificador`),
  KEY `FK_libro_ciudad` (`ciudad_id`),
  CONSTRAINT `FK_libro_ciudad` FOREIGN KEY (`ciudad_id`) REFERENCES `ciudad` (`ciud_id`),
  CONSTRAINT `fk_libro_publicacion1` FOREIGN KEY (`pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pais` (
  `pais_id` int(11) NOT NULL AUTO_INCREMENT,
  `pais_nombre` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`pais_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'COLOMBIA'),(2,'ARGENTINA'),(3,'ESPAÑA');
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `practica_docente`
--

DROP TABLE IF EXISTS `practica_docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `practica_docente` (
  `pub_identificador` int(11) NOT NULL,
  `numero_horas` int(2) DEFAULT NULL,
  `id_actividad` int(11) NOT NULL,
  `otros` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pub_identificador`),
  KEY `fk_practica_publicacion_idx` (`pub_identificador`),
  KEY `fk_practica_actividad_idx` (`id_actividad`),
  CONSTRAINT `fk_practica_actividad` FOREIGN KEY (`id_actividad`) REFERENCES `actividad_pd` (`id_actividad`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_practica_publicacion` FOREIGN KEY (`pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `practica_docente`
--

LOCK TABLES `practica_docente` WRITE;
/*!40000 ALTER TABLE `practica_docente` DISABLE KEYS */;
/*!40000 ALTER TABLE `practica_docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publicacion`
--

DROP TABLE IF EXISTS `publicacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publicacion` (
  `pub_identificador` int(11) NOT NULL AUTO_INCREMENT,
  `pub_hash` varchar(40) DEFAULT NULL,
  `pub_diropkm` varchar(30) DEFAULT NULL,
  `pub_fecha_visado` date DEFAULT NULL,
  `pub_fecha_registro` date DEFAULT NULL,
  `pub_estado` varchar(15) DEFAULT NULL,
  `pub_est_identificador` int(11) DEFAULT NULL,
  `pub_autores_secundarios` varchar(300) DEFAULT NULL,
  `pub_tipo_publicacion` varchar(22) DEFAULT NULL,
  `pub_fecha_publicacion` date NOT NULL,
  `pub_num_acta` int(20) DEFAULT NULL,
  `pub_visado` varchar(20) DEFAULT NULL,
  `id_tipo_documento` int(11) DEFAULT NULL,
  `pub_creditos` int(3) DEFAULT '0',
  PRIMARY KEY (`pub_identificador`),
  KEY `fk_publicacion_estudiante_idx` (`pub_est_identificador`),
  KEY `id_tipo_documento` (`id_tipo_documento`),
  CONSTRAINT `fk_publicacion_estudiante` FOREIGN KEY (`pub_est_identificador`) REFERENCES `estudiante` (`est_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `publicacion_ibfk_1` FOREIGN KEY (`id_tipo_documento`) REFERENCES `tipo_documento` (`identificador`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publicacion`
--

LOCK TABLES `publicacion` WRITE;
/*!40000 ALTER TABLE `publicacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `publicacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revista`
--

DROP TABLE IF EXISTS `revista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revista` (
  `pub_identificador` int(11) NOT NULL,
  `rev_nombre_revista` varchar(80) NOT NULL,
  `rev_titulo_articulo` varchar(200) NOT NULL,
  `rev_categoria` varchar(2) NOT NULL,
  `rev_doi` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`pub_identificador`),
  KEY `fk_revista_publicacion1_idx` (`pub_identificador`),
  CONSTRAINT `fk_revista_publicacion1` FOREIGN KEY (`pub_identificador`) REFERENCES `publicacion` (`pub_identificador`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revista`
--

LOCK TABLES `revista` WRITE;
/*!40000 ALTER TABLE `revista` DISABLE KEYS */;
/*!40000 ALTER TABLE `revista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_documento`
--

DROP TABLE IF EXISTS `tipo_documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_documento` (
  `identificador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `creditos` int(3) NOT NULL,
  `correquisitos` int(11) DEFAULT NULL,
  `max_creditos` int(4) DEFAULT NULL,
  `min_creditos` int(4) DEFAULT NULL,
  PRIMARY KEY (`identificador`),
  KEY `correquisitos` (`correquisitos`),
  CONSTRAINT `tipo_documento_ibfk_1` FOREIGN KEY (`correquisitos`) REFERENCES `tipo_documento` (`identificador`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_documento`
--

LOCK TABLES `tipo_documento` WRITE;
/*!40000 ALTER TABLE `tipo_documento` DISABLE KEYS */;
INSERT INTO `tipo_documento` VALUES (1,'libro',0,NULL,NULL,NULL),(2,'capitulo libro',0,NULL,NULL,NULL),(3,'congreso',0,NULL,NULL,NULL),(4,'revista',0,NULL,NULL,NULL),(5,'Practica docente',1,NULL,3,6);
/*!40000 ALTER TABLE `tipo_documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_usuario`
--

DROP TABLE IF EXISTS `tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_usuario`
--

LOCK TABLES `tipo_usuario` WRITE;
/*!40000 ALTER TABLE `tipo_usuario` DISABLE KEYS */;
INSERT INTO `tipo_usuario` VALUES (1,'PROFESOR'),(2,'ESTUDIANTE'),(3,'COORDINADOR');
/*!40000 ALTER TABLE `tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(30) DEFAULT NULL,
  `apellidos` varchar(30) DEFAULT NULL,
  `nombre_usuario` varchar(30) NOT NULL,
  `contrasena` varchar(65) DEFAULT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'activo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (2,'Alvaro','Restrepo','alvaro','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','activo');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-14 13:10:22
