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
INSERT INTO `grades` VALUES (1,1,'1',NULL),(1,2,'1',NULL),(1,3,'1',NULL),(1,4,'1',NULL),(1,5,'1',NULL),(1,6,'1',NULL),(1,7,'1',NULL),(1,8,'1',NULL),(1,9,'1',NULL),(1,10,'1',NULL),(1,11,'1',NULL),(1,12,'1',NULL),(1,13,'1',NULL),(2,1,'1',NULL),(2,2,'1',NULL),(2,3,'1',NULL),(2,4,'1',NULL),(2,5,'1',NULL),(2,6,'1',NULL),(2,7,'1',NULL),(2,8,'1',NULL),(2,9,'1',NULL),(2,10,'1',NULL),(2,11,'1',NULL),(2,12,'11',NULL),(2,13,'1',NULL),(3,1,'1',NULL),(3,2,'1',NULL),(3,3,'1',NULL),(3,4,'1',NULL),(3,5,'1',NULL),(3,6,'1',NULL),(3,7,'1',NULL),(3,8,'1',NULL),(3,9,'1',NULL),(3,10,'1',NULL),(3,11,'1',NULL),(3,12,'1',NULL),(3,13,'1',NULL),(4,1,'1',NULL),(4,2,'1',NULL),(4,3,'1',NULL),(4,4,'1',NULL),(4,5,'1',NULL),(4,6,'1',NULL),(4,7,'1',NULL),(4,8,'1',NULL),(4,9,'1',NULL),(4,10,'1',NULL),(4,11,'1',NULL),(4,12,'11',NULL),(4,13,'1',NULL),(5,1,'1',NULL),(5,2,'1',NULL),(5,3,'1',NULL),(5,4,'1',NULL),(5,5,'1',NULL),(5,6,'1',NULL),(5,7,'1',NULL),(5,8,'1',NULL),(5,9,'1',NULL),(5,10,'1',NULL),(5,11,'1',NULL),(5,12,'1',NULL),(5,13,'1',NULL),(6,1,'1',NULL),(6,2,'1',NULL),(6,3,'1',NULL),(6,4,'1',NULL),(6,5,'1',NULL),(6,6,'1',NULL),(6,7,'1',NULL),(6,8,'1',NULL),(6,9,'1',NULL),(6,10,'1',NULL),(6,11,'1',NULL),(6,12,'11',NULL),(6,13,'1',NULL),(7,1,'1',NULL),(7,2,'1',NULL),(7,3,'1',NULL),(7,4,'1',NULL),(7,5,'1',NULL),(7,6,'1',NULL),(7,7,'1',NULL),(7,8,'1',NULL),(7,9,'1',NULL),(7,10,'1',NULL),(7,11,'1',NULL),(7,12,'1',NULL),(7,13,'1',NULL),(8,1,'1',NULL),(8,2,'1',NULL),(8,3,'1',NULL),(8,4,'1',NULL),(8,5,'1',NULL),(8,6,'1',NULL),(8,7,'1',NULL),(8,8,'1',NULL),(8,9,'1',NULL),(8,10,'1',NULL),(8,11,'1',NULL),(8,12,'11',NULL),(8,13,'1',NULL),(9,1,'1',NULL),(9,2,'1',NULL),(9,3,'1',NULL),(9,4,'1',NULL),(9,5,'1',NULL),(9,6,'1',NULL),(9,7,'1',NULL),(9,8,'1',NULL),(9,9,'1',NULL),(9,10,'1',NULL),(9,11,'1',NULL),(9,12,'11',NULL),(9,13,'1',NULL);
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
INSERT INTO `student` VALUES (1,'hector',1,'student','pas1'),(2,'maximilian',2,'student','pas'),(3,'vasile',3,'student','pas'),(4,'ion',4,'student','pas'),(5,'andrei',5,'student','pas'),(6,'maria',6,'student','pas'),(7,'pop',7,'student','pas'),(8,'ana',8,'student','pas'),(9,'cal',9,'student','pas'),(10,'marinela',10,'student','pas'),(11,'florinel',11,'student','pas'),(12,'saveta',12,'student','pas'),(13,'ionel',1,'student','pas');
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

-- Dump completed on 2016-02-10 14:15:59
