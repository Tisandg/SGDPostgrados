-- MySQL Script generated by MySQL Workbench
-- Fri Apr 21 22:12:09 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

 -- -----------------------------------------------------
-- Schema doctorado
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `doctorado` DEFAULT CHARACTER SET utf8 ;
USE `doctorado` ;

-- -----------------------------------------------------
-- Table `doctorado`.`estudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`estudiante` (
  `est_identificador` INT NOT NULL AUTO_INCREMENT,
  `est_codigo` VARCHAR(20) NULL,
  `est_nombre` VARCHAR(20) NULL,
  `est_apellido` VARCHAR(20) NULL,
  `est_correo` VARCHAR(30) NULL,
  `est_cohorte` INT(4) NULL,
  `est_tutor` VARCHAR(45) NULL,
  `est_semestre` INT(2) NULL,
  `est_estado` VARCHAR(12) NULL,
  `est_usuario` VARCHAR(20) NULL,
  `est_contrasena` VARCHAR(40) NULL,
  `est_creditos` INT NULL,
  PRIMARY KEY (`est_identificador`))
ENGINE = InnoDB;
 

-- -----------------------------------------------------
-- Table `doctorado`.`publicacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`publicacion` (
  `pub_identificador` INT NOT NULL AUTO_INCREMENT,
  `pub_hash` VARCHAR(40) NULL,
  `pub_diropkm` VARCHAR(30) NULL,
  `pub_creditos` INT NULL,
  `pub_fecha_visado` DATE NULL,
  `pub_fecha_registro` DATE NULL,
  `pub_estado` VARCHAR(15) NULL,
  `pub_est_identificador` INT,
  `pub_nombre_autor` VARCHAR(60) NOT NULL,
  `pub_autores_secundarios` VARCHAR(300) NULL,
  `pub_tipo_publicacion` VARCHAR(22) NOT NULL,
  `pub_fecha_publicacion` DATE NOT NULL,
  `pub_num_acta` INT(20) NULL,
  `pub_doi`  VARCHAR(30) NULL,
  `pub_isbn` VARCHAR(30) NULL,
  `pub_issn` VARCHAR(30) NULL,
  `pub_visado` VARCHAR(20) NULL,
  PRIMARY KEY (`pub_identificador`),
  INDEX `fk_publicacion_estudiante_idx` (`pub_est_identificador` ASC),
  CONSTRAINT `fk_publicacion_estudiante`
    FOREIGN KEY (`pub_est_identificador`)
    REFERENCES `doctorado`.`estudiante` (`est_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doctorado`.`archivo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`archivo` (
  `arc_identificador` INT NOT NULL AUTO_INCREMENT,
  `arc_pub_identificador` INT NOT NULL,
  `arc_tipoPDF_cargar` VARCHAR(40) NULL,
  PRIMARY KEY (`arc_identificador`),
  INDEX `fk_archivo_publicacion1_idx` (`arc_pub_identificador` ASC),
  CONSTRAINT `fk_archivo_publicacion1`
    FOREIGN KEY (`arc_pub_identificador`)
    REFERENCES `doctorado`.`publicacion` (`pub_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `doctorado`.`congreso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`congreso` (
  `pub_identificador` INT NOT NULL,
  `cong_titulo_ponencia` VARCHAR(200) NOT NULL,
  `cong_nombre_evento` VARCHAR(100) NOT NULL,
  `cong_tipo_congreso` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`pub_identificador`),
  INDEX `fk_congreso_publicacion1_idx` (`pub_identificador` ASC),
  CONSTRAINT `fk_congreso_publicacion1`
    FOREIGN KEY (`pub_identificador`)
    REFERENCES `doctorado`.`publicacion` (`pub_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 


-- -----------------------------------------------------
-- Table `doctorado`.`coordinador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`coordinador` (
  `coo_identificador` INT NOT NULL AUTO_INCREMENT,
  `coo_nombre` VARCHAR(45) NULL,
  `coo_contrasena` VARCHAR(40) NULL,
  `coo_correo`  VARCHAR(30) NULL,
  `coo_usuario` VARCHAR(20) NULL,
  PRIMARY KEY (`coo_identificador`))
ENGINE = InnoDB;
 


-- -----------------------------------------------------
-- Table `doctorado`.`doctorado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`doctorado` (
  `doc_identificador` INT NOT NULL AUTO_INCREMENT,
  `doc_coo_identificador` INT NOT NULL,
  `doc_est_identificador` INT NOT NULL,
  PRIMARY KEY (`doc_identificador`, `doc_coo_identificador`, `doc_est_identificador`),
  INDEX `fk_doctorado_coordinador1_idx` (`doc_coo_identificador` ASC),
  INDEX `fk_doctorado_estudiante1_idx` (`doc_est_identificador` ASC),
  CONSTRAINT `fk_doctorado_coordinador1`
    FOREIGN KEY (`doc_coo_identificador`)
    REFERENCES `doctorado`.`coordinador` (`coo_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_doctorado_estudiante1`
    FOREIGN KEY (`doc_est_identificador`)
    REFERENCES `doctorado`.`estudiante` (`est_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 


-- -----------------------------------------------------
-- Table `doctorado`.`revista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`revista` (
  `pub_identificador` INT NOT NULL,
  `rev_nombre_revista` VARCHAR(80) NOT NULL,
  `rev_titulo_articulo` VARCHAR(200) NOT NULL,
  `rev_categoria` VARCHAR(2) NOT NULL,
  PRIMARY KEY (`pub_identificador`),
  INDEX `fk_revista_publicacion1_idx` (`pub_identificador` ASC),
  CONSTRAINT `fk_revista_publicacion1`
    FOREIGN KEY (`pub_identificador`)
    REFERENCES `doctorado`.`publicacion` (`pub_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doctorado`.`libro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`libro` (
  `pub_identificador` INT NOT NULL,
  `lib_titulo_libro` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`pub_identificador`),
  INDEX `fk_libro_publicacion1_idx` (`pub_identificador` ASC),
  CONSTRAINT `fk_libro_publicacion1`
    FOREIGN KEY (`pub_identificador`)
    REFERENCES `doctorado`.`publicacion` (`pub_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `doctorado`.`capitulo_libro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`capitulo_libro` (
  `pub_identificador` INT NOT NULL,
  `caplib_titulo_libro` VARCHAR(200) NOT NULL,
  `caplib_titulo_capitulo` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`pub_identificador`),
  INDEX `fk_capitulo_libro_publicacion1_idx` (`pub_identificador` ASC),
  CONSTRAINT `fk_capitulo_libro_publicacion1`
    FOREIGN KEY (`pub_identificador`)
    REFERENCES `doctorado`.`publicacion` (`pub_identificador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Fin Creacion Tablas Tipo de Publicacion
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table `doctorado`.`tipo_usuario`
-- Contiene los tipos de usuario. EJ: Profesores, Coordinadores, Estudiantes ...
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`tipo_usuario` ( 
  `id` INT(11) NOT NULL AUTO_INCREMENT, 
  `nombre` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL , 
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `doctorado`.`usuario`
-- Contiene los datos del usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`usuario` ( 
  `id` INT NOT NULL AUTO_INCREMENT, 
  `nombres` VARCHAR(30) NULL , 
  `apellidos` VARCHAR(30) NULL , 
  `nombre_usuario` VARCHAR(30) NOT NULL , 
  `contrasena` VARCHAR(60) NOT NULL , 
  `estado` VARCHAR(20) NOT NULL DEFAULT 'activo' , 
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `doctorado`.`grupo_tipo_usuario`
-- Contiene la relación entre un usuario y un tipo (muchos a muchos)
 
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `doctorado`.`grupo_tipo_usuario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_tipo` INT NOT NULL,
  `id_usuario` INT NOT NULL,
  `nombre_usuario` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
  INDEX `fk_tipo_usuario_idx` (`id_tipo` ASC),
  INDEX `fk_usuario_idx` (`id_usuario` ASC),
  PRIMARY KEY (`id`, `id_tipo`, `id_usuario`),
  CONSTRAINT `fk_tipo_usuario_idx`
    FOREIGN KEY (`id_tipo`)
    REFERENCES `doctorado`.`tipo_usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_idx`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `doctorado`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Datos para la tabla tipo_usuario
 -- -----------------------------------------------------
INSERT INTO `tipo_usuario` (`id`, `nombre`) VALUES ('1', 'PROFESOR'), ('2', 'ESTUDIANTE');
INSERT INTO `tipo_usuario`(`id`, `nombre`) VALUES ('3','COORDINADOR');
-- -----------------------------------------------------
-- Datos para la tabla usuario
 -- -----------------------------------------------------
INSERT INTO `usuario` (`id`, `nombres`, `apellidos`, `nombre_usuario`, `contrasena`, `estado`) VALUES ('1', 'Sant', NULL, 'user1', '123456', 'activo');
INSERT INTO `usuario` (`id`, `nombres`, `apellidos`, `nombre_usuario`, `contrasena`, `estado`) VALUES ('2', 'Alvaro', 'Restrepo', 'alvaro', '123456', 'activo');
-- -----------------------------------------------------
-- Datos para la tabla grupo_tipo_usuario
 -- -----------------------------------------------------
INSERT INTO `grupo_tipo_usuario` (`id`, `id_tipo`, `id_usuario`, `nombre_usuario`) VALUES ('1', '2', '1','user1');
INSERT INTO `grupo_tipo_usuario` (`id`, `id_tipo`, `id_usuario`, `nombre_usuario`) VALUES ('2', '3', '2', 'alvaro');
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;