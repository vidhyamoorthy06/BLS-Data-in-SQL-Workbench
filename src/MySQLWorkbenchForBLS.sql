/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Archana
 * Created: Aug 6, 2018
 */
DROP DATABASE BLSData;

create database BLSData DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE  BLSData;

create table seriesData (
seriesID varchar(20),
Period varchar(20),
year int(10),
PeriodName varchar(15),
Value int(11),
Latest boolean,
Code varchar(15),
Text varchar(200)
);

CREATE USER 'BLSuser'@'localhost' IDENTIFIED BY 'Password123';
GRANT ALL PRIVILEGES ON javabase.* TO 'BLSuser'@'localhost' ;
FLUSH PRIVILEGES;


select count(*) from seriesData;
select * from seriesData;
select distinct * from seriesData;
TRUNCATE  TABLE seriesData;

DROP TABLE seriesData ;

