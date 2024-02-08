-- DATABASE CREATION
CREATE DATABASE IF NOT EXISTS `SiGAC`;

-- TABLES DROP
DROP TABLE IF EXISTS `SiGAC`.`CollaAdreca`;
DROP TABLE IF EXISTS `SiGAC`.`Ciutat`;
DROP TABLE IF EXISTS `SiGAC`.`Castell`;
DROP TABLE IF EXISTS `SiGAC`.`Rengla`;
DROP TABLE IF EXISTS `SiGAC`.`Estructura`;
DROP TABLE IF EXISTS `SiGAC`.`Pisos`;
DROP TABLE IF EXISTS `SiGAC`.`Reforcos`;
DROP TABLE IF EXISTS `SiGAC`.`CollaColor`;
DROP TABLE IF EXISTS `SiGAC`.`CollaNom`;
DROP TABLE IF EXISTS `SiGAC`.`CollaFundacio`;
DROP TABLE IF EXISTS `SiGAC`.`TeCarrec`;
DROP TABLE IF EXISTS `SiGAC`.`Carrec`;
DROP TABLE IF EXISTS `SiGAC`.`Usuari`;
DROP TABLE IF EXISTS `SiGAC`.`EsDeLaColla`;
DROP TABLE IF EXISTS `SiGAC`.`Colla`;
DROP TABLE IF EXISTS `SiGAC`.`Registre`;
DROP TABLE IF EXISTS `SiGAC`.`Casteller`;

-- TABLES CREATION
CREATE TABLE `SiGAC`.`Casteller` (
	`dni` VARCHAR(16) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	`cognom1` VARCHAR(256) NOT NULL,
	`cognom2` VARCHAR(256),
	`sexe` ENUM('home', 'done', 'no binari') NOT NULL,
	`dataNaixement` DATE NOT NULL,
	`dataDefuncio` DATE,
	PRIMARY KEY (`dni`)
);

CREATE TABLE `SiGAC`.`Usuari` (
	`casteller` VARCHAR(16) NOT NULL,
	`password` VARCHAR(256) NOT NULL,
	`isAdmin` BOOLEAN NOT NULL,
	PRIMARY KEY (`casteller`),
	FOREIGN KEY (`casteller`) REFERENCES `SiGAC`.`Casteller`(`dni`)
);

CREATE TABLE `SiGAC`.`Registre` (
	`numeroDeRegistre` VARCHAR(16) NOT NULL,
	`dataHora` DATETIME NOT NULL,
	`casteller` VARCHAR(16) NOT NULL,
	`alcadaEspatlla` FLOAT,
	`alcadaBrac` FLOAT,
	PRIMARY KEY (`numeroDeRegistre`),
	FOREIGN KEY (`casteller`) REFERENCES `SiGAC`.`Casteller`(`dni`)
);

CREATE TABLE `SiGAC`.`Colla` (
	`id` VARCHAR(16) NOT NULL,
	`universitaria` BOOLEAN NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `SiGAC`.`EsDeLaColla` (
	`casteller` VARCHAR(16) NOT NULL,
	`colla` VARCHAR(16) NOT NULL,
	`desDe` DATE NOT NULL,
	`finsA` DATE,
	`malnom` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`casteller`, `colla`, `desDe`),
	FOREIGN KEY (`casteller`) REFERENCES `SiGAC`.`Casteller`(`dni`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`)
);

CREATE TABLE `SiGAC`.`Carrec` (
	`masculi` VARCHAR(256) NOT NULL,
	`femeni` VARCHAR(256) NOT NULL,
	`neutre` VARCHAR(256) NOT NULL,
	`area` ENUM('tecnica', 'junta') NOT NULL,
	PRIMARY KEY (`masculi`)
);

CREATE TABLE `SiGAC`.`TeCarrec` (
	`casteller` VARCHAR(16) NOT NULL,
	`colla` VARCHAR(16) NOT NULL,
	`carrec` VARCHAR(256) NOT NULL,
	`desDe` DATE NOT NULL,
	`finsA` DATE,
	PRIMARY KEY (`casteller`, `colla`, `carrec`, `desDe`),
	FOREIGN KEY (`casteller`) REFERENCES `SiGAC`.`Casteller`(`dni`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`),
	FOREIGN KEY (`carrec`) REFERENCES `SiGAC`.`Carrec`(`masculi`)
);

