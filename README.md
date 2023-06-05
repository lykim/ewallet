# EWallet Simultion

This is a simulation of ewallet written in Java

## Compile and Run 
Open workspace in eclipse, add the project as maven project
from project core build with maven build syntax clean install
from project mysql-persistence build with maven build syntax clean install
from project presenter build wiht maven build syntax spring-boot:run


## In Memory Usage
in presenter project open application.properties and use persistence= IN_MEMORY instead of persistence= MYSQL_GATEWAY

## Persisten usage
in presenter project open application.properties and use persistence= MYSQL_GATEWAY
change config.properties in mysql-persistence according to your mysql settings
create schema ewallet and create table user and transfer with below syntax
CREATE TABLE `user` (
  `username` varchar(120) NOT NULL,
  `balance_amount` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `transfer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `source` varchar(150) NOT NULL,
  `destination` varchar(150) NOT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


