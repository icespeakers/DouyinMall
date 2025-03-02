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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` int NOT NULL COMMENT '用户id',
  `user_currency` varchar(10) NOT NULL COMMENT '货币类型',
  `street_address` varchar(255) NOT NULL COMMENT '街道',
  `city` varchar(100) NOT NULL COMMENT '城市',
  `state` varchar(100) DEFAULT NULL COMMENT '州/省',
  `country` varchar(100) NOT NULL COMMENT '国家',
  `zip_code` int NOT NULL COMMENT '邮编',
  `total_cost` float NOT NULL,
  `status` int DEFAULT '0' COMMENT '订单状态，0为下单1支付2取消',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'CN','长安街','北京','北京','中国',10001,125,0,'2025-01-25 11:34:34','test@example.com'),(2,1,'CN','长安街','北京','北京','中国',10001,125,0,'2025-01-25 11:37:31','test@example.com'),(3,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-01-25 11:40:12','test@example.com'),(4,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-01-25 11:42:28','test@example.com'),(5,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-01-25 11:48:51','test@example.com'),(6,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-01-25 11:50:13','test@example.com'),(7,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-01-25 16:44:22','test@example.com'),(8,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-01-25 17:20:47','test@example.com'),(9,1,'CN','长安街','北京','北京','中国',10001,25,2,'2025-01-25 17:23:51','test@example.com'),(10,1,'CN','长安街','北京','北京','中国',10001,25,2,'2025-01-25 17:24:59','test@example.com'),(11,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-02-08 21:53:49','test@example.com'),(12,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-02-08 21:56:43','test@example.com'),(13,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-02-08 22:01:14','test@example.com'),(14,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-02-08 22:17:16','test@example.com'),(15,1,'CN','长安街','北京','北京','中国',10001,25,1,'2025-02-09 18:12:47','test@example.com'),(16,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-02-09 18:18:56','test@example.com'),(17,1,'CN','长安街','北京','北京','中国',10001,25,2,'2025-02-09 18:27:50','test@example.com'),(18,1,'CN','长安街','北京','北京','中国',10001,25,0,'2025-02-21 17:36:43','test@example.com');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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
