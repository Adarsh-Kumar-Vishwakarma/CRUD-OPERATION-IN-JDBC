# CRUD-OPERATION-IN-JDBC.
I CREATED A CRUD OPERATION IN JAVA USING JDBC.
DATABASE QUERY IS HERE:
create database adarsh;
use adarsh;
create table Products (
    P_Id int auto_increment primary key,
    P_Name varchar(255) not null,
    Quantity  int not null,
    Price double not null,
    Category varchar(255) not null
);
