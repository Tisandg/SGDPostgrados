SET FOREIGN_KEY_CHECKS=0;

/*  ALTER's  */

ALTER TABLE congreso ADD ciudad_id int(11);
ALTER TABLE congreso ADD CONSTRAINT FK_congreso_ciudad FOREIGN KEY (ciudad_id) REFERENCES ciudad(ciud_id);

SET FOREIGN_KEY_CHECKS=1;