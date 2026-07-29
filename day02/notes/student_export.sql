-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: javaweb_learning
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `age` tinyint DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES 
(1,'刘雨萱','女',19,'13789001234'),
(2,'陈一鸣','男',20,'13689002345'),
(3,'张诗涵','女',18,'13589003456'),
(4,'李昊然','男',21,'13889004567'),
(5,'王若溪','女',19,'13989005678'),
(6,'赵宇轩','男',20,'13789006789'),
(7,'周思琪','女',18,'13689007890'),
(8,'孙浩宇','男',22,'13589008901'),
(9,'唐语嫣','女',19,'13889009012'),
(10,'郑子轩','男',20,'13989000123'),
(11,'吴梓涵','女',21,'13789001111'),
(12,'钱子豪','男',19,'13689002222'),
(13,'林梦瑶','女',20,'13589003333'),
(14,'黄俊杰','男',18,'13889004444'),
(15,'许思婷','女',22,'13989005555'),
(16,'何天宇','男',19,'13789006666'),
(17,'宋雨桐','女',20,'13689007777'),
(18,'邓浩然','男',21,'13589008888'),
(19,'冯雅琴','女',18,'13889009999'),
(20,'曹文博','男',20,'13989000001'),
(21,'彭嘉怡','女',19,'13789001112'),
(22,'蒋明辉','男',22,'13689002223'),
(23,'余芷若','女',20,'13589003334'),
(24,'潘俊豪','男',18,'13889004445'),
(25,'苏晓彤','女',21,'13989005556'),
(26,'曾伟强','男',19,'13789006667'),
(27,'沈佳宜','女',20,'13689007778'),
(28,'姚志鹏','男',21,'13589008889'),
(29,'蔡欣怡','女',18,'13889009990'),
(30,'丁俊杰','男',20,'13989000002');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-28
