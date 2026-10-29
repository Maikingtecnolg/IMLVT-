-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: imlvt_bd
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `citas_medicas`
--

DROP TABLE IF EXISTS `citas_medicas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `citas_medicas` (
  `id_cita` int(11) NOT NULL AUTO_INCREMENT,
  `cedula_paciente` varchar(10) NOT NULL,
  `cedula_doctor` varchar(10) NOT NULL,
  `especialidad` varchar(50) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `sintomas` varchar(500) DEFAULT NULL,
  `estado` varchar(45) NOT NULL DEFAULT 'Pendiente',
  PRIMARY KEY (`id_cita`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `citas_medicas`
--

LOCK TABLES `citas_medicas` WRITE;
/*!40000 ALTER TABLE `citas_medicas` DISABLE KEYS */;
INSERT INTO `citas_medicas` VALUES (1,'0801860987','0801234567','Pediatría','2026-10-16','08:00:00',NULL,'Cancelada'),(2,'0801860989','0801234567','Medicina General','2026-10-16','09:00:00',NULL,'Pendiente'),(3,'0805858829','0801234567','Medicina General','2026-10-17','16:00:00',NULL,'Pendiente'),(4,'0801860987','0850000001','Cardiología','2026-11-02','09:30:00','Siento punsadas en el corazon y mi presion cardica es muy irregular','Pendiente');
/*!40000 ALTER TABLE `citas_medicas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados_autorizados`
--

DROP TABLE IF EXISTS `empleados_autorizados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados_autorizados` (
  `cedula` varchar(11) NOT NULL,
  `rol_asignado` varchar(45) NOT NULL,
  PRIMARY KEY (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados_autorizados`
--

LOCK TABLES `empleados_autorizados` WRITE;
/*!40000 ALTER TABLE `empleados_autorizados` DISABLE KEYS */;
INSERT INTO `empleados_autorizados` VALUES ('0801234567','Medico'),('0806537292','Enfermera'),('0809876543','Secretaria');
/*!40000 ALTER TABLE `empleados_autorizados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicamentos_recetados`
--

DROP TABLE IF EXISTS `medicamentos_recetados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicamentos_recetados` (
  `id_medicamento` int(11) NOT NULL AUTO_INCREMENT,
  `medicamento` varchar(100) NOT NULL,
  `dosis` varchar(100) NOT NULL,
  `duracion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_medicamento`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamentos_recetados`
--

LOCK TABLES `medicamentos_recetados` WRITE;
/*!40000 ALTER TABLE `medicamentos_recetados` DISABLE KEYS */;
INSERT INTO `medicamentos_recetados` VALUES (1,'paracetamol','Diaria','1 semana'),(2,'Ibuprofeno','500 mg','2 dias'),(4,'Febra','250 mg','1 semana');
/*!40000 ALTER TABLE `medicamentos_recetados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signos_vitales`
--

DROP TABLE IF EXISTS `signos_vitales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `signos_vitales` (
  `id_triaje` int(11) NOT NULL AUTO_INCREMENT,
  `cedula_paciente` varchar(10) NOT NULL,
  `presion_arterial` varchar(45) DEFAULT NULL,
  `temperatura` double DEFAULT NULL,
  `peso` double DEFAULT NULL,
  `estatura` int(11) DEFAULT NULL,
  `frecuencia_cardiaca` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_triaje`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signos_vitales`
--

LOCK TABLES `signos_vitales` WRITE;
/*!40000 ALTER TABLE `signos_vitales` DISABLE KEYS */;
INSERT INTO `signos_vitales` VALUES (1,'0801807504','175/90',36,63,175,89);
/*!40000 ALTER TABLE `signos_vitales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `cedula` varchar(10) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `especialidad` varchar(100) DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `cedula_UNIQUE` (`cedula`),
  UNIQUE KEY `correo_UNIQUE` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'0801807504','Michael David O M',NULL,'MDOMUS@gmail.com','Ormemi030','Paciente'),(2,'0801234567','Mayte Carolina Perea Castillo','Medicina General','maytesitacastillo01@yahoo.com','Ormemi00','Medico'),(3,'0801860987','Anahi Lilibeth Olives Mendes',NULL,'lilichiquita2005@gmail.com','Lili2005','Paciente'),(160,'0850000001','Dr. Carlos Mendoza','Cardiología','cmendoza@gmail.com','Medico123','Medico'),(161,'0850000002','Dra. Laura Torres','Pediatría','ltorres@gmail.com','Medico123','Medico'),(162,'0850780002','Dr. Ferran Torres','Pediatría','Ferrantorres@gmail.com','Medico123','Medico'),(163,'0850000003','Dr. Andrés Silva','Neurología','asilva@gmail.com','Medico123','Medico'),(164,'0850000004','Dra. Sofía Vargas','Ginecología','svargas@gmail.com','Medico123','Medico'),(165,'0850830002','Dr. Jonhy Simps','Ginecología','drjhonys@gmail.com','Medico123','Medico'),(166,'0850000005','Dr. Luis Paredes','Traumatología','lparedes@gmail.com','Medico123','Medico'),(167,'0850000006','Dra. Diana Castro','Dermatología','dcastro@gmail.com','Medico123','Medico'),(168,'0850050007','Dr. Jorge Ruiz','Oftalmología','jruiz@gmail.com','Medico123','Medico'),(169,'0850043008','Dra. Carmen Ríos','Odontología','crios@gmail.com','Medico123','Medico'),(170,'0850450008','Dra. Carla Rivaldo','Odontología','crvaldos@gmail.com','Medico123','Medico'),(171,'0850000009','Dr. Miguel Santos','Psiquiatría','msantos@gmail.com','Medico123','Medico'),(172,'0853450010','Dra. Elena Mora','Medicina General','emora@gmail.com','Medico123','Medico'),(251,'0805555551','Diana Méndez',NULL,'diana.sec@imlvt.com','12345','Secretaria'),(252,'0805555552','Valeria Castro',NULL,'valeria.sec@imlvt.com','12345','Secretaria'),(253,'0806666661','Jorge Ruiz',NULL,'jorge.enf@imlvt.com','15345','Enfermero'),(254,'0806666662','Carmen Velez',NULL,'carmen.enf@imlvt.com','14355','Enfermera'),(255,'0806666663','Luisa Solis',NULL,'luis.enf@imlvt.com','12454','Enfermero');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-29  0:33:53
