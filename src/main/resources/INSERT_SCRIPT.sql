USE Kucheza;

/*Insert plants*/
INSERT INTO plant (waterUsage,maximumWater,growingTime,profit,purchasePrice,name)
VALUES
(5,237,10,700,500,'Cabbage'),
(10,113,5,800,700,'Corn'),
(2.5,101,50,1500,1000,'Orange');

/*Insert waterManager*/
INSERT INTO waterManager (waterYield,purchasePrice,name)
VALUES
(1,5000,'Silo');

/*Insert waterSource*/
INSERT INTO waterSource (waterYield,maximumWater,purchasePrice,name)
VALUES
(20,4000,750,'Well');

/*Insert status*/
INSERT INTO status (status)
VALUES
("Normal"),
("Dehydrated"),
("Dead");

/*Insert user, wachtwoorden zijn 'wachtwoord'*/
INSERT INTO user (userID, name,password,email, admin)
VALUES
(1, 'Admin','da5698be17b9b46962335799779fbeca8ce5d491c0d26243bafef9ea1837a9d8','info@kucheza.nl', 1);

/*Insert plants*/
INSERT INTO Animal (waterUsage,maximumWater,productionTime,profit,purchasePrice,name)
VALUES
(10,300,10,700,500,'Cow'),
(20,200,600,300,700,'Chicken'),
(20,200,600,300,600,'Goat');

/*Insert actions*/
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