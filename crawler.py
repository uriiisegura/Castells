import mysql.connector
from json import loads


def fetch_table_data(table_name):
    with open('config.json', 'r') as f:
        config = loads(f.read())
        cnx = mysql.connector.connect(
            host=config["url"].split('//')[1].split(':')[0],
            database=config["url"].split('/')[-1],
            user=config["user"],
            password=config["password"]
        )
    cursor = cnx.cursor()
    cursor.execute(f'SELECT * FROM {table_name}')
    header = [row[0] for row in cursor.description]
    rows = cursor.fetchall()
    cnx.close()
    return header, rows


def export(table_name):
    header, rows = fetch_table_data(table_name)
    with open('dump.sql', 'a') as f:
        f.write('\n')
        for row in rows:
            values = "\", \"".join(str(r) for r in row)
            f.write(f'INSERT INTO `SiGAC`.`{table_name}` (`{"`, `".join(header)}`) VALUES ("{values}");\n'.replace('"None"', 'NULL'))


if __name__ == '__main__':
    with open('dump.sql', 'w') as f:
        f.write("""-- DATABASE CREATION
CREATE DATABASE IF NOT EXISTS `SiGAC`;

-- TABLES DROP
DROP TABLE IF EXISTS `SiGAC`.`RenglaLineUpCastellers`;
DROP TABLE IF EXISTS `SiGAC`.`RenglaLineUp`;
DROP TABLE IF EXISTS `SiGAC`.`PomLineUp`;
DROP TABLE IF EXISTS `SiGAC`.`CastellDiada`;
DROP TABLE IF EXISTS `SiGAC`.`Diada`;
DROP TABLE IF EXISTS `SiGAC`.`Location`;
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
	`sexe` ENUM("home", "dona", "no binari") NOT NULL,
	`dataNaixement` DATE NOT NULL,
	`dataDefuncio` DATE,
	PRIMARY KEY (`dni`)
);

CREATE TABLE `SiGAC`.`Usuari` (
	`casteller` VARCHAR(16) NOT NULL,
	`password` VARCHAR(256) NOT NULL,
	`rol` VARCHAR(32) NOT NULL,
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
	`area` ENUM("tècnica", "junta") NOT NULL,
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

CREATE TABLE `SiGAC`.`Location` (
	`id` VARCHAR(16) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	`ciutat` VARCHAR(16) NOT NULL,
	`type` VARCHAR(32) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`ciutat`) REFERENCES `SiGAC`.`Ciutat`(`id`)
);

CREATE TABLE `SiGAC`.`Diada` (
	`id` VARCHAR(64) NOT NULL,
	`nom` VARCHAR(256) NOT NULL,
	`inici` DATETIME NOT NULL,
	`fi` DATETIME,
	`location` VARCHAR(16) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`location`) REFERENCES `SiGAC`.`Location`(`id`)
);

CREATE TABLE `SiGAC`.`CastellDiada` (
	`id` BIGINT NOT NULL,
	`diada` VARCHAR(64) NOT NULL,
	`castell` VARCHAR(64) NOT NULL,
	`resultat` ENUM("INTENT", "INTENT_DESMUNTAT", "CARREGAT", "DESCARREGAT") NOT NULL,
	`colla` VARCHAR(16) NOT NULL,
	`ordre` INT NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`diada`) REFERENCES `SiGAC`.`Diada`(`id`),
	FOREIGN KEY (`castell`) REFERENCES `SiGAC`.`Castell`(`id`),
	FOREIGN KEY (`colla`) REFERENCES `SiGAC`.`Colla`(`id`)
);

CREATE TABLE `SiGAC`.`PomLineUp` (
	`id` BIGINT NOT NULL,
	`castell` BIGINT NOT NULL,
	`dosos1` VARCHAR(16) NOT NULL,
	`dosos2` VARCHAR(16) NOT NULL,
	`acotxador` VARCHAR(16) NOT NULL,
	`enxaneta` VARCHAR(16),
	PRIMARY KEY (`id`),
	FOREIGN KEY (`castell`) REFERENCES `SiGAC`.`CastellDiada`(`id`),
	FOREIGN KEY (`dosos1`) REFERENCES `SiGAC`.`Casteller`(`dni`),
	FOREIGN KEY (`dosos2`) REFERENCES `SiGAC`.`Casteller`(`dni`),
	FOREIGN KEY (`acotxador`) REFERENCES `SiGAC`.`Casteller`(`dni`),
	FOREIGN KEY (`enxaneta`) REFERENCES `SiGAC`.`Casteller`(`dni`)
);

CREATE TABLE `SiGAC`.`RenglaLineUp` (
	`id` BIGINT NOT NULL,
	`castell` BIGINT NOT NULL,
	`renglaNom` VARCHAR(256) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`castell`) REFERENCES `SiGAC`.`CastellDiada`(`id`),
	FOREIGN KEY (`renglaNom`) REFERENCES `SiGAC`.`Rengla`(`nom`)
);

CREATE TABLE `SiGAC`.`RenglaLineUpCastellers` (
	`renglaLineUp` BIGINT NOT NULL,
	`casteller` VARCHAR(16) NOT NULL,
	`pis` INT NOT NULL,
	PRIMARY KEY (`renglaLineUp`, `pis`),
	FOREIGN KEY (`renglaLineUp`) REFERENCES `SiGAC`.`RenglaLineUp`(`id`),
	FOREIGN KEY (`casteller`) REFERENCES `SiGAC`.`Casteller`(`dni`)
);

-- TABLES POPULATION""")
    export('Casteller')
    export('Usuari')
    export('Registre')
    export('Colla')
    export('EsDeLaColla')
    export('Carrec')
    export('TeCarrec')
    export('CollaFundacio')
    export('CollaNom')
    export('CollaColor')
    export('Reforcos')
    export('Pisos')
    export('Estructura')
    export('Rengla')
    export('Castell')
    export('Ciutat')
    export('CollaAdreca')
    export('Location')
    export('Diada')
    export('CastellDiada')
    export('PomLineUp')
    export('RenglaLineUp')
    export('RenglaLineUpCastellers')
