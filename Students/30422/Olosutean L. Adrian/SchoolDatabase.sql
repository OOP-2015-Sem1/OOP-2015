CREATE DATABASE  IF NOT EXISTS `School` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `School`;
-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: School
-- ------------------------------------------------------
-- Server version	5.5.46-0ubuntu0.14.04.2

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
-- Table structure for table `Absence`
--

DROP TABLE IF EXISTS `Absence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Absence` (
  `absence_id` int(11) NOT NULL AUTO_INCREMENT,
  `student` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `subject` varchar(45) NOT NULL,
  `motivated` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`absence_id`),
  UNIQUE KEY `absence_index` (`student`,`date`,`subject`),
  KEY `fk_Absence_Student_idx` (`student`),
  KEY `fk_Absence_Subject_idx` (`subject`),
  CONSTRAINT `fk_Absence_Student` FOREIGN KEY (`student`) REFERENCES `Student` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Absence_Subject` FOREIGN KEY (`subject`) REFERENCES `Subject` (`subjectName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Absence`
--

LOCK TABLES `Absence` WRITE;
/*!40000 ALTER TABLE `Absence` DISABLE KEYS */;
INSERT INTO `Absence` VALUES (1,'ioanag','2015-01-01','Biologie',''),(2,'andreip','2015-12-30','Geografie',''),(3,'andreea','2015-12-31','Biologie',''),(9,'andreip','2015-12-31','Geografie','');
/*!40000 ALTER TABLE `Absence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Class`
--

DROP TABLE IF EXISTS `Class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(45) NOT NULL,
  `year` year(4) NOT NULL,
  `specialization` varchar(45) NOT NULL,
  PRIMARY KEY (`class_id`),
  KEY `fk_Class_Specialization_idx` (`specialization`),
  CONSTRAINT `fk_Class_Specialization` FOREIGN KEY (`specialization`) REFERENCES `Specialization` (`specialization`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Class`
--

LOCK TABLES `Class` WRITE;
/*!40000 ALTER TABLE `Class` DISABLE KEYS */;
INSERT INTO `Class` VALUES (1,'9A',2014,'Stiinte ale naturii'),(2,'10B',2014,'Sociologie'),(3,'11C',2015,'Mate-Info intensiv engleza'),(4,'12D',2015,'Mate-Info intensiv info');
/*!40000 ALTER TABLE `Class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Course`
--

DROP TABLE IF EXISTS `Course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Course` (
  `teacher` varchar(45) NOT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`teacher`,`class_id`),
  KEY `fk_Course_Teacher_idx` (`teacher`),
  KEY `fk_Course_Class_idx` (`class_id`),
  CONSTRAINT `fk_Course_Class` FOREIGN KEY (`class_id`) REFERENCES `Class` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Course_Teacher` FOREIGN KEY (`teacher`) REFERENCES `Teacher` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Course`
--

LOCK TABLES `Course` WRITE;
/*!40000 ALTER TABLE `Course` DISABLE KEYS */;
INSERT INTO `Course` VALUES ('aionescu',1),('apop',1),('imuresan',1),('sol',1),('Cpopescu',2),('imuresan',2),('ioltean',2),('malexandrescu',2),('sol',2),('eandrei',3),('gchirila',3),('imuresan',3),('ioanc',3),('eandrei',4),('gchirila',4);
/*!40000 ALTER TABLE `Course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Mark`
--

DROP TABLE IF EXISTS `Mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mark` (
  `subject` varchar(45) NOT NULL,
  `grading` int(10) NOT NULL,
  `date` date NOT NULL,
  `student` varchar(45) NOT NULL,
  PRIMARY KEY (`subject`,`date`,`student`),
  KEY `fk_Mark_Subject_idx` (`subject`),
  KEY `fk_Mark_Student_idx` (`student`),
  CONSTRAINT `fk_Mark_Student` FOREIGN KEY (`student`) REFERENCES `Student` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Mark_Subject` FOREIGN KEY (`subject`) REFERENCES `Subject` (`subjectName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Mark`
--

LOCK TABLES `Mark` WRITE;
/*!40000 ALTER TABLE `Mark` DISABLE KEYS */;
INSERT INTO `Mark` VALUES ('Biologie',6,'2015-12-31','andreea'),('Biologie',1,'2015-12-31','ioanag'),('Fizica',8,'2015-12-31','alexo'),('Geografie',7,'2015-12-31','andreip'),('Matematica',8,'2015-12-31','danradu');
/*!40000 ALTER TABLE `Mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Specialization`
--

DROP TABLE IF EXISTS `Specialization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Specialization` (
  `specialization` varchar(45) NOT NULL,
  `description` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`specialization`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Specialization`
--

LOCK TABLES `Specialization` WRITE;
/*!40000 ALTER TABLE `Specialization` DISABLE KEYS */;
INSERT INTO `Specialization` VALUES ('Mate-Info intensiv engleza',NULL),('Mate-Info Intensiv info',NULL),('Sociologie',NULL),('Stiinte ale naturii',NULL);
/*!40000 ALTER TABLE `Specialization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Staff`
--

DROP TABLE IF EXISTS `Staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Staff` (
  `teacher_username` varchar(45) NOT NULL,
  `subject_name` varchar(45) NOT NULL,
  PRIMARY KEY (`teacher_username`,`subject_name`),
  KEY `fk_Staff_Teacher_idx` (`teacher_username`),
  KEY `fk_Staff_Subject_idx` (`subject_name`),
  CONSTRAINT `fk_Staff_Subject` FOREIGN KEY (`subject_name`) REFERENCES `Subject` (`subjectName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Staff_Teacher` FOREIGN KEY (`teacher_username`) REFERENCES `Teacher` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Staff`
--

LOCK TABLES `Staff` WRITE;
/*!40000 ALTER TABLE `Staff` DISABLE KEYS */;
INSERT INTO `Staff` VALUES ('aionescu','Biologie'),('apop','Fizica'),('Cpopescu','Geografie'),('eandrei','Informatica'),('gchirila','Matematica'),('imuresan','Engleza'),('ioanc','Fizica'),('ioanc','Matematica'),('ioltean','Romana'),('malexandrescu','Geografie'),('malexandrescu','Istorie'),('sol','Biologie'),('sol','Geografie');
/*!40000 ALTER TABLE `Staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Student`
--

DROP TABLE IF EXISTS `Student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Student` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `phoneNumber` varchar(10) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `fk_Student_Class_idx` (`class_id`),
  CONSTRAINT `fk_Student_Class` FOREIGN KEY (`class_id`) REFERENCES `Class` (`class_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
INSERT INTO `Student` VALUES ('alexo','','Alexandru','Oltean','','alexoltean@yahoo.com',3),('andreea','','Andreea','Pop','','anpop@yahoo.com',1),('andreip','','Andrei','Popescu','','apop@gmail.com',2),('bogdanc','','Bogdan','Cristescu','','bogdanc@yahoo.com',4),('danradu','','Dan','Radu','','danradu@gmail.com',3),('gigel','','','','','gigel',2),('ioanag','','Ioana','Georgescu','','ioana@yahoo.com',1),('ionel','','','','','ionel@gmail.com',3),('vladt','','Vlad','Toc','','vladt@gmail.com',4);
/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subject`
--

DROP TABLE IF EXISTS `Subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subject` (
  `subjectName` varchar(45) NOT NULL,
  `curriculum` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`subjectName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subject`
--

LOCK TABLES `Subject` WRITE;
/*!40000 ALTER TABLE `Subject` DISABLE KEYS */;
INSERT INTO `Subject` VALUES ('Biologie',NULL),('Chimie',NULL),('Engleza',''),('Fizica',''),('Geografie',''),('Informatica',NULL),('Istorie',''),('Matematica',''),('Romana','');
/*!40000 ALTER TABLE `Subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SubjectSpecialization`
--

DROP TABLE IF EXISTS `SubjectSpecialization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SubjectSpecialization` (
  `specialization` varchar(45) NOT NULL,
  `subject` varchar(45) NOT NULL,
  PRIMARY KEY (`specialization`,`subject`),
  KEY `fk_SubjectSpecialization_Subject_idx` (`subject`),
  KEY `fk_SubjectSpecialization_Specialization_idx` (`specialization`),
  CONSTRAINT `fk_SubjectSpecialization_Specialization` FOREIGN KEY (`specialization`) REFERENCES `Specialization` (`specialization`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_SubjectSpecialization_Subject` FOREIGN KEY (`subject`) REFERENCES `Subject` (`subjectName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SubjectSpecialization`
--

LOCK TABLES `SubjectSpecialization` WRITE;
/*!40000 ALTER TABLE `SubjectSpecialization` DISABLE KEYS */;
INSERT INTO `SubjectSpecialization` VALUES ('Stiinte ale naturii','Biologie'),('Stiinte ale naturii','Chimie'),('Mate-Info intensiv engleza','Engleza'),('Sociologie','Engleza'),('Mate-Info intensiv info','Fizica'),('Stiinte ale naturii','Fizica'),('Mate-Info intensiv engleza','Geografie'),('Sociologie','Geografie'),('Mate-Info intensiv engleza','Informatica'),('Mate-Info intensiv info','Informatica'),('Sociologie','Istorie'),('Mate-Info intensiv engleza','Matematica'),('Mate-Info intensiv info','Matematica'),('Sociologie','Romana');
/*!40000 ALTER TABLE `SubjectSpecialization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Teacher`
--

DROP TABLE IF EXISTS `Teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Teacher` (
  `username` varchar(15) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `phoneNumber` varchar(10) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Teacher`
--

LOCK TABLES `Teacher` WRITE;
/*!40000 ALTER TABLE `Teacher` DISABLE KEYS */;
INSERT INTO `Teacher` VALUES ('abc','s','sdf','sdvs','',''),('aIonescu',NULL,'Andreea','Ionescu','0756985411','aionescu@yahoo.com'),('apop',NULL,'Andrei','Pop','0796541223','andrei.pop@yahoo.com'),('Cpopescu',NULL,'Cristian','Popescu','0745622115','cpopescu@yahoo.com'),('eandrei',NULL,'Emil','Andrei','','eandrei@gmail.com'),('gchirila',NULL,'George','Chirila','0714525122','gchirila@gmail.com'),('imuresan',NULL,'Ioana','Muresan','0073546874','ioanamuresan@gmail.com'),('ioanc','','Ioan','Crisan','','ioanc@gmail.com'),('ioltean',NULL,'Irina','Oltean','','irinaoltean@yahoo.com'),('malexandrescu',NULL,'Maria','Alexandrescu','0762145211','maria.alexandrescu@gmail.com'),('sol','sss','','','','re');
/*!40000 ALTER TABLE `Teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `View_SubjectTeacher`
--

DROP TABLE IF EXISTS `View_SubjectTeacher`;
/*!50001 DROP VIEW IF EXISTS `View_SubjectTeacher`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `View_SubjectTeacher` (
  `First Name` tinyint NOT NULL,
  `Last Name` tinyint NOT NULL,
  `Subject` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `View_SubjectTeacher`
--

/*!50001 DROP TABLE IF EXISTS `View_SubjectTeacher`*/;
/*!50001 DROP VIEW IF EXISTS `View_SubjectTeacher`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `View_SubjectTeacher` AS select `t`.`firstName` AS `First Name`,`t`.`lastName` AS `Last Name`,`s`.`subject_name` AS `Subject` from (`Teacher` `t` join `Staff` `s` on((`s`.`teacher_username` = `t`.`username`))) */;
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

-- Dump completed on 2015-12-31 22:59:18
