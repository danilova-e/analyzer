CREATE DATABASE analyzer DEFAULT CHARACTER SET utf8;

USE analyzer;

CREATE TABLE subject (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name TEXT
);

CREATE TABLE grade (
  id INT PRIMARY KEY AUTO_INCREMENT,
  value INT
);

CREATE TABLE subject_grade (
  subject_id INT,
  date TIMESTAMP,
  grade_id INT
);

INSERT INTO grade VALUES (1, 1);
INSERT INTO grade VALUES (2, 2);
INSERT INTO grade VALUES (3, 3);
INSERT INTO grade VALUES (4, 4);
INSERT INTO grade VALUES (5, 5);
INSERT INTO grade VALUES (6, 6);
INSERT INTO grade VALUES (7, 7);
INSERT INTO grade VALUES (8, 8);
INSERT INTO grade VALUES (9, 9);
INSERT INTO grade VALUES (10, 10);

INSERT INTO subject VALUES ("mama");
INSERT INTO subject VALUES (2, "papa");
INSERT INTO subject VALUES (3, "health");
INSERT INTO subject VALUES (4, "weather");
INSERT INTO subject VALUES (5, "brother");
INSERT INTO subject VALUES (6, "work");
INSERT INTO subject VALUES (7, "hobby");

INSERT INTO subject_grade VALUES (1, '2013-12-01 23:43:00', 5);
INSERT INTO subject_grade VALUES (2, '2013-12-02 20:07:00', 6);
INSERT INTO subject_grade VALUES (3, '2013-12-03 15:34:37', 7);
INSERT INTO subject_grade VALUES (4, '2013-12-04 09:34:00', 5);