CREATE DATABASE IF NOT EXISTS palmoilfactory;

USE palmoilfactory;

CREATE TABLE user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50),
	password VARCHAR(10)
);

INSERT INTO user (name,password) VALUES
('admin','admin123'),
('dilshan','dilshan123');

CREATE TABLE supplier(
	supId VARCHAR(40),
	name VARCHAR(20),
	address TEXT,
	contact VARCHAR(20),
	CONSTRAINT PRIMARY KEY(supId)
 	);

CREATE TABLE ffbstock(
	stockId VARCHAR(40),
	ffbInput DOUBLE,
	date DATE,
	time TIME,
	supId VARCHAR(40),
	CONSTRAINT PRIMARY KEY(stockId),
	CONSTRAINT FOREIGN KEY(supId) REFERENCES supplier(supId)
	ON UPDATE CASCADE ON DELETE CASCADE
	);

INSERT INTO supplier VALUES
	('SUP0001','Kamal','Matugama','0772985802'),
	('SUP0002','Amal','Kalutara','0763415202'),
	('SUP0003','Nimal','Panadura','0786754321'),
	('SUP0004','Piyal','Horana','0712345178'),
	('SUP0005','Sunimal','Galle','0701254267');

CREATE TABLE steam(
	stockId VARCHAR(40),
	fruit DOUBLE,
	emptyBunch DOUBLE,
	date DATE,
	time TIME,
	CONSTRAINT FOREIGN KEY(stockId) REFERENCES ffbstock(stockId)
	ON UPDATE CASCADE ON DELETE CASCADE
	);

CREATE TABLE byproductfuel(
	stockId VARCHAR(40),
	bunchFiber DOUBLE,
	shell DOUBLE,
	pressFiber DOUBLE,
	date DATE,
	time TIME,
	CONSTRAINT FOREIGN KEY(stockId) REFERENCES ffbstock(stockId)
	ON UPDATE CASCADE ON DELETE CASCADE
	);

CREATE TABLE oilproduction(
	stockId VARCHAR(40),
	totalEBLiquid DOUBLE,
	totalPressLiquid DOUBLE,
	date DATE,
	time TIME,
	CONSTRAINT FOREIGN KEY(stockId) REFERENCES ffbstock(stockId)
	ON UPDATE CASCADE ON DELETE CASCADE
	);

CREATE TABLE totaloilqty (
	totalQty DOUBLE
	);

INSERT INTO totaloilqty (totalQty) VALUES
	(0);

CREATE TABLE schedule(
	schId VARCHAR(40),
	timeRange VARCHAR(40),
	CONSTRAINT PRIMARY KEY(schId)
	);

INSERT INTO schedule VALUES
	('SCH01','08.00a.m-17.00p.m'),
	('SCH02','18.00p.m-06.00a.m');

CREATE TABLE employee(
	empId VARCHAR(40),
	empName VARCHAR(20),
	empAddress TEXT,
	contact VARCHAR(20),
	salary DECIMAL(10,2),
	type ENUM('By Production','Oil Production','Other'),
	schId VARCHAR(40),
	CONSTRAINT PRIMARY KEY(empId),
	CONSTRAINT FOREIGN KEY(schId) REFERENCES schedule(schId)
	ON UPDATE CASCADE ON DELETE CASCADE
	);

INSERT INTO employee VALUES
	('EMP0001','D.A.D.Dekumpitiya','Matugama','0772985802',1000000,'By Production','SCH01'),
	('EMP0002','K.W.Nishantha','Kalutara','0763415202',60000,'By Production','SCH01'),
	('EMP0003','N.Perera','Panadura','0786754321',70000,'By Production','SCH02'),
	('EMP0004','Piyal Somarathna','Horana','0712345178',80000,'By Production','SCH01'),
	('EMP0005','Sunimal Perera','Galle','0701254267',90000,'Oil Production','SCH01'),
	('EMP0007','Kamal Gunaratna','Matugama','0772985802',95000,'Oil Production','SCH02'),
	('EMP0008','Maithripala Sirisena','Kalutara','0763415202',75000,'Oil Production','SCH02'),
	('EMP0009','Mahinda Rajapaksha','Panadura','0786754321',60000,'Other','SCH02'),
	('EMP0010','Piyal Perera','Horana','0712345178',55000,'Other','SCH02'),
	('EMP0011','Sunimal Withana','Galle','0701254267',88000,'Other','SCH01');

CREATE TABLE orders(
	orderId VARCHAR(40),
	date DATE,
	quantity DOUBLE,
	price DECIMAL(20,2),
	CONSTRAINT PRIMARY KEY(orderId)
	);

