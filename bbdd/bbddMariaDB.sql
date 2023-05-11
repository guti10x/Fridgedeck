-- MySQL dump 10.16  Distrib 10.1.48-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	10.1.48-MariaDB-0+deb9u2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Fridge`
--

DROP TABLE IF EXISTS `Fridge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Fridge` (
  `id` tinyint(4) DEFAULT NULL,
  `subscription` varchar(7) DEFAULT NULL,
  `country` varchar(5) DEFAULT NULL,
  `CCAA` varchar(9) DEFAULT NULL,
  `municipality` varchar(9) DEFAULT NULL,
  `street` varchar(26) DEFAULT NULL,
  `number` smallint(6) DEFAULT NULL,
  `CPP` mediumint(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Fridge`
--

LOCK TABLES `Fridge` WRITE;
/*!40000 ALTER TABLE `Fridge` DISABLE KEYS */;
INSERT INTO `Fridge` VALUES (1,'Monthly','Spain','Madrid','Madrid','Gran Vía',123,28013),(2,'Yearly','Spain','Valencia','Valencia','Calle de la Paz',6,46003),(3,'Monthly','Spain','Cataluña','Barcelona','Rambla de Catalunya',20,8007),(4,'Yearly','Spain','Andalucía','Sevilla','Avenida de la Constitución',12,41001);
/*!40000 ALTER TABLE `Fridge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Humedad`
--

DROP TABLE IF EXISTS `Humedad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Humedad` (
  `id` tinyint(4) DEFAULT NULL,
  `fecha` varchar(0) DEFAULT NULL,
  `valor` varchar(3) DEFAULT NULL,
  `id_nevera` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Humedad`
--

LOCK TABLES `Humedad` WRITE;
/*!40000 ALTER TABLE `Humedad` DISABLE KEYS */;
INSERT INTO `Humedad` VALUES (1,'','70%',1),(2,'','68%',1),(3,'','72%',1),(4,'','71%',1),(5,'','69%',1),(6,'','75%',2),(7,'','73%',2),(8,'','76%',2),(9,'','74%',2),(10,'','72%',2),(11,'','65%',3),(12,'','64%',3),(13,'','68%',3),(14,'','70%',3),(15,'','67%',3),(16,'','78%',4),(17,'','79%',4),(18,'','80%',4),(19,'','81%',4),(20,'','82%',4);
/*!40000 ALTER TABLE `Humedad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mensajes`
--

DROP TABLE IF EXISTS `Mensajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mensajes` (
  `id` tinyint(4) DEFAULT NULL,
  `fecha` varchar(0) DEFAULT NULL,
  `contenido` varchar(65) DEFAULT NULL,
  `lectura` tinyint(4) DEFAULT NULL,
  `id_nevera` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mensajes`
--

LOCK TABLES `Mensajes` WRITE;
/*!40000 ALTER TABLE `Mensajes` DISABLE KEYS */;
INSERT INTO `Mensajes` VALUES (3,'','Ya se ha solucionado la incidencia',1,1),(4,'','cesta de la compra realizada',0,2),(5,'','Averia solucionada',1,2),(6,'','La goma de cierre de la puerta de la nevera necesita ser cambiada',0,2),(7,'','Cesta de la compra entregada ',1,3),(8,'','Invemtario de la nevera repuesto',0,4),(9,'',' Limpieza de la nevera completada',1,1),(10,'','Se han retirado 3 articulos caducados de la nevera',0,2),(11,'','La entrega de la cesta de la compra se retrasará a mañana',1,4);
/*!40000 ALTER TABLE `Mensajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notificaciones`
--

DROP TABLE IF EXISTS `Notificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notificaciones` (
  `id` tinyint(4) DEFAULT NULL,
  `nombre` varchar(26) DEFAULT NULL,
  `tipo` varchar(6) DEFAULT NULL,
  `descripción` varchar(45) DEFAULT NULL,
  `fecha` varchar(0) DEFAULT NULL,
  `id_nevera` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notificaciones`
--

LOCK TABLES `Notificaciones` WRITE;
/*!40000 ALTER TABLE `Notificaciones` DISABLE KEYS */;
INSERT INTO `Notificaciones` VALUES (1,'Humedad anormal','Aviso','La humedad en la nevera es anormal','',1),(2,'Temperatura anormal','Aviso','La temperatura en la nevera es anormal','',1),(3,'Puerta abierta','Alerta','La puerta de la nevera está abierta','',1),(4,'Error técnico en la nevera','Alerta','Se ha detectado un error técnico en la nevera','',2),(5,'Error en el sistema','Alerta','Se ha producido un error en el sistema','',2),(6,'Humedad anormal','Aviso','La humedad en la nevera es anormal','',3),(7,'Temperatura anormal','Aviso','La temperatura en la nevera es anormal','',3),(8,'Puerta abierta','Alerta','La puerta de la nevera está abierta','',4),(9,'Error técnico en la nevera','Alerta','Se ha detectado un error técnico en la nevera','',5),(10,'Error en el sistema','Alerta','Se ha producido un error en el sistema','',6);
/*!40000 ALTER TABLE `Notificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Productos`
--

DROP TABLE IF EXISTS `Productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Productos` (
  `id` tinyint(4) DEFAULT NULL,
  `nombre` varchar(16) DEFAULT NULL,
  `descripción` varchar(40) DEFAULT NULL,
  `stock` smallint(6) DEFAULT NULL,
  `fecha` varchar(0) DEFAULT NULL,
  `id_nevera` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Productos`
--

LOCK TABLES `Productos` WRITE;
/*!40000 ALTER TABLE `Productos` DISABLE KEYS */;
INSERT INTO `Productos` VALUES (1,'Leche','Leche fresca entera',50,'',1),(2,'Queso','Queso cheddar madurado',20,'',1),(3,'Yogur','Yogur natural bajo en grasa',40,'',1),(4,'Mantequilla','Mantequilla sin sal',30,'',1),(5,'Jugo de naranja','Jugo de naranja recién exprimido',15,'',2),(6,'Jugo de manzana','Jugo de manzana 100% natural',10,'',2),(7,'Jugo de uva','Jugo de uva sin azúcar añadido',20,'',2),(8,'Agua mineral','Agua mineral natural',100,'',2),(9,'Coca-Cola','Refresco de cola',25,'',3),(10,'Pepsi','Refresco de cola con sabor a lima',30,'',3),(11,'Fanta','Refresco de naranja',20,'',3),(12,'Sprite','Refresco de lima-limón',15,'',3),(13,'Cerveza Corona','Cerveza mexicana de estilo Lager',40,'',4),(14,'Cerveza Heineken','Cerveza holandesa de estilo Lager',50,'',4),(15,'Cerveza Guinness','Cerveza irlandesa negra de estilo Stout',20,'',4),(16,'Vino tinto','Vino tinto reserva de la Rioja',10,'',4),(17,'Pan de molde','Pan de molde integral',30,'',5),(18,'Pan integral','Pan integral recién horneado',25,'',5),(19,'Baguette','Baguette crujiente recién horneada',20,'',5),(20,'Croissant','Croissant de mantequilla recién horneado',15,'',5),(21,'Huevos','Huevos frescos de granja',50,'',6);
/*!40000 ALTER TABLE `Productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Puerta`
--

DROP TABLE IF EXISTS `Puerta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Puerta` (
  `id` tinyint(4) DEFAULT NULL,
  `fecha` varchar(0) DEFAULT NULL,
  `valor` tinyint(4) DEFAULT NULL,
  `id_nevera` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Puerta`
--

LOCK TABLES `Puerta` WRITE;
/*!40000 ALTER TABLE `Puerta` DISABLE KEYS */;
INSERT INTO `Puerta` VALUES (1,'',1,1),(2,'',0,1),(3,'',1,1),(4,'',0,1),(5,'',1,1),(6,'',0,2),(7,'',1,2),(8,'',0,2),(9,'',1,2),(10,'',0,2),(11,'',1,3),(12,'',0,3),(13,'',1,3),(14,'',0,3),(15,'',1,3),(16,'',0,4),(17,'',1,4),(18,'',0,4),(19,'',1,4),(20,'',0,4);
/*!40000 ALTER TABLE `Puerta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subscribe`
--

DROP TABLE IF EXISTS `Subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subscribe` (
  `id_nevera` tinyint(4) DEFAULT NULL,
  `id_user` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscribe`
--

LOCK TABLES `Subscribe` WRITE;
/*!40000 ALTER TABLE `Subscribe` DISABLE KEYS */;
INSERT INTO `Subscribe` VALUES (1,1),(2,2),(3,3),(4,4),(1,5),(2,5),(3,6),(4,6),(1,7),(2,7),(3,8),(4,8);
/*!40000 ALTER TABLE `Subscribe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Temperatura`
--

DROP TABLE IF EXISTS `Temperatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Temperatura` (
  `id` tinyint(4) DEFAULT NULL,
  `fecha` varchar(0) DEFAULT NULL,
  `valor` varchar(4) DEFAULT NULL,
  `id_nevera` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Temperatura`
--

LOCK TABLES `Temperatura` WRITE;
/*!40000 ALTER TABLE `Temperatura` DISABLE KEYS */;
INSERT INTO `Temperatura` VALUES (1,'','20°C',1),(2,'','19°C',1),(3,'','22°C',1),(4,'','21°C',1),(5,'','18°C',1),(6,'','25°C',2),(7,'','23°C',2),(8,'','26°C',2),(9,'','24°C',2),(10,'','22°C',2),(11,'','15°C',3),(12,'','14°C',3),(13,'','18°C',3),(14,'','20°C',3),(15,'','17°C',3),(16,'','28°C',4),(17,'','29°C',4),(18,'','30°C',4),(19,'','31°C',4),(20,'','32°C',4);
/*!40000 ALTER TABLE `Temperatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuarios`
--

DROP TABLE IF EXISTS `Usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuarios` (
  `id` tinyint(4) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  `username` varchar(8) DEFAULT NULL,
  `nombre` varchar(9) DEFAULT NULL,
  `surname1` varchar(10) DEFAULT NULL,
  `surname2` varchar(10) DEFAULT NULL,
  `email` varchar(22) DEFAULT NULL,
  `password` varchar(11) DEFAULT NULL,
  `birthdate` varchar(0) DEFAULT NULL,
  `credit_card` bigint(20) DEFAULT NULL,
  `telephone_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuarios`
--

LOCK TABLES `Usuarios` WRITE;
/*!40000 ALTER TABLE `Usuarios` DISABLE KEYS */;
INSERT INTO `Usuarios` VALUES (1,'user','dani_','Daniel','Gutiérrez','Torres','gt104515gmail.com','dani10','',1015724736,673484600),(2,'user','Leon','Leon','Karagishev','Karagishev','l.karagishev@gmail.com','leonpasswd','',-374136698,987654321),(3,'user','Gille12','Guillermo','Aos','One','guille@gmail.com','password789','',986516178,567890123),(4,'user','Álvaro33','Álvaro','Doe','Brown','alvaro@gmail.com','passwordabc','',1764029262,345678901),(5,'repartidor','Ivi','Iván','Doe','Wilson','ivan@gmail.com','ivan','',-2048902636,789012345),(6,'repartidor','Adri','Adriana','Doe','Lee','adrinaCruz@gmail.com','passwordjkl','',-307606649,234567890),(7,'técnico','Alba2344','Alba','Doe','Miller','alba@gmail.com','alba','',490564661,678901234),(8,'técnico','pb','Pablo','Martinez','Valero','padaro@gmail.com.com','passwordstu','',1015724736,123456789);
/*!40000 ALTER TABLE `Usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sqlite_sequence`
--

DROP TABLE IF EXISTS `sqlite_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sqlite_sequence` (
  `name` varchar(14) DEFAULT NULL,
  `seq` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sqlite_sequence`
--

LOCK TABLES `sqlite_sequence` WRITE;
/*!40000 ALTER TABLE `sqlite_sequence` DISABLE KEYS */;
INSERT INTO `sqlite_sequence` VALUES ('Fridge',4),('Productos',21),('Notificaciones',10),('Humedad',20),('Temperatura',20),('Puerta',20),('Usuarios',8),('Mensajes',12);
/*!40000 ALTER TABLE `sqlite_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-27 22:44:48
