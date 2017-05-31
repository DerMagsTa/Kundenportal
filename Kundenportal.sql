
DROP TABLE IF EXISTS Messwerte;
DROP TABLE IF EXISTS Zaehler ;
DROP TABLE IF EXISTS Personen;



CREATE TABLE Personen (
  ID int(11) NOT NULL AUTO_INCREMENT,
  EMail varchar(255) NOT NULL,
  Passwort varchar(40) NOT NULL,
  Vorname varchar(255) DEFAULT NULL,
  Nachname varchar(255) DEFAULT NULL,  
  Anrede enum('Herr','Frau','Kunde') DEFAULT NULL,
  Geburtstag date DEFAULT NULL,
  Straße varchar(255) DEFAULT NULL,
  HausNr varchar(255) DEFAULT NULL,
  PLZ int(5) DEFAULT NULL,
  Ort varchar(255) DEFAULT NULL,
  Adminrechte tinyint(1) NOT NULL DEFAULT '0',
  Erstellt TIMESTAMP,
  PRIMARY KEY (ID),
  UNIQUE KEY EMail (EMail)
) DEFAULT CHARSET=utf8;



CREATE TABLE Zaehler (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Energieart varchar(255) NOT NULL,
  ZaehlerNr varchar(255) NOT NULL,
  KundenID int(11) NOT NULL,
  Erstellt TIMESTAMP,
  PRIMARY KEY (ID),
  FOREIGN KEY (KundenID) REFERENCES Personen (ID) On Delete cascade On Update Cascade
) DEFAULT CHARSET=utf8;



CREATE TABLE Messwerte (
  ID int(11) NOT NULL AUTO_INCREMENT,
  ZaehlerID  int(11) NOT NULL,
  Ablesedatum date NOT NULL,
  Messwert int(11) NOT NULL,
  Erstellt TIMESTAMP,
  PRIMARY KEY (ID),
  FOREIGN KEY (ZaehlerID) REFERENCES Zaehler (ID) On Delete cascade On Update Cascade
) DEFAULT CHARSET=utf8;



Insert Into Personen VALUES (NULL,"test@test.de","passwort" ,"Max" ,"Mustermann" ,"Herr" ,"1990-12-24" ,
"Teststr." ,1 ,"12345" ,"Testort" , "1" , NULL);

INSERT INTO Zaehler VALUES (NULL, "Gas", "12345", 1, NULL);
INSERT INTO Zaehler VALUES (NULL, "Strom", "54321", 1, NULL);

INSERT INTO Messwerte VALUES(NULL, 1, "2017-05-01", 350, NULL);
INSERT INTO Messwerte VALUES(NULL, 1, "2017-05-31", 1000, NULL);
INSERT INTO Messwerte VALUES(NULL, 1, "2017-04-01", 100, NULL);

INSERT INTO Messwerte VALUES(NULL, 2, "2017-01-01", 3750, NULL);
INSERT INTO Messwerte VALUES(NULL, 2, "2017-02-01", 4750, NULL);
INSERT INTO Messwerte VALUES(NULL, 2, "2017-03-01", 5500, NULL);