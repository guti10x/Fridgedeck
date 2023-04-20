BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS Fridge (
	id INT NOT NULL AUTO_INCREMENT,
	subscription TEXT NOT NULL,
	country TEXT NOT NULL,
	CCAA TEXT NOT NULL,
	municipality TEXT NOT NULL,
	street TEXT NOT NULL,
	number VARCHAR(10) NOT NULL,
	CPP INT NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Productos (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	nombre TEXT NOT NULL,
	descripcion TEXT NOT NULL,
	stock INT NOT NULL,
	fecha DATE NOT NULL,
	id_nevera INT NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Notificaciones (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	nombre TEXT NOT NULL,
	tipo TEXT NOT NULL,
	descripcion TEXT NOT NULL,
	fecha DATE NOT NULL,
	id_nevera INT NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Humedad (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	fecha DATE NOT NULL,
	valor VARCHAR(10) NOT NULL,
	id_nevera INT NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Temperatura (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	fecha DATE NOT NULL,
	valor VARCHAR(10) NOT NULL,
	id_nevera INT NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Puerta (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	fecha DATE NOT NULL,
	valor INT NOT NULL,
	id_nevera INT NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Subscribe (
	id_nevera INT NOT NULL,
	id_user INT NOT NULL,
	PRIMARY KEY (id_nevera, id_user),
	FOREIGN KEY (id_nevera) REFERENCES Fridge (id),
	FOREIGN KEY (id_user) REFERENCES Usuarios (id),
	INDEX fk_subscribe_fridge_idx (id_nevera),
	INDEX fk_subscribe_usuarios_idx (id_user)
);
CREATE TABLE IF NOT EXISTS Usuarios (
	id INT NOT NULL AUTO_INCREMENT UNIQUE,
	role TEXT NOT NULL,
	username TEXT NOT NULL,
	nombre TEXT NOT NULL,
	surname1 TEXT NOT NULL,
	surname2 TEXT NOT NULL,
	email TEXT NOT NULL,
	password TEXT NOT NULL,
	birthdate DATETIME NOT NULL,
	credit_card INTEGER(20) NOT NULL,
	telephone_number INTEGER NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Mensajes (
	id INT NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	contenido TEXT NOT NULL,
	lectura INT NOT NULL,
	id_nevera INT NOT NULL,
	PRIMARY KEY (id),
	INDEX idx_fecha (fecha),
	INDEX idx_lectura (lectura),
	INDEX idx_id_nevera (id_nevera)
);
INSERT INTO Fridge (id, subscription, country, CCAA, municipality, street, number, CPP) VALUES 
(1, 'Monthly', 'Spain', 'Madrid', 'Madrid', 'Gran Vía', '123', 28013),
(2, 'Yearly', 'Spain', 'Valencia', 'Valencia', 'Calle de la Paz', '6', 46003),
(3, 'Monthly', 'Spain', 'Cataluña', 'Barcelona', 'Rambla de Catalunya', '20', 8007),
(4, 'Yearly', 'Spain', 'Andalucía', 'Sevilla', 'Avenida de la Constitución', '12', 41001);

INSERT INTO Productos (id, nombre, descripcion, stock, fecha, id_nevera) VALUES
(1,'Leche','Leche fresca entera',50,'2023-03-17',1),
(2,'Queso','Queso cheddar madurado',20,'2023-03-17',1),
(3,'Yogur','Yogur natural bajo en grasa',40,'2023-03-17',1),
(4,'Mantequilla','Mantequilla sin sal',30,'2023-03-17',1),
(5,'Jugo de naranja','Jugo de naranja recién exprimido',15,'2023-03-17',2),
(6,'Jugo de manzana','Jugo de manzana 100% natural',10,'2023-03-17',2),
(7,'Jugo de uva','Jugo de uva sin azúcar añadido',20,'2023-03-17',2),
(8,'Agua mineral','Agua mineral natural',100,'2023-03-17',2),
(9,'Coca-Cola','Refresco de cola',25,'2023-03-17',3),
(10,'Pepsi','Refresco de cola con sabor a lima',30,'2023-03-17',3),
(11,'Fanta','Refresco de naranja',20,'2023-03-17',3),
(12,'Sprite','Refresco de lima-limón',15,'2023-03-17',3),
(13,'Cerveza Corona','Cerveza mexicana de estilo Lager',40,'2023-03-17',4),
(14,'Cerveza Heineken','Cerveza holandesa de estilo Lager',50,'2023-03-17',4),
(15,'Cerveza Guinness','Cerveza irlandesa negra de estilo Stout',20,'2023-03-17',4),
(16,'Vino tinto','Vino tinto reserva de la Rioja',10,'2023-03-17',4),
(17,'Pan de molde','Pan de molde integral',30,'2023-03-17',5),
(18,'Pan integral','Pan integral recién horneado',25,'2023-03-17',5),
(19,'Baguette','Baguette crujiente recién horneada',20,'2023-03-17',5),
(20,'Croissant','Croissant de mantequilla recién horneado',15,'2023-03-17',5),
(21,'Huevos','Huevos frescos de granja',50,'2023-03-17',6);
INSERT INTO `Notificaciones` (`id`,`nombre`,`tipo`,`descripcion`,`fecha`,`id_nevera`) VALUES 
(1,'Humedad anormal','Aviso','La humedad en la nevera es anormal','2023-03-17',1),
(2,'Temperatura anormal','Aviso','La temperatura en la nevera es anormal','2023-03-18',1),
(3,'Puerta abierta','Alerta','La puerta de la nevera está abierta','2023-03-19',1),
(4,'Error técnico en la nevera','Alerta','Se ha detectado un error técnico en la nevera','2023-03-20',2),
(5,'Error en el sistema','Alerta','Se ha producido un error en el sistema','2023-03-21',2),
(6,'Humedad anormal','Aviso','La humedad en la nevera es anormal','2023-03-22',3),
(7,'Temperatura anormal','Aviso','La temperatura en la nevera es anormal','2023-03-23',3),
(8,'Puerta abierta','Alerta','La puerta de la nevera está abierta','2023-03-24',4),
(9,'Error técnico en la nevera','Alerta','Se ha detectado un error técnico en la nevera','2023-03-25',5),
(10,'Error en el sistema','Alerta','Se ha producido un error en el sistema','2023-03-26',6);
INSERT INTO Humedad (id, fecha, valor, id_nevera) VALUES 
(1, '2022-01-01', '70%', 1),
(2, '2022-01-02', '68%', 1),
(3, '2022-01-03', '72%', 1),
(4, '2022-01-04', '71%', 1),
(5, '2022-01-05', '69%', 1),
(6, '2022-01-06', '75%', 2),
(7, '2022-01-07', '73%', 2),
(8, '2022-01-08', '76%', 2),
(9, '2022-01-09', '74%', 2),
(10, '2022-01-10', '72%', 2),
(11, '2022-01-01', '65%', 3),
(12, '2022-01-02', '64%', 3),
(13, '2022-01-03', '68%', 3),
(14, '2022-01-04', '70%', 3),
(15, '2022-01-05', '67%', 3),
(16, '2022-01-06', '78%', 4),
(17, '2022-01-07', '79%', 4),
(18, '2022-01-08', '80%', 4),
(19, '2022-01-09', '81%', 4),
(20, '2022-01-10', '82%', 4);
INSERT INTO Temperatura (id, fecha, valor, id_nevera) VALUES 
(1, '2022-01-01', '20°C', 1),
(2, '2022-01-02', '19°C', 1),
(3, '2022-01-03', '22°C', 1),
(4, '2022-01-04', '21°C', 1),
(5, '2022-01-05', '18°C', 1),
(6, '2022-01-06', '25°C', 2),
(7, '2022-01-07', '23°C', 2),
(8, '2022-01-08', '26°C', 2),
(9, '2022-01-09', '24°C', 2),
(10, '2022-01-10', '22°C', 2),
(11, '2022-01-01', '15°C', 3),
(12, '2022-01-02', '14°C', 3),
(13, '2022-01-03', '18°C', 3),
(14, '2022-01-04', '20°C', 3),
(15, '2022-01-05', '17°C', 3),
(16, '2022-01-06', '28°C', 4),
(17, '2022-01-07', '29°C', 4),
(18, '2022-01-08', '30°C', 4),
(19, '2022-01-09', '31°C', 4),
(20, '2022-01-10', '32°C', 4);
INSERT INTO Puerta (id, fecha, valor, id_nevera) VALUES
(1,'2022-01-01',1,1),
(2,'2022-01-02',0,1),
(3,'2022-01-03',1,1),
(4,'2022-01-04',0,1),
(5,'2022-01-05',1,1),
(6,'2022-01-06',0,2),
(7,'2022-01-07',1,2),
(8,'2022-01-08',0,2),
(9,'2022-01-09',1,2),
(10,'2022-01-10',0,2),
(11,'2022-01-01',1,3),
(12,'2022-01-02',0,3),
(13,'2022-01-03',1,3),
(14,'2022-01-04',0,3),
(15,'2022-01-05',1,3),
(16,'2022-01-06',0,4),
(17,'2022-01-07',1,4),
(18,'2022-01-08',0,4),
(19,'2022-01-09',1,4),
(20,'2022-01-10',0,4);
INSERT INTO `Usuarios` (`id`,`role`,`username`,`nombre`,`surname1`,`surname2`,`email`,`password`,`birthdate`,`credit_card`,`telephone_number`) VALUES
(1,'user','dani_','Daniel','Gutiérrez','Torres','gt104515@gmail.com','dani10','1990-05-15',1234567890123456,673484600),
(2,'user','Leon','Leon','Karagishev','Karagishev','l.karagishev@gmail.com','leonpasswd','1995-02-28',9876543210987654,987654321),
(3,'user','Gille12','Guillermo','Aos','One','guille@gmail.com','password789','1980-01-01',5678901234567890,567890123),
(4,'user','Álvaro33','Álvaro','Doe','Brown','alvaro@gmail.com','passwordabc','1987-09-22',3456789012345678,345678901),
(5,'repartidor','Ivi','Iván','Doe','Wilson','ivan@gmail.com','ivan','1997-07-07',7890123456789012,789012345),
(6,'repartidor','Adri','Adriana','Doe','Lee','adrinaCruz@gmail.com','passwordjkl','1985-04-18',2345678901234567,234567890),
(7,'técnico','Alba2344','Alba','Doe','Miller','alba@gmail.com','alba','1994-06-09',6789012345678901,678901234),
(8,'técnico','pb','Pablo','Martinez','Valero','padaro@gmail.com','passwordstu','2002-03-03',1234567890123456,123456789);
INSERT INTO Subscribe (id_nevera, id_user) VALUES (1,1),
 (2,2),
 (3,3),
 (4,4),
 (1,5),
 (2,5),
 (3,6),
 (4,6),
 (1,7),
 (2,7),
 (3,8),
 (4,8);
INSERT INTO "Mensajes" ("id","fecha","contenido","lectura","id_nevera") VALUES (3,'2023-03-27 14:30:00','Ya se ha solucionado la incidencia',1,1),
 (4,'2022-01-03 14:08:00','cesta de la compra realizada',0,2),
 (5,'2022-01-03 12:03:00','Averia solucionada',1,2),
 (6,'2022-01-04 1:12:00','La goma de cierre de la puerta de la nevera necesita ser cambiada',0,2),
 (7,'2022-01-05 8:24:00','Cesta de la compra entregada ',1,3),
 (8,'2022-01-11 15:33:00','Invemtario de la nevera repuesto',0,4),
 (9,'2022-01-21 16:01:00',' Limpieza de la nevera completada',1,1),
 (10,'2022-01-22 17:13:00','Se han retirado 3 articulos caducados de la nevera',0,2),
 (11,'2022-01-22 23:23:00','La entrega de la cesta de la compra se retrasará a mañana',1,4);
COMMIT;
