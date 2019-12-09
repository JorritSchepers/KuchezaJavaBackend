CREATE TABLE user
(
    userID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    password CHAR(70) NOT NULL,
    email VARCHAR(45) UNIQUE NOT NULL,
    admin bit NOT NULL
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
                      name VARCHAR(45) NOT NULL
);

CREATE TABLE waterManager(
                             waterManagerId INT PRIMARY KEY AUTO_INCREMENT,
                             waterYield INT NOT NULL,
                             purchasePrice INT NOT NULL,
                             name VARCHAR(45) NOT NULL
);


CREATE TABLE animal(
                       animalId int PRIMARY KEY AUTO_INCREMENT,
                       waterUsage int,
                       profit float,
                       purchasePrice float not null,
                       name varchar(45) not null
);

CREATE TABLE plot (
                      plotID int PRIMARY KEY AUTO_INCREMENT,
                      farmID int not null,
                      x int not null,
                      y int not null,
                      price float not null,
                      animalId int null,
                      waterManagerId int null,
                      plantID int null,
                      waterAvailable int default 0 not null,
                      purchased bit,
                      age int DEFAULT 0,
                      objectAge int,

                      FOREIGN KEY (farmID)
                          REFERENCES farm(farmID)
                          ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (animalId)
                          REFERENCES animal(animalId)
                          ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (waterManagerId)
                          REFERENCES waterManager(waterManagerId)
                          ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (plantID)
                          REFERENCES plant(plantID)
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

INSERT INTO plant (waterUsage,growingTime,profit,purchasePrice,name)
VALUES
(5,10,20,5,'Cabbage'),
(10,5,20,7.5,'Tomato'),
(2.5,50,40,10,'Banana');

INSERT INTO plot (x,y,price,purchased, farmID,age,waterAvailable,objectAge)
VALUES
(1,1,10,1,1,10,0,0),
(1,2,10,1,1,10,100,0),
(1,3,10,0,1,2000,0,0);

update plot set plantID = 1 where plotID = 2;
