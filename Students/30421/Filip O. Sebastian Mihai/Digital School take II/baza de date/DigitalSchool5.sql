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
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grades` (
  `subjectId` int(11) NOT NULL,
  `studentIdGrades` int(11) NOT NULL,
  `Grade` varchar(45) DEFAULT NULL,
  `Absence` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subjectId`,`studentIdGrades`),
  KEY `studentkey_idx` (`studentIdGrades`),
  CONSTRAINT `studentkeeey` FOREIGN KEY (`studentIdGrades`) REFERENCES `student` (`idstudent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `subjectkeey` FOREIGN KEY (`subjectId`) REFERENCES `subject` (`idsubject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (1,1,'4, 5, 6','10/11'),(1,2,'2','null'),(1,3,'2','null'),(1,4,'2','null'),(1,5,'null','10/12/2016'),(1,6,'2','null'),(1,7,'2','null'),(1,8,'2','1/12'),(1,9,'2','null'),(1,10,'2','null'),(1,11,'10, 10','12/12, 01/03'),(1,12,'10','12/10, 10/10'),(1,13,'4, 5, 89','12/12'),(1,14,'4, 5, 6','10/11'),(1,15,'2','null'),(1,16,'2','null'),(1,17,'10','12/12/2016'),(1,18,'null','null'),(1,19,'2','null'),(1,20,'2','null'),(1,21,'2','null'),(1,22,'2','null'),(1,23,'2, 10, 12, 4','12/10, 01/02'),(1,24,'10','12/12, 09/12'),(2,1,'4, 5, 6','10/11'),(2,2,'2','null'),(2,3,'2','null'),(2,4,'2','null'),(2,5,'null','10/12/2016'),(2,6,'2','null'),(2,7,'2','null'),(2,8,'2','1/12'),(2,9,'2','null'),(2,10,'2','null'),(2,11,'10, 10','12/12, 01/03'),(2,12,'11','12/10, 10/10'),(2,13,'4, 5, 89','12/12'),(2,14,'4, 5, 6','10/11'),(2,15,'2','null'),(2,16,'2','null'),(2,17,'10','12/12/2016'),(2,18,'null','null'),(2,19,'2','null'),(2,20,'2','null'),(2,21,'2','null'),(2,22,'2','null'),(2,23,'2, 10, 12, 4','12/10, 01/02'),(2,24,'10','12/12, 09/12'),(3,1,'4, 5, 6','10/11'),(3,2,'2','null'),(3,3,'2','null'),(3,4,'2','null'),(3,5,'null','10/12/2016'),(3,6,'2','null'),(3,7,'2','null'),(3,8,'2','1/12'),(3,9,'2','null'),(3,10,'2','null'),(3,11,'10, 10','12/12, 01/03'),(3,12,'10','12/10, 10/10'),(3,13,'4, 5, 89','12/12'),(3,14,'4, 5, 6','10/11'),(3,15,'2','null'),(3,16,'2','null'),(3,17,'10','12/12/2016'),(3,18,'null','null'),(3,19,'2','null'),(3,20,'2','null'),(3,21,'2','null'),(3,22,'2','null'),(3,23,'2, 10, 12, 4','12/10, 01/02'),(3,24,'10','12/12, 09/12'),(4,1,'4, 5, 6','10/11'),(4,2,'2','null'),(4,3,'2','null'),(4,4,'2','null'),(4,5,'null','10/12/2016'),(4,6,'2','null'),(4,7,'2','null'),(4,8,'2','1/12'),(4,9,'2','null'),(4,10,'2','null'),(4,11,'10, 10','12/12, 01/03'),(4,12,'11','12/10, 10/10'),(4,13,'4, 5, 89','12/12'),(4,14,'4, 5, 6','10/11'),(4,15,'2','null'),(4,16,'2','null'),(4,17,'10','12/12/2016'),(4,18,'null','null'),(4,19,'2','null'),(4,20,'2','null'),(4,21,'2','null'),(4,22,'2','null'),(4,23,'2, 10, 12, 4','12/10, 01/02'),(4,24,'10','12/12, 09/12'),(5,1,'4, 5,6','10/11'),(5,2,'2','null'),(5,3,'2','null'),(5,4,'2','null'),(5,5,'null','10/12/2016'),(5,6,'2','null'),(5,7,'2','null'),(5,8,'2','1/12'),(5,9,'2','null'),(5,10,'2','null'),(5,11,'10, 10','12/12, 01/03'),(5,12,'10','12/10, 10/10'),(5,13,'4, 5, 89','12/12'),(5,14,'4, 5, 6','10/11'),(5,15,'2','null'),(5,16,'2','null'),(5,17,'10','12/12/2016'),(5,18,'null','null'),(5,19,'2','null'),(5,20,'2','null'),(5,21,'2','null'),(5,22,'2','null'),(5,23,'2, 10, 12, 4','12/10, 01/02'),(5,24,'10','12/12, 09/12'),(6,1,'4, 5, 6','10/11'),(6,2,'2','null'),(6,3,'2','null'),(6,4,'2','null'),(6,5,'null','10/12/2016'),(6,6,'2','null'),(6,7,'2','null'),(6,8,'2','1/12'),(6,9,'2','null'),(6,10,'2','null'),(6,11,'10, 10','12/12, 01/03'),(6,12,'11','12/10, 10/10'),(6,13,'4, 5, 89','12/12'),(6,14,'4, 5, 6','10/11'),(6,15,'2','null'),(6,16,'2','null'),(6,17,'10','12/12/2016'),(6,18,'null','null'),(6,19,'2','null'),(6,20,'2','null'),(6,21,'2','null'),(6,22,'2','null'),(6,23,'2, 10, 12, 4','12/10, 01/02'),(6,24,'10','12/12, 09/12'),(7,1,'4, 5, 6','10/11'),(7,2,'2','null'),(7,3,'2','null'),(7,4,'2','null'),(7,5,'null','10/12/2016'),(7,6,'2','null'),(7,7,'2','null'),(7,8,'2','1/12'),(7,9,'2','null'),(7,10,'2','null'),(7,11,'10, 10','12/12, 01/03'),(7,12,'10','12/10, 10/10'),(7,13,'4, 5, 89','12/12'),(7,14,'4, 5, 6','10/11'),(7,15,'2','null'),(7,16,'2','null'),(7,17,'10','12/12/2016'),(7,18,'null','null'),(7,19,'2','null'),(7,20,'2','null'),(7,21,'2','null'),(7,22,'2','null'),(7,23,'2, 10, 12, 4','12/10, 01/02'),(7,24,'10','12/12, 09/12'),(8,1,'4, 5, 6','10/11'),(8,2,'2','null'),(8,3,'2','null'),(8,4,'2','null'),(8,5,'null','10/12/2016'),(8,6,'2','null'),(8,7,'2','null'),(8,8,'2','1/12'),(8,9,'2','null'),(8,10,'2','null'),(8,11,'10, 10','12/12, 01/03'),(8,12,'11','12/10, 10/10'),(8,13,'4, 5, 89','12/12'),(8,14,'4, 5, 6','10/11'),(8,15,'2','null'),(8,16,'2','null'),(8,17,'10','12/12/2016'),(8,18,'null','null'),(8,19,'2','null'),(8,20,'2','null'),(8,21,'2','null'),(8,22,'2','null'),(8,23,'2, 10, 12, 4','12/10, 01/02'),(8,24,'10','12/12, 09/12'),(9,1,'4, 5, 6','10/11'),(9,2,'2','null'),(9,3,'2','null'),(9,4,'2','null'),(9,5,'null','10/12/2016'),(9,6,'2','null'),(9,7,'2','null'),(9,8,'2','1/12'),(9,9,'2','null'),(9,10,'2','null'),(9,11,'10, 10','12/12, 01/03'),(9,12,'11','12/10, 10/10'),(9,13,'4, 5, 89','12/12'),(9,14,'4, 5, 6','10/11'),(9,15,'2','null'),(9,16,'2','null'),(9,17,'10','12/12/2016'),(9,18,'null','null'),(9,19,'2','null'),(9,20,'2','null'),(9,21,'2','null'),(9,22,'2','null'),(9,23,'2, 10, 12, 4','12/10, 01/02'),(9,24,'10','12/12, 09/12');
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
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
INSERT INTO `student` VALUES (1,'hector',1,'student','pas'),(2,'maximilian',2,'student','pas'),(3,'vasile',3,'student','pas'),(4,'ion',4,'student','pas'),(5,'andrei',5,'student','pas'),(6,'maria',6,'student','pas'),(7,'pop',7,'student','pas'),(8,'ana',8,'student','pas'),(9,'sofia',9,'student','pas'),(10,'marinela',10,'student','pas'),(11,'florinel',11,'student','pas'),(12,'saveta',12,'student','pas'),(13,'ionel',1,'student','pas'),(14,'marian',2,'student','pas'),(15,'marcel',3,'student','pas'),(16,'miron',4,'student','pas'),(17,'marius',5,NULL,'pas'),(18,'andreea',6,NULL,'pas'),(19,'ionutz',7,NULL,'pas'),(20,'vasilica',8,NULL,'pas'),(21,'laurentiu',9,NULL,'pas'),(22,'ionica',10,NULL,'pas'),(23,'ema',11,NULL,'pas'),(24,'maya',12,NULL,'pas');
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
INSERT INTO `subject` VALUES (1,'Matematica'),(2,'Romana'),(3,'Fizica'),(4,'Biologie'),(5,'Chimie'),(6,'Istorie'),(7,'Geografie'),(8,'Educatie Fizica'),(9,'Psihologie');
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
INSERT INTO `teacher` VALUES (1,'Marius Cicortas',1,'teacher','pas'),(2,'Medan Eugenia',1,'teacher','pas'),(3,'Fildan Mihaela',2,'teacher','pas'),(4,'Popa Dorina',2,'teacher','pas'),(5,'Berian Sergiu',3,'teacher','pas'),(6,'Nagy Anna',4,'teacher','pas'),(7,'Marinela Pop',5,'teacher','pas'),(8,'Fiter Ciprian',8,'teacher','pas'),(9,'Ivan Daniel',9,'teacher','pas'),(10,'Istoc Florin',6,'teacher','pas'),(11,'Istoc Ramon',7,'teacher','pas'),(12,'Hector Daniel',4,'teacher','pas'),(13,'Ionutz Petrutz',3,'teacher','pas'),(14,'Diana Marcis',5,'teacher','pas'),(15,'Vasile Pop',7,'teacher','pas');
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-15  2:32:31
