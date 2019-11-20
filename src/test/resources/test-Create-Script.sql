create table animal
(
    animalId int auto_increment
        primary key,
    waterUsage int null,
    profit float null,
    purchasePrice float not null,
    name varchar(45) not null
);

create table plant
(
    plantID int auto_increment
        primary key,
    waterUsage int not null,
    growingTime int not null,
    profit float not null,
    purchasePrice float not null,
    name varchar(45) not null
);

create table user
(
    userID int auto_increment
        primary key,
    name varchar(45) not null,
    password char(70) not null,
    email varchar(45) not null,
    constraint email
        unique (email)
);

create table farm
(
    farmID int auto_increment
        primary key,
    ownerID int not null,
    constraint farm_ibfk_1
        foreign key (ownerID) references user (userID)
            on update cascade on delete cascade
);

create index ownerID
    on farm (ownerID);

create table inventory
(
    userID int not null,
    money int not null,
    primary key (userID, money),
    constraint inventory_ibfk_1
        foreign key (userID) references user (userID)
            on update cascade on delete cascade
);

create table waterManager
(
    waterManagerId int auto_increment
        primary key,
    waterYield int null,
    purchasePrice float not null,
    name varchar(45) not null
);

create table plot
(
    plotID int auto_increment
        primary key,
    x int not null,
    y int not null,
    price float not null,
    animalId int null,
    waterManagerId int null,
    plantID int null,
    purchased bit null,
    constraint plot_ibfk_1
        foreign key (animalId) references animal (animalId)
            on update cascade on delete cascade,
    constraint plot_ibfk_2
        foreign key (waterManagerId) references waterManager (waterManagerId)
            on update cascade on delete cascade,
    constraint plot_ibfk_3
        foreign key (plantID) references plant (plantID)
            on update cascade on delete cascade
);

create index animalId
    on plot (animalId);

create index plantID
    on plot (plantID);

create index waterManagerId
    on plot (waterManagerId);

create table plotInFarm
(
    farmID int not null,
    plotID int not null,
    primary key (farmID, plotID),
    constraint plotID
        unique (plotID),
    constraint plotinfarm_ibfk_1
        foreign key (farmID) references farm (farmID)
            on update cascade on delete cascade,
    constraint plotinfarm_ibfk_2
        foreign key (plotID) references plot (plotID)
            on update cascade on delete cascade
);

INSERT INTO user (name,password,email)
VALUES
('PatrickSt3r','DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937','Patrick@Ster.com'),
('Thomi','DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937','Geitenzijncool@hotmail.com'),
('Sapporo','DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937','oose.sapporo@gmail.com');