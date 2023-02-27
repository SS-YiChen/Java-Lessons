create table `owners` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(50),
	`favoriteColor` VARCHAR(50),
	`favoriteFood` VARCHAR(50),
	`age` INT
);

create table `vehicles` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`make` VARCHAR(50),
	`model` VARCHAR(50),
	"year" VARCHAR(50),
	`color` VARCHAR(50),
	`price` DECIMAL(9,2),
	`mileage` INT,
	`electric` VARCHAR(50),
	`ownerId` INT
);

create table `dinosaurs` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(50),
	`color` VARCHAR(50)
);