SET FOREIGN_KEY_CHECKS=0;

/*  CREATE TABLE's  */

DROP TABLE IF EXISTS pais;
CREATE TABLE pais(
  pais_id int(11) NOT NULL AUTO_INCREMENT,
  pais_nombre varchar(40),
  PRIMARY KEY (pais_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS ciudad;
CREATE TABLE ciudad (
  ciud_id int(11) NOT NULL AUTO_INCREMENT,
  ciud_nombre varchar(40),
  pais_id int(11) NOT NULL,
  PRIMARY KEY (ciud_id),
  CONSTRAINT fk_pais_id FOREIGN KEY (pais_id) REFERENCES pais(pais_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*  ALTER's  */

ALTER TABLE libro ADD ciudad_id int(11);
ALTER TABLE libro ADD CONSTRAINT FK_libro_ciudad FOREIGN KEY (ciudad_id) REFERENCES ciudad(ciud_id);

/*  INSERT's  */

INSERT INTO pais VALUES(1,'COLOMBIA');
INSERT INTO pais VALUES(2,'ARGENTINA');
INSERT INTO pais VALUES(3,'ESPAÑA');


INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('BOGOTA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('MEDELLIN',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CALI',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CARTAGENA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('BARRANQUILLA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('BUCARAMANGA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('SANTA MARTA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('MANIZALES',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CÚCUTA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('PASTO',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('PEREIRA',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('POPAYAN',1);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('ARMENIA',1);


INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('BUENOS AIRES',2);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CATAMARCA',2);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CHACO',2);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CÓRDOBA',2);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('CORRIENTES',2);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('MENDOZA',2);


INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('MADRID',3);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('SEVILLA',3);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('ZARAGOZA',3);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('VALENCIA',3);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('PAMPLONA',3);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('LAS PALMAS DE GRAN CANARIA',3);
INSERT INTO ciudad (ciud_nombre, pais_id) VALUES ('ALICANTE',3);


SET FOREIGN_KEY_CHECKS=1;