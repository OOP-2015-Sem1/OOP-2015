-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: digitalschool
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.9-MariaDB

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
-- Table structure for table `biologie`
--

DROP TABLE IF EXISTS `biologie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biologie` (
  `studentidbiologie` int(11) NOT NULL,
  `notebiologie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentidbiologie`),
  CONSTRAINT `studentidbio` FOREIGN KEY (`studentidbiologie`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biologie`
--

LOCK TABLES `biologie` WRITE;
/*!40000 ALTER TABLE `biologie` DISABLE KEYS */;
INSERT INTO `biologie` VALUES (1,'1');
/*!40000 ALTER TABLE `biologie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chimie`
--

DROP TABLE IF EXISTS `chimie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chimie` (
  `studentidchimie` int(11) NOT NULL,
  `notechimie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentidchimie`),
  CONSTRAINT `studentidchimie` FOREIGN KEY (`studentidchimie`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chimie`
--

LOCK TABLES `chimie` WRITE;
/*!40000 ALTER TABLE `chimie` DISABLE KEYS */;
INSERT INTO `chimie` VALUES (1,'1');
/*!40000 ALTER TABLE `chimie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classroom`
--

DROP TABLE IF EXISTS `classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classroom` (
  `idclassroom` int(11) NOT NULL,
  `classgrade` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idclassroom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classroom`
--

LOCK TABLES `classroom` WRITE;
/*!40000 ALTER TABLE `classroom` DISABLE KEYS */;
INSERT INTO `classroom` VALUES (1,'1'),(2,'2'),(3,'3'),(4,'4'),(5,'5'),(6,'6'),(7,'7'),(8,'8'),(9,'9'),(10,'10'),(11,'11'),(12,'12');
/*!40000 ALTER TABLE `classroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educatiefizica`
--

DROP TABLE IF EXISTS `educatiefizica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educatiefizica` (
  `studentideducatiefizica` int(11) NOT NULL,
  `noteeducatiefizica` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentideducatiefizica`),
  CONSTRAINT `studentkeyeducatiefizica` FOREIGN KEY (`studentideducatiefizica`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educatiefizica`
--

LOCK TABLES `educatiefizica` WRITE;
/*!40000 ALTER TABLE `educatiefizica` DISABLE KEYS */;
INSERT INTO `educatiefizica` VALUES (1,'1');
/*!40000 ALTER TABLE `educatiefizica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fizica`
--

DROP TABLE IF EXISTS `fizica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fizica` (
  `studentidfizica` int(11) NOT NULL,
  `notefizica` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentidfizica`),
  CONSTRAINT `studentkeey` FOREIGN KEY (`studentidfizica`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fizica`
--

LOCK TABLES `fizica` WRITE;
/*!40000 ALTER TABLE `fizica` DISABLE KEYS */;
INSERT INTO `fizica` VALUES (1,'1');
/*!40000 ALTER TABLE `fizica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geografie`
--

DROP TABLE IF EXISTS `geografie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `geografie` (
  `studentidgeografie` int(11) NOT NULL,
  `notegeografie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentidgeografie`),
  CONSTRAINT `studentkeygeografie` FOREIGN KEY (`studentidgeografie`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geografie`
--

LOCK TABLES `geografie` WRITE;
/*!40000 ALTER TABLE `geografie` DISABLE KEYS */;
INSERT INTO `geografie` VALUES (1,'1');
/*!40000 ALTER TABLE `geografie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `istorie`
--

DROP TABLE IF EXISTS `istorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `istorie` (
  `studentidistorie` int(11) NOT NULL,
  `noteistorie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentidistorie`),
  CONSTRAINT `studentkeyistorie` FOREIGN KEY (`studentidistorie`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `istorie`
--

LOCK TABLES `istorie` WRITE;
/*!40000 ALTER TABLE `istorie` DISABLE KEYS */;
INSERT INTO `istorie` VALUES (1,'1');
/*!40000 ALTER TABLE `istorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matematica`
--

DROP TABLE IF EXISTS `matematica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matematica` (
  `idstudent` int(11) NOT NULL,
  `notematematica` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idstudent`),
  CONSTRAINT `studentkey` FOREIGN KEY (`idstudent`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matematica`
--

LOCK TABLES `matematica` WRITE;
/*!40000 ALTER TABLE `matematica` DISABLE KEYS */;
INSERT INTO `matematica` VALUES (1,'1');
/*!40000 ALTER TABLE `matematica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `new_view`
--

DROP TABLE IF EXISTS `new_view`;
/*!50001 DROP VIEW IF EXISTS `new_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `new_view` AS SELECT 
 1 AS `notematematica`,
 1 AS `notebiologie`,
 1 AS `notechimie`,
 1 AS `noteeducatiefizica`,
 1 AS `notefizica`,
 1 AS `notegeografie`,
 1 AS `noteistorie`,
 1 AS `notepsihologie`,
 1 AS `noteromana`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `psihologie`
--

DROP TABLE IF EXISTS `psihologie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `psihologie` (
  `studentidpsihologie` int(11) NOT NULL,
  `notepsihologie` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentidpsihologie`),
  CONSTRAINT `studentkeypsihologie` FOREIGN KEY (`studentidpsihologie`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `psihologie`
--

LOCK TABLES `psihologie` WRITE;
/*!40000 ALTER TABLE `psihologie` DISABLE KEYS */;
INSERT INTO `psihologie` VALUES (1,'1');
/*!40000 ALTER TABLE `psihologie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `romana`
--

DROP TABLE IF EXISTS `romana`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `romana` (
  `studentid` int(11) NOT NULL,
  `noteromana` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`studentid`),
  CONSTRAINT `studentkeyromana` FOREIGN KEY (`studentid`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `romana`
--

LOCK TABLES `romana` WRITE;
/*!40000 ALTER TABLE `romana` DISABLE KEYS */;
INSERT INTO `romana` VALUES (1,'1');
/*!40000 ALTER TABLE `romana` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `idstudent` int(11) NOT NULL,
  `studentname` varchar(45) DEFAULT NULL,
  `classid` int(11) NOT NULL,
  `studentuser` varchar(45) DEFAULT NULL,
  `studentpassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idstudent`,`classid`),
  KEY `classroom_idx` (`classid`),
  CONSTRAINT `classroom` FOREIGN KEY (`classid`) REFERENCES `classroom` (`idclassroom`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'hector',1,'student','pas1'),(2,'maximilian',2,'student','pas'),(3,'vasile',3,'student','pas'),(4,'ion',4,'student','pas'),(5,'andrei',5,'student','pas'),(6,'maria',6,'student','pas'),(7,'pop',7,'student','pas'),(8,'ana',8,'student','pas'),(9,'cal',9,'student','pas'),(10,'marinela',10,'student','pas'),(11,'florinel',11,'student','pas'),(12,'saveta',12,'student','pas');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subject` (
  `idsubject` int(11) NOT NULL,
  `subjectname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idsubject`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'Matematica'),(2,'Romana'),(3,'Fizica'),(4,'Biologie'),(5,'Chimie'),(6,'Istorie'),(7,'Geografie'),(8,'Educatie Fizica'),(9,'Psihologie'),(10,'Invatator');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `idteacher` int(11) NOT NULL,
  `teachername` varchar(45) DEFAULT NULL,
  `subjectid` int(11) NOT NULL,
  `teacheruser` varchar(45) DEFAULT NULL,
  `teacherpassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idteacher`),
  KEY `subjectkey_idx` (`subjectid`),
  CONSTRAINT `subjectkey` FOREIGN KEY (`subjectid`) REFERENCES `subject` (`idsubject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (1,'Marius Cicortas',1,'teacher','pas'),(2,'Medan Eugenia',1,'teacher','pas'),(3,'Fildan Mihaela',2,'teacher','pas'),(4,'Popa Dorina',2,'teacher','pas'),(5,'Berian Sergiu',3,'teacher','pas'),(6,'Nagy Anna',4,'teacher','pas'),(7,'Marinela Pop',5,'teacher','pas'),(8,'Fiter Ciprian',8,'teacher','pas'),(9,'Ivan Daniel',9,'teacher','pas'),(10,'Istoc Florin',6,'teacher','pas'),(11,'Istoc Ramon',7,'teacher','pas'),(12,'Hector Daniel',10,'teacher','pas'),(13,'Ionutz Petrutz',10,'teacher','pas'),(14,'Diana Marcis',10,'teacher','pas'),(15,'Vasile Pop',10,'teacher','pas');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacherclassroom`
--

DROP TABLE IF EXISTS `teacherclassroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacherclassroom` (
  `teacherid` int(11) NOT NULL,
  `classid` int(11) NOT NULL,
  PRIMARY KEY (`teacherid`,`classid`),
  KEY `classroomkey_idx` (`classid`),
  CONSTRAINT `classroomkey` FOREIGN KEY (`classid`) REFERENCES `classroom` (`idclassroom`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `teacherkey` FOREIGN KEY (`teacherid`) REFERENCES `teacher` (`idteacher`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacherclassroom`
--

LOCK TABLES `teacherclassroom` WRITE;
/*!40000 ALTER TABLE `teacherclassroom` DISABLE KEYS */;
INSERT INTO `teacherclassroom` VALUES (1,5),(1,9),(1,10),(1,11),(2,6),(2,7),(2,8),(2,12),(3,5),(3,6),(3,7),(3,8),(4,9),(4,10),(4,11),(4,12),(5,9),(5,10),(5,11),(5,12),(6,7),(6,8),(6,9),(6,10),(6,11),(6,12),(7,9),(7,10),(7,11),(7,12),(8,5),(8,6),(8,7),(8,8),(8,9),(8,10),(8,11),(8,12),(9,11),(9,12),(10,5),(10,6),(10,7),(10,8),(11,9),(11,10),(11,11),(11,12),(12,1),(13,2),(14,3),(15,4);
/*!40000 ALTER TABLE `teacherclassroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `new_view`
--

/*!50001 DROP VIEW IF EXISTS `new_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `new_view` AS select `matematica`.`notematematica` AS `notematematica`,`biologie`.`notebiologie` AS `notebiologie`,`chimie`.`notechimie` AS `notechimie`,`educatiefizica`.`noteeducatiefizica` AS `noteeducatiefizica`,`fizica`.`notefizica` AS `notefizica`,`geografie`.`notegeografie` AS `notegeografie`,`istorie`.`noteistorie` AS `noteistorie`,`psihologie`.`notepsihologie` AS `notepsihologie`,`romana`.`noteromana` AS `noteromana` from (((((((((`student` join `matematica` on((`student`.`idstudent` = `matematica`.`idstudent`))) join `biologie` on((`student`.`idstudent` = `biologie`.`studentidbiologie`))) join `chimie` on((`student`.`idstudent` = `chimie`.`studentidchimie`))) join `educatiefizica` on((`student`.`idstudent` = `educatiefizica`.`studentideducatiefizica`))) join `fizica` on((`student`.`idstudent` = `fizica`.`studentidfizica`))) join `geografie` on((`student`.`idstudent` = `geografie`.`studentidgeografie`))) join `istorie` on((`student`.`idstudent` = `istorie`.`studentidistorie`))) join `psihologie` on((`student`.`idstudent` = `psihologie`.`studentidpsihologie`))) join `romana` on((`student`.`idstudent` = `romana`.`studentid`))) where (`student`.`studentname` = 'hector') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-09 14:44:49
