--alterar tablas de tipo documento
alter table libro add lib_isbn varchar(30);
alter table congreso add (
	cong_doi varchar(30),
	cong_issn varchar(30) 
	);
alter table revista add rev_doi varchar(30);
alter table capitulo_libro add caplib_isbn varchar(30);


--alterar tabla publicacion
alter table publicacion drop pub_doi;
alter table publicacion drop pub_isbn;
alter table publicacion drop pub_issn;
alter table publicacion drop pub_creditos;
alter table publicacion drop pub_nombre_autor;	


--adicionar cmapos para foranneas de tabla usuario
alter table coordinador add usuario_id int(11) not null;
alter table estudiante add usuario_id int(11) not null;

--adicionar foraneas a estudiante y coordinador
alter table coordinador add foreign key(usuario_id) references usuario(id);
set foreign_key_checks = 0;
alter table estudiante add foreign key(usuario_id) references usuario(id);


--modificar creditos por defecto estudiante
alter table estudiante modify est_creditos int(11) default 0;


--adicion tabla tipo_documento	
create table tipo_documento (
	`identificador` int(11) not null AUTO_INCREMENT,
	`nombre` varchar(30) not null,
	`creditos` int(3) not null,
	`correquisitos` int(11),
	PRIMARY KEY(`identificador`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

alter table tipo_documento add foreign key(correquisitos) references tipo_documento(identificador);

alter table publicacion add tipo_documento int(11); 
alter table publicacion add foreign key(id_tipo_documento) references tipo_documento(identificador);