CREATE TABLE `SiGAC`.`CollaFundacio` (
	`colla` VARCHAR(16) NOT NULL,
	`desDe` DATE NOT NULL,
	`finsA` DATE,
	PRIMARY KEY (`colla`, `desDe`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`)
);

CREATE TABLE `SiGAC`.`CollaNom` (
	`colla` VARCHAR(16) NOT NULL,
	`desDe` DATE NOT NULL,
	`finsA` DATE,
	`nom` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`colla`, `desDe`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`)
);

CREATE TABLE `SiGAC`.`CollaColor` (
	`colla` VARCHAR(16) NOT NULL,
	`desDe` DATE NOT NULL,
	`finsA` DATE,
	`color` VARCHAR(7) NOT NULL,
	PRIMARY KEY (`colla`, `desDe`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`)
);

CREATE TABLE `SiGAC`.`Reforcos` (
	`notacio` VARCHAR(16) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`notacio`)
);

CREATE TABLE `SiGAC`.`Pisos` (
	`notacio` VARCHAR(16) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`notacio`)
);

CREATE TABLE `SiGAC`.`Estructura` (
	`notacio` VARCHAR(16) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	`rengles` INT NOT NULL,
	`poms` INT NOT NULL,
	PRIMARY KEY (`notacio`)
);

CREATE TABLE `SiGAC`.`Rengla` (
	`nom` VARCHAR(256) NOT NULL,
	`estructura` VARCHAR(16) NOT NULL,
	PRIMARY KEY (`nom`, `estructura`),
	FOREIGN KEY (`estructura`) REFERENCES `SiGAC`.`Estructura`(`notacio`)
);

CREATE TABLE `SiGAC`.`Castell` (
	`id` VARCHAR(64) NOT NULL,
	`estructura` VARCHAR(16) NOT NULL,
	`pisos` VARCHAR(16) NOT NULL,
	`reforcos` VARCHAR(16) NOT NULL,
	`agulles` INT NOT NULL,
	`perSota` BOOLEAN NOT NULL,
	`caminant` BOOLEAN NOT NULL,
	`enxanetes` INT NOT NULL,
	`universitari` BOOLEAN NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`estructura`) REFERENCES `SiGAC`.`Estructura`(`notacio`),
	FOREIGN KEY (`pisos`) REFERENCES `SiGAC`.`Pisos`(`notacio`),
	FOREIGN KEY (`reforcos`) REFERENCES `SiGAC`.`Reforcos`(`notacio`)
);

CREATE TABLE `SiGAC`.`Ciutat` (
	`id` VARCHAR(16) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `SiGAC`.`CollaAdreca` (
	`colla` VARCHAR(16) NOT NULL,
	`ciutat` VARCHAR(16) NOT NULL,
	`adreca` VARCHAR(256) NOT NULL,
	`desDe` DATE NOT NULL,
	`finsA` DATE,
	PRIMARY KEY (`colla`, `ciutat`, `adreca`, `desDe`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`),
	FOREIGN KEY (`ciutat`) REFERENCES `SiGAC`.`Ciutat`(`id`)
);

-- TABLES POPULATION
INSERT INTO `SiGAC`.`Casteller` (`dni`, `nom`, `cognom1`, `cognom2`, `sexe`, `dataNaixement`, `dataDefuncio`) VALUES ('46482922Z', 'Oriol', 'Segura', 'Niño', 'home', '2001-06-13', NULL);

INSERT INTO `SiGAC`.`Usuari` (`casteller`, `password`, `isAdmin`) VALUES ('46482922Z', 'e72cb8d06a267c26ea3434c573ade27c426d39e25d3c61f0b7fe94ebcd78475c', 1);	-- putavic

