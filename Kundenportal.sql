-- CREATE DATABASE `kundenportal` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `kundenportal`;

DROP TABLE IF EXISTS Messwerte;
DROP TABLE IF EXISTS Zaehler ;
DROP TABLE IF EXISTS entnahmestelle;
DROP TABLE IF EXISTS Person;



CREATE TABLE `person` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMail` varchar(255) NOT NULL,
  `Passwort` char(128) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `Vorname` varchar(255) DEFAULT NULL,
  `Nachname` varchar(255) DEFAULT NULL,
  `Anrede` enum('Herr','Frau','Kunde') DEFAULT "Kunde",
  `Geburtstag` date DEFAULT NULL,
  `Straße` varchar(255) DEFAULT NULL,
  `HausNr` varchar(255) DEFAULT NULL,
  `PLZ` int(5) DEFAULT NULL,
  `Ort` varchar(255) DEFAULT NULL,
  `Land` varchar(255) DEFAULT NULL,
  `Adminrechte` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `EMail` (`EMail`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `entnahmestelle` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PersonId` int(11) NOT NULL,
  `Straße` varchar(255) DEFAULT NULL,
  `HausNr` varchar(10) DEFAULT NULL,
  `Plz` int(5) DEFAULT NULL,
  `Ort` varchar(255) DEFAULT NULL,
  `Land` varchar(255) DEFAULT NULL,
  `Hinweis` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `PersonID_idx` (`PersonId`),
  CONSTRAINT `PersonID` FOREIGN KEY (`PersonId`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `zaehler` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EntnahmestellenID` int(11) NOT NULL,
  `Energieart` varchar(255) NOT NULL,
  `ZaehlerNr` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `EntnahmestellenID` (`EntnahmestellenID`),
  CONSTRAINT `EntnahmestellenID` FOREIGN KEY (`EntnahmestellenID`) REFERENCES `entnahmestelle` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `messwerte` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ZaehlerID` int(11) NOT NULL,
  `Ablesedatum` date NOT NULL,
  `Messwert` Double NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ZaehlerID` (`ZaehlerID`),
  CONSTRAINT `ZaehlerID` FOREIGN KEY (`ZaehlerID`) REFERENCES `zaehler` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


Insert Into Person VALUES (NULL,"test@test.de","3aaa73fbc8ebdd728463b8480ad091508dc5af7a8f39ed73d6192316def8727164530ec357e69ae0631a11b98f128a1b909c674ced22535126a74bf8aa97dea4" ,"8wk8Sr5Pd98B6yLwyzSK9qEe0EAEQ4AjogY1YE1OPbs",
"Max" ,"Mustermann" ,"Herr" ,"1990-12-24" ,"Teststr." ,1 ,"12345" ,"Testort" , "DE" , "1");

Insert Into Entnahmestelle VALUES (NULL, 1, "Ortsstr." ,"1a" , 10115 ,"Ortsort" ,"DE" ,"steht leer.");
Insert Into Entnahmestelle VALUES (NULL, 1, "Zweitstr." ,"2a" , 10115 ,"Ortsort" ,"DE" ,"steht voll.");

INSERT INTO Zaehler VALUES (NULL, 1, "Gas", "12345");
INSERT INTO Zaehler VALUES (NULL, 1, "Strom", "54321");

INSERT INTO Messwerte VALUES(NULL, 1, "2017-05-01", 350);
INSERT INTO Messwerte VALUES(NULL, 1, "2017-05-31", 1000);
INSERT INTO Messwerte VALUES(NULL, 1, "2017-04-01", 100);

INSERT INTO Messwerte VALUES(NULL, 2, "2017-01-01", 3750);
INSERT INTO Messwerte VALUES(NULL, 2, "2017-02-01", 4750);
INSERT INTO Messwerte VALUES(NULL, 2, "2017-03-01", 5500);

INSERT INTO Zaehler VALUES (NULL, 2, "Gas", "11111");
INSERT INTO Zaehler VALUES (NULL, 2, "Strom", "22222");

INSERT INTO Messwerte VALUES(NULL, 3, "2017-05-01", 100100);
INSERT INTO Messwerte VALUES(NULL, 3, "2017-05-31", 100200);
INSERT INTO Messwerte VALUES(NULL, 3, "2017-04-01", 100300);

INSERT INTO Messwerte VALUES(NULL, 4, "2017-01-01", 123);
INSERT INTO Messwerte VALUES(NULL, 4, "2017-02-01", 124);
INSERT INTO Messwerte VALUES(NULL, 4, "2017-03-01", 125);

Insert Into Person VALUES (NULL,"test2@test.de","3aaa73fbc8ebdd728463b8480ad091508dc5af7a8f39ed73d6192316def8727164530ec357e69ae0631a11b98f128a1b909c674ced22535126a74bf8aa97dea4" ,"8wk8Sr5Pd98B6yLwyzSK9qEe0EAEQ4AjogY1YE1OPbs",
"Andi" ,"Mustermann" ,"Herr" ,"1990-12-24" ,"Teststr." ,"2a" ,12345 ,"Testort" , "DE" , "1");

Insert Into Entnahmestelle VALUES (NULL, 2, "Ortsstr." ,"1a" , 10115 ,"Ortsort" ,"DE" ,"Andi wohnt hier");

INSERT INTO Zaehler VALUES (NULL, 3, "Strom", "1EM1234");

INSERT INTO Messwerte VALUES(NULL, 1, "2017-01-01", 100);
INSERT INTO Messwerte VALUES(NULL, 1, "2017-12-31", 1000);