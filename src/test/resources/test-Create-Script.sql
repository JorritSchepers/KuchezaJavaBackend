CREATE TABLE user
(
    userID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    password CHAR(70) NOT NULL,
    email VARCHAR(45) UNIQUE NOT NULL,
    admin bit NOT NULL default 0
);

CREATE TABLE inventory (
                           userID INT,
                           water INT,
                           money INT NOT NULL,
                           PRIMARY KEY (userID),

                           FOREIGN KEY (userID)
                               REFERENCES user(userID)
                               ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE farm (
                      farmID INT PRIMARY KEY AUTO_INCREMENT,
                      ownerID INT UNIQUE NOT NULL,

                      FOREIGN KEY (ownerID)
                          REFERENCES user(userID)
                          ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE plant(
                      plantID INT PRIMARY KEY AUTO_INCREMENT,
                      waterUsage INT NOT NULL,
                      growingTime INT NOT NULL,
                      profit INT NOT NULL,
                      purchasePrice INT NOT NULL,
                      name VARCHAR(45) NOT NULL,
                      maximumWater int NOT NULL
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
                       animalId INT PRIMARY KEY AUTO_INCREMENT,
                       waterUsage INT NOT NULL,
                       productionTime INT NOT NULL,
                       maximumWater INT,
                       profit INT NOT NULL,
                       purchasePrice INT NOT NULL,
                       name VARCHAR(45) NOT NULL
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
                      status 			VARCHAR(30) NOT NULL	default 'Normal',

                      FOREIGN KEY (farmID)
                          REFERENCES farm(farmID)
                          ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (animalId)
                          REFERENCES animal(animalId)
                          ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (waterManagerId)
                          REFERENCES waterManager(waterManagerId)
                          ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (status) REFERENCES status(status)
                          ON UPDATE CASCADE
                          ON DELETE CASCADE,
                      FOREIGN KEY (plantID)
                          REFERENCES plant(plantID)
                          ON UPDATE CASCADE ON DELETE CASCADE
);

create table action (
                        actionID int PRIMARY KEY AUTO_INCREMENT,
                        actionText varchar(45) not null
);

create table actionPerPlayer (
                                 userID int,
                                 actionID int,
                                 dateOfAction DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 affectedItem varchar(45) null,
                                 currentWater int not null,
                                 currentMoney int not null,

                                 PRIMARY KEY (userID, actionID,dateOfAction),

                                 FOREIGN KEY (userID)
                                     REFERENCES user(userID)
                                     ON UPDATE CASCADE ON DELETE CASCADE,
                                 FOREIGN KEY (actionID)
                                     REFERENCES action(actionID)
                                     ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO user (name,password,email,admin)
VALUES
('PatrickSt3r','DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937','Patrick@Ster.com', 0),
('Thomi','DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937','Geitenzijncool@hotmail.com', 0),
('TestUser','wachtwoord','oose.sapporo@gmail.com', 0),
('admin', 'admin', 'admin@admin.com', 1);

INSERT INTO farm (ownerID)
VALUES
(1);

Insert into inventory Values
(1,1000,2000),
(2,1000,2000),
(3,1000,2000);

INSERT INTO plant (waterUsage,growingTime,profit,purchasePrice,name,maximumWater)
VALUES
(5,10,20,5,'Cabbage', 100),
(10,5,20,7.5,'Tomato', 200),
(2.5,50,40,10,'Banana', 300);

INSERT INTO status (status)
VALUES
('Normal'),
('Dehydrated'),
('Dead');

INSERT INTO plot (x,y,price,purchased, farmID,objectAge,waterAvailable,status, waterSourceID)
VALUES
(1,1,10,1,1,10,0,'Normal', 0),
(1,2,10,1,1,10,100,'Normal', 0),
(1,3,10,0,1,2000,0,'Normal', 1);

INSERT INTO animal (waterUsage,productionTime,maximumWater,profit,purchasePrice,name)
VALUES
(10,10,300,20,5,'Cow'),
(10,10,300,20,5,'Chicken');

update plot set plantID = 1 where plotID = 2;
update plot set animalID = 2 where plotID = 3;

INSERT INTO action (actionText)
VALUES
('Planted a seed'),
('Harvested a plant'),
('Gave a plant water'),
('Bought a plot'),
('Lost a plant'),
('Bought an animal'),
('Lost an animal'),
('Sold an item from an animal');

INSERT INTO waterManager (waterYield,purchasePrice,name)
VALUES
(1,5000,'Silo'),
(25,1500,'Sprinkler');

/*Insert waterSource*/
INSERT INTO waterSource (waterYield,maximumWater,purchasePrice,name)
VALUES
(20,4000,750,'Well'),
(2,50000,500,'canal');

INSERT INTO actionPerPlayer (userID,actionID,affectedItem,currentWater,currentMoney)
VALUES
(1,1,'Corn',100,100),
(1,2,'Corn',100,100),
(1,3,'Corn',100,100);