INSERT INTO `SiGAC`.`Registre` (`numeroDeRegistre`, `dataHora`, `casteller`, `alcadaEspatlla`, `alcadaBrac`) VALUES ('R2024-001/0001', '2024-02-08 17:28:13', '46482922Z', 145.7, NULL);

INSERT INTO `SiGAC`.`Colla` (`id`, `universitaria`) VALUES ('SANTS', 0);
INSERT INTO `SiGAC`.`Colla` (`id`, `universitaria`) VALUES ('ARREPLEGATS', 1);
INSERT INTO `SiGAC`.`Colla` (`id`, `universitaria`) VALUES ('ENGRESCATS', 1);

INSERT INTO `SiGAC`.`EsDeLaColla` (`casteller`, `colla`, `desDe`, `finsA`, `malnom`) VALUES ('46482922Z', 'SANTS', '2018-11-27', NULL, 'Segura');
INSERT INTO `SiGAC`.`EsDeLaColla` (`casteller`, `colla`, `desDe`, `finsA`, `malnom`) VALUES ('46482922Z', 'ARREPLEGATS', '2019-09-17', '2019-09-19', 'Segura');
INSERT INTO `SiGAC`.`EsDeLaColla` (`casteller`, `colla`, `desDe`, `finsA`, `malnom`) VALUES ('46482922Z', 'ENGRESCATS', '2021-10-07', '2022-05-05', 'Segura');
INSERT INTO `SiGAC`.`EsDeLaColla` (`casteller`, `colla`, `desDe`, `finsA`, `malnom`) VALUES ('46482922Z', 'ARREPLEGATS', '2022-05-09', NULL, 'Segura');

INSERT INTO `SiGAC`.`Carrec` (`masculi`, `femeni`, `neutre`, `area`) VALUES ('sotscap de colla', 'sotscap de colla', 'sotscap de colla', 'tecnica');

INSERT INTO `SiGAC`.`TeCarrec` (`casteller`, `colla`, `carrec`, `desDe`, `finsA`) VALUES ('46482922Z', 'ARREPLEGATS', 'sotscap de colla', '2023-09-21', NULL);

INSERT INTO `SiGAC`.`CollaFundacio` (`colla`, `desDe`, `finsA`) VALUES ('SANTS', '1993-05-09', NULL);
INSERT INTO `SiGAC`.`CollaFundacio` (`colla`, `desDe`, `finsA`) VALUES ('ARREPLEGATS', '1995-04-04', NULL);
INSERT INTO `SiGAC`.`CollaFundacio` (`colla`, `desDe`, `finsA`) VALUES ('ENGRESCATS', '2013-01-01', NULL);

INSERT INTO `SiGAC`.`CollaNom` (`colla`, `desDe`, `finsA`, `nom`) VALUES ('SANTS', '1993-05-09', NULL, 'Castellers de Sants');
INSERT INTO `SiGAC`.`CollaNom` (`colla`, `desDe`, `finsA`, `nom`) VALUES ('ARREPLEGATS', '1995-04-04', NULL, 'Arreplegats de la Zona Universitària');
INSERT INTO `SiGAC`.`CollaNom` (`colla`, `desDe`, `finsA`, `nom`) VALUES ('ENGRESCATS', '2013-01-01', NULL, 'Engrescats de la URL');

INSERT INTO `SiGAC`.`CollaColor` (`colla`, `desDe`, `finsA`, `color`) VALUES ('SANTS', '1993-05-09', NULL, '#808080');
INSERT INTO `SiGAC`.`CollaColor` (`colla`, `desDe`, `finsA`, `color`) VALUES ('ARREPLEGATS', '1995-04-04', NULL, '#15A884');
INSERT INTO `SiGAC`.`CollaColor` (`colla`, `desDe`, `finsA`, `color`) VALUES ('ENGRESCATS', '2013-01-01', NULL, '#FFDD00');

