CREATE TABLE ROLES (
    ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(255));

CREATE TABLE USERS (
    ID INT PRIMARY KEY AUTO_INCREMENT, FIRST_NAME VARCHAR(50), LAST_NAME VARCHAR(50), USER_NAME VARCHAR(51),
    PASSWORD VARCHAR(255), ACTIVE SMALLINT,ROLE_ID INT, FOREIGN KEY(ROLE_ID) REFERENCES ROLES(ID));

