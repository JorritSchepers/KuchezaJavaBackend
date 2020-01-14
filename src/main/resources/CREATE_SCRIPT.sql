DROP DATABASE IF EXISTS Kucheza;
CREATE DATABASE Kucheza;
USE Kucheza;

CREATE TABLE user (
	userID 		INT 			NOT NULL	PRIMARY KEY AUTO_INCREMENT,
	name 		VARCHAR(45) 	NOT NULL,
	password 	CHAR(70)		NOT NULL,
	admin 		BIT 			NOT NULL 	default (0),
	email 		VARCHAR(45) 	NOT NULL	UNIQUE ,
    
	CONSTRAINT CHK_Email CHECK (email LIKE '%_@__%.__%') /*email moet voldoen aan de formats van email*/
);

CREATE TABLE inventory (
	userID 		INT 	NOT NULL 	PRIMARY KEY,
	water 		INT 	NOT NULL 	default 0,
	money 		INT 	NOT NULL 	default 0,
	
	CONSTRAINT CHK_moneyInInventory CHECK (money >= 0),
	FOREIGN KEY (userID) REFERENCES user(userID)
		ON UPDATE CASCADE 
        ON DELETE CASCADE
);

CREATE TABLE farm (
	farmID 		INT 	NOT NULL 	PRIMARY KEY 	AUTO_INCREMENT,
	ownerID 	INT 	NOT NULL	UNIQUE,

	FOREIGN KEY (ownerID) REFERENCES user(userID)
		ON UPDATE CASCADE 
        ON DELETE CASCADE
);

CREATE TABLE plant(
	plantID 		INT 		NOT NULL	PRIMARY KEY 	AUTO_INCREMENT,
	waterUsage 		INT 		NOT NULL,
	maximumWater 	INT 		NOT NULL,
	growingTime 	INT 		NOT NULL,
	profit 			INT 		NOT NULL,
	purchasePrice 	INT 		NOT NULL,
	name 			VARCHAR(45) NOT NULL,
    
    CONSTRAINT CHK_waterUsagePlant CHECK (waterUsage >= 1),
	CONSTRAINT CHK_growingTimePlant CHECK (growingTime >= 1),
	CONSTRAINT CHK_maximumWaterPlant CHECK (maximumWater > 0),
	CONSTRAINT CHK_profitPlant CHECK (profit >= 1) /*Deze waardes moeten minimaal 1 zijn*/
);

CREATE TABLE waterManager(
	waterManagerId	INT 		NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
	waterYield 		INT 		NOT NULL,
	purchasePrice 	INT 		NOT NULL,
	name 			VARCHAR(45) NOT NULL,
    
    CONSTRAINT CHK_waterYieldWaterManager CHECK (waterYield >= 1) /*Deze waarde moeten minimaal 1 zijn, anders levert hij niks op*/
);

CREATE TABLE waterSource(
	waterSourceId 	INT 		NOT NULL	PRIMARY KEY 	AUTO_INCREMENT,
	waterYield 		INT 		NOT NULL,
	maximumWater 	INT 		NOT NULL,
	purchasePrice 	INT 		NOT NULL,
	name 			VARCHAR(45)	NOT NULL,
    
    CONSTRAINT CHK_maximumWaterWaterSource CHECK (maximumWater > 0), /*Deze waarde moeten groter dan 0 zijn, anders bevat het niets*/
	CONSTRAINT CHK_waterYieldWaterSource CHECK (waterYield >= 1) /*Deze waarde moeten minimaal 1 zijn, anders levert hij niks op*/
);

CREATE TABLE animal(
	animalId 		INT			NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
	waterUsage 		INT			NOT NULL,
	maximumWater 	INT 		NOT NULL,
	profit 			INT			NOT NULL,
	purchasePrice 	INT 		NOT NULL,
	name 			VARCHAR(45) NOT NULL,
	productionTime 	INT 		NOT NULL,
    
    CONSTRAINT CHK_waterUsageAnimal CHECK (waterUsage >= 1),
	CONSTRAINT CHK_maximumWaterAnimal CHECK (maximumWater > 0),
	CONSTRAINT CHK_profitAnimal CHECK (profit >= 1) /*Deze waardes moeten minimaal 1 zijn*/
);

CREATE TABLE status(
	status VARCHAR(30)	NOT NULL	PRIMARY KEY
);

CREATE TABLE plot (
	plotID 			INT 		NOT NULL 	PRIMARY KEY		AUTO_INCREMENT,
	farmID 			INT 		NOT NULL,
	x 				INT 		NOT NULL,
	y 				INT 		NOT NULL,
	price 			INT 		NOT NULL,
	animalID 		INT			NULL,
	waterManagerID 	INT 		NULL,
	waterSourceID 	INT 		NULL,
	plantID 		INT 		NULL,
	objectAge 		INT 		NOT NULL 	default 0,
	waterAvailable 	INT 		NOT NULL	default 0,
	purchased 		BIT			NOT NULL,
	status 			VARCHAR(30) NOT NULL	default "Normal",

	FOREIGN KEY (farmID) REFERENCES farm(farmID)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (animalId) REFERENCES animal(animalId)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (waterManagerId) REFERENCES waterManager(waterManagerId)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (waterSourceId) REFERENCES waterSource(waterSourceId)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (plantID) REFERENCES plant(plantID)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (status) REFERENCES status(status)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	CONSTRAINT CHK_waterAvailablePlot CHECK (waterAvailable >= 0)
);

CREATE TABLE action (
    actionID 	INT 		NOT NULL	PRIMARY KEY		AUTO_INCREMENT,
    actionText 	VARCHAR(45) NOT NULL
);

CREATE TABLE actionPerPlayer (
    userID 			INT			NOT NULL,
    actionID 		INT			NOT NULL,
    dateOfAction 	DATETIME 	NOT NULL	DEFAULT CURRENT_TIMESTAMP,
    affectedItem 	VARCHAR(45) NULL,
    currentWater 	INT 		NOT NULL,
    currentMoney 	INT 		NOT NULL,
	
    PRIMARY KEY (userID, actionID,dateOfAction),
	FOREIGN KEY (userID) REFERENCES user(userID)
		ON UPDATE CASCADE 
        ON DELETE CASCADE,
	FOREIGN KEY (actionID) REFERENCES action(actionID)
		ON UPDATE CASCADE 
        ON DELETE CASCADE
)