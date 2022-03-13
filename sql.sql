CREATE TABLE admin (
     username VARCHAR(30),
     password NUMBER(10)
);


INSERT INTO admin (username,password) VALUES ('admin',1234);

SELECT * FROM admin;


DROP TABLE admin;





CREATE TABLE student (
     id NUMBER GENERATED BY DEFAULT AS IDENTITY,
     firstname VARCHAR(30),
     lastname VARCHAR(30),
     phone NUMBER(30),
     email VARCHAR(45),
     dateofbirth DATE,
     klasse VARCHAR(45),
     PRIMARY KEY (id)
    );


INSERT INTO student (id,firstname,lastname,phone,email,dateofbirth,klasse) VALUES (1,'AHMET','KATIP',12347854,'AHMET@GMAIL.COM','17-12-2022','A');


SELECT * FROM student;


DROP TABLE student;






CREATE TABLE teacher (
     id NUMBER GENERATED BY DEFAULT AS IDENTITY,
     firstname VARCHAR(30),
     lastname VARCHAR(30),
     phone NUMBER(30),
     email VARCHAR(45),
     branch VARCHAR(50),
     salary NUMBER(10),
     PRIMARY KEY (id)
);

INSERT INTO teacher (id,firstname,lastname,phone,email,branch,salary) VALUES (1,'ALEX','MULLER',12347854,'AHMET@GMAIL.COM','MATE',5000);


SELECT * FROM teacher;


DROP TABLE teacher;