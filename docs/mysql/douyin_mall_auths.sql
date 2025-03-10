-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: douyin_mall
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `auths`
--

DROP TABLE IF EXISTS `auths`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auths` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` int NOT NULL COMMENT '用户id',
  `token` varchar(100) NOT NULL COMMENT 'Token',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auths`
--

LOCK TABLES `auths` WRITE;
/*!40000 ALTER TABLE `auths` DISABLE KEYS */;
INSERT INTO `auths` VALUES (1,1,'c4ca4238a0b923820dcc509a6f75849b','2025-02-05 21:56:13','2025-02-05 23:56:13'),(2,1,'11307c1c63ba3f3126f93ecbfabfa27d','2025-02-05 22:01:49','2025-02-06 00:01:49'),(3,1,'0432f342-6c66-4e60-b6ae-0e41b4ce0d2a','2025-02-08 21:48:52','2025-02-08 23:48:52'),(4,1,'0432f342-6c66-4e60-b6ae-0e41b4ce0d2a','2025-02-08 21:52:34','2025-02-08 23:52:34'),(5,1,'0432f342-6c66-4e60-b6ae-0e41b4ce0d2a','2025-02-08 22:00:04','2025-02-09 00:00:04'),(6,1,'0432f342-6c66-4e60-b6ae-0e41b4ce0d2a','2025-02-08 22:16:05','2025-02-09 00:16:05'),(7,1,'b5dfef55-cb76-4441-a536-a7f6224b01ae','2025-02-09 18:11:23','2025-02-09 20:11:23'),(8,1,'b5dfef55-cb76-4441-a536-a7f6224b01ae','2025-02-09 18:17:40','2025-02-09 20:17:40'),(9,1,'b5dfef55-cb76-4441-a536-a7f6224b01ae','2025-02-09 18:26:37','2025-02-09 20:26:37'),(10,1,'b5dfef55-cb76-4441-a536-a7f6224b01ae','2025-02-21 17:22:57','2025-02-21 19:22:57');
/*!40000 ALTER TABLE `auths` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-02 16:59:56