INSERT INTO `SiGAC`.`Reforcos` (`notacio`, `nom`) VALUES ('', '');
INSERT INTO `SiGAC`.`Reforcos` (`notacio`, `nom`) VALUES ('f', 'amb folre');
INSERT INTO `SiGAC`.`Reforcos` (`notacio`, `nom`) VALUES ('sf', 'sense folre');
INSERT INTO `SiGAC`.`Reforcos` (`notacio`, `nom`) VALUES ('fm', 'amb folre i manilles');
INSERT INTO `SiGAC`.`Reforcos` (`notacio`, `nom`) VALUES ('sm', 'sense manilles');
INSERT INTO `SiGAC`.`Reforcos` (`notacio`, `nom`) VALUES ('fmp', 'amb folre, manilles i puntals');

INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('3', 'tres');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('4', 'quatre');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('5', 'cinc');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('6', 'sis');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('7', 'set');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('8', 'vuit');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('9', 'nou');
INSERT INTO `SiGAC`.`Pisos` (`notacio`, `nom`) VALUES ('10', 'deu');

INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('P', 'pilar', 1, 0);
INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('T', 'torre', 2, 1);
INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('3', 'tres', 3, 1);
INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('4', 'quatre', 4, 1);
INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('5', 'cinc', 5, 2);
INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('7', 'set', 7, 2);
INSERT INTO `SiGAC`.`Estructura` (`notacio`, `nom`, `rengles`, `poms`) VALUES ('9', 'nou', 9, 3);

INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pilar', 'P');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('fals rengle', 'P');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('carregar', 'T');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('descarregar', 'T');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom', 'T');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('rengla', '3');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('plena', '3');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('buida', '3');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom', '3');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('enxaneta', '4');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('dosos (enxaneta)', '4');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('acotxador', '4');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('dosos (acotxador)', '4');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom', '4');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('rengla', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('plena', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('buida', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('carregar', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('descarregar', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom del tres', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom de la torre', '5');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('rengla', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('súper plena', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('buida', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('plena', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('esquerra', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('darrera', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('dreta', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom del quatre', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom del tres', '7');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('rengla A', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('rengla B', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('rengla C', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('carregar A', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('descarregar A', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('carregar B', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('descarregar B', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('carregar C', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('descarregar C', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom A', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom B', '9');
INSERT INTO `SiGAC`.`Rengla` (`nom`, `estructura`) VALUES ('pom C', '9');

INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('Pd4', 'P', '4', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('7d7', '7', '7', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('4d6', '4', '6', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('uTd5', 'T', '5', '', 0, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u3d5', '3', '5', '', 0, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u3d5a', '3', '5', '', 1, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u3d6', '3', '6', '', 0, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('4d5a', '4', '6', '', 1, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('5d7', '5', '7', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('3d6a', '3', '6', '', 1, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('4d7a', '4', '7', '', 1, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('4d8', '4', '8', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('Td7', 'T', '7', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('3d7', '3', '7', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('4d6a', '4', '6', '', 1, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('3d6', '3', '6', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u3d7', '3', '7', '', 0, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u3d6a', '3', '6', '', 1, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u4d6a', '4', '6', '', 1, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('Pd3', 'P', '3', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('3d8', '3', '8', '', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u4d7', '4', '7', '', 0, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('u5d6', '5', '6', '', 0, 0, 0, 1, 1);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('4d9f', '4', '9', 'f', 0, 0, 0, 1, 0);
INSERT INTO `SiGAC`.`Castell` (`id`, `estructura`, `pisos`, `reforcos`, `agulles`, `perSota`, `caminant`, `enxanetes`, `universitari`) VALUES ('uTd7f', 'T', '7', 'f', 0, 0, 0, 1, 1);
-- TODO: finish

INSERT INTO `SiGAC`.`Ciutat` (`id`, `nom`) VALUES ('BARCELONA', 'Barcelona');

INSERT INTO `SiGAC`.`CollaAdreca` (`colla`, `ciutat`, `adreca`, `desDe`, `finsA`) VALUES ('ARREPLEGATS', 'BARCELONA', 'Avinguda Diagonal, 647', '1995-04-04', NULL);
