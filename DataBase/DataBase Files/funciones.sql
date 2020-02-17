USE `KRONOS`;
DROP procedure IF EXISTS insertAccord;
DELIMITER $$
USE `KRONOS`$$
CREATE PROCEDURE insertAccord (IN accNumber VARCHAR(45), IN incorDate DATE, IN incorTime TIME, 
IN deadLine DATE, IN sessionDate DATE, IN type_id
CHAR(1), IN observations longtext, IN publics TINYINT(4),
IN notified TINYINT(4), IN states INT, IN user_id VARCHAR(45))
BEGIN
INSERT INTO T_ACCORD (ACCNUMBER, INCORDATE,INCORTIME, 
DEADLINE, SESSIONDATE, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE, TYPE_ID, USER_ID) 
VALUES (accNumber, incorDate, incorTime, deadLine, sessionDate, observations, publics, notified, states, type_id, user_id);
commit; 
END$$
DELIMITER ;


USE `KRONOS`;
DROP procedure IF EXISTS insertAccPdf;
DELIMiTER $$
USE `KRONOS`$$
create procedure insertAccPdf(
in accord varchar(45), in url varchar(100),in final tinyint)
begin
insert into T_ACCPDF (ACCORD, URL, FINALRESPONSE) values (accord, url, final);
commit;  
end$$ 
DELIMITER ;


USE `KRONOS`;
DROP procedure IF EXISTS insertAccNotification;
DELIMiTER $$
USE `KRONOS`$$
create procedure insertAccNotification(
in accord varchar(45))
begin
insert into T_NOTIFICATION (ACCORD) values (accord);
commit;  
end$$ 
DELIMITER ;


USE `KRONOS`;
DROP procedure IF EXISTS insertTempUser;
DELIMITER $$
USE `KRONOS`$$
create procedure insertTempUser(
in namee varchar(45), in email varchar(45))
begin
insert into T_TEMPUSER (NAME, EMAIL) values (namee, email);
commit; 
end$$
DELIMITER ;

USE `KRONOS`;
DROP procedure IF EXISTS insertUserAcc;
DELIMITER $$
USE `KRONOS`$$
create procedure insertUserAcc(
in tempuser varchar(45), in accord varchar(45))
begin
insert into T_USERACC (TEMPUSER, ACCORD) values (tempuser, accord);
commit; 
end$$
DELIMITER ;

USE `KRONOS`;
DROP procedure IF EXISTS searchAccordType;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAccordType(in type_id char(1))
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID,T_TYPE.DESCRIPTION AS TYPE_DESC, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE,T_STATE.DESCRIPTION AS STATE_DESC,  T_ACCPDF.URL  from T_ACCORD, T_ACCPDF,T_STATE, T_TYPE where T_ACCORD.ACCNUMBER= T_ACCPDF.ACCORD AND T_ACCORD.STATE=T_STATE.ID AND T_ACCORD.TYPE_ID=T_TYPE.ID AND T_ACCORD.TYPE_ID = type_id;
end$$
DELIMITER ;

USE `KRONOS`;
DROP procedure IF EXISTS searchAccordNumber;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAccordNumber(in accNumber varchar(45)
)
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID,T_TYPE.DESCRIPTION AS TYPE_DESC, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE,T_STATE.DESCRIPTION AS STATE_DESC,  T_ACCPDF.URL  from T_ACCORD, T_ACCPDF,T_STATE, T_TYPE where T_ACCORD.ACCNUMBER= T_ACCPDF.ACCORD AND T_ACCORD.STATE=T_STATE.ID AND T_ACCORD.TYPE_ID=T_TYPE.ID AND T_ACCORD.ACCNUMBER LIKE CONCAT('%',accNumber,'%');
end$$
DELIMITER ; 




USE `KRONOS`;
DROP procedure IF EXISTS getAccord;
DELIMITER $$
USE `KRONOS`$$
create procedure getAccord(in accNumber varchar(45)
)
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID,T_TYPE.DESCRIPTION AS TYPE_DESC, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE,T_STATE.DESCRIPTION AS STATE_DESC,  T_ACCPDF.URL  from T_ACCORD, T_ACCPDF,T_STATE, T_TYPE where T_ACCORD.ACCNUMBER= T_ACCPDF.ACCORD AND T_ACCORD.STATE=T_STATE.ID AND T_ACCORD.TYPE_ID=T_TYPE.ID AND T_ACCORD.ACCNUMBER=accNumber;
end$$
DELIMITER ; 









USE `KRONOS`;
DROP procedure IF EXISTS searchAccordIncorDate;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAccordIncorDate(in incorDate  date
)
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID,T_TYPE.DESCRIPTION AS TYPE_DESC, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE,T_STATE.DESCRIPTION AS STATE_DESC,  T_ACCPDF.URL  from T_ACCORD, T_ACCPDF,T_STATE, T_TYPE where T_ACCORD.ACCNUMBER= T_ACCPDF.ACCORD AND T_ACCORD.STATE=T_STATE.ID AND T_ACCORD.TYPE_ID=T_TYPE.ID AND T_ACCORD.INCORDATE = incorDate;
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS searchAccordsessionDate;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAccordsessionDate(in sessionDate date
)
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID,T_TYPE.DESCRIPTION AS TYPE_DESC, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE,T_STATE.DESCRIPTION AS STATE_DESC,  T_ACCPDF.URL  from T_ACCORD, T_ACCPDF,T_STATE, T_TYPE where T_ACCORD.ACCNUMBER= T_ACCPDF.ACCORD AND T_ACCORD.STATE=T_STATE.ID AND T_ACCORD.TYPE_ID=T_TYPE.ID AND T_ACCORD.SESSIONDATE = sessionDate;
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS deleteAccord;
DELIMITER $$
USE `KRONOS`$$
create procedure deleteAccord(in accord varchar(45), in _user varchar(45) 
)
begin
delete from T_USERACC where T_USERACC.ACCORD = accord;
commit;
delete from T_ACCPDF where T_ACCPDF.ACCORD = accord;
commit;
INSERT INTO T_DELETEDACCORDS values (accord,_user,CURRENT_TIMESTAMP());
commit;
delete from T_ACCORD where T_ACCORD.ACCNUMBER = accord;
commit;
end$$
DELIMITER ; 



USE `KRONOS`;
DROP procedure IF EXISTS searchAllAccords;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAllAccords()
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID,T_TYPE.DESCRIPTION AS TYPE_DESC, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE,T_STATE.DESCRIPTION AS STATE_DESC,  T_ACCPDF.URL  from T_ACCORD, T_ACCPDF,T_STATE, T_TYPE where T_ACCORD.ACCNUMBER= T_ACCPDF.ACCORD AND T_ACCORD.STATE=T_STATE.ID AND T_ACCORD.TYPE_ID=T_TYPE.ID;
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS searchTempUser;
DELIMITER $$
USE `KRONOS`$$
create procedure searchTempUser( in email varchar(45))
begin
select NAME, EMAIL  from T_TEMPUSER where EMAIL= email; 
end$$
DELIMITER ; 



USE `KRONOS`;
DROP procedure IF EXISTS updateAccord;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccord( in _accNumber varchar(45), in _deadline date, in _sessionDate date, in _observations longtext, in _public tinyint, in _notified tinyint, in _state int(11), in _type char(1), in _user varchar(45)) 
begin
UPDATE T_ACCORD SET DEADLINE=_deadline, SESSIONDATE=_sessionDate, OBSERVATIONS=_observations,PUBLIC=_public , NOTIFIED=_notified, STATE=_state, 
TYPE_ID=_type, USER_ID=_user WHERE ACCNUMBER=_accNumber;
end$$
DELIMITER ; 


USE `KRONOS`;
DROP procedure IF EXISTS updateAccordSessionDate;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordSessionDate( in accord varchar(45), in newdate date)
begin
update T_ACCORD set SESSIONDATE = newdate where ACCNUMBER = accord;   
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS updateAccordDeadline;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordDeadline( in accord varchar(45), in newdeadline date)
begin
update T_ACCORD set DEADLINE = newdeadline where ACCNUMBER = accord;
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS updateAccordType;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordType( in accord varchar(45), in newtype char(1))
begin
update T_ACCORD set TYPE_ID = newtype where ACCNUMBER = accord;
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS updateAccordState;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordState( in accord varchar(45), in newstate int)
begin
update T_ACCORD set STATE = newstate where ACCNUMBER = accord;
end$$
DELIMITER ;  


USE `KRONOS`;
DROP procedure IF EXISTS updateAccordObservations;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordObservations( in accord varchar(45), in new_observations longtext)
begin
update T_ACCORD set OBSERVATIONS = new_observations where ACCNUMBER = accord;
end$$
DELIMITER ;  


USE `KRONOS`;
DROP procedure IF EXISTS updateAccordUser;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordUser( in accord varchar(45), in newuser varchar(45))
begin
update T_USERACC set TEMPUSER = newuser where ACCORD = accord;
end$$
DELIMITER ; 




USE `KRONOS`;
DROP procedure IF EXISTS updateAccordUrls;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordUrls( in accord varchar(45), in newurl varchar(100), in anturl varchar(100))
begin
update T_ACCPDF set URL=newurl where ACCORD=accord and URL=anturl; 
end$$
DELIMITER ; 

USE `KRONOS`;
DROP procedure IF EXISTS updateAccordUrlResponse;
DELIMITER $$
USE `KRONOS`$$
create procedure updateAccordUrlResponse( in accord varchar(45), in url varchar(100), in finalResponse bool)
begin
update T_ACCPDF set FINALRESPONSE=finalResponse where ACCORD=accord and URL=anturl; 
end$$
DELIMITER ;


USE `KRONOS`;
DROP procedure IF EXISTS getUserRole;
DELIMITER $$
USE `KRONOS`$$
create procedure getUserRole( in _user varchar(45), in _password varchar(100))
begin
SELECT USER_NAME, ROLE_NAME, T_USER.DEPARTMENT FROM T_USERROLE,T_USER WHERE T_USERROLE.USER_NAME=_user AND (T_USER.TEMPUSER=_user
AND T_USER.PASSWORD=_password); 
end$$
DELIMITER ;


USE `KRONOS`;
DROP procedure IF EXISTS getUser;
DELIMITER $$
USE `KRONOS`$$
create procedure getUser( in _user varchar(45), in _password varchar(100))
begin
SELECT TEMPUSER, PASSWORD, DEPARTMENT FROM T_USER WHERE TEMPUSER=_user AND PASSWORD=_password; 
end$$
DELIMITER ;



USE `KRONOS`;
DROP procedure IF EXISTS getUserByEmail;
DELIMITER $$
USE `KRONOS`$$
create procedure getUserByEmail( in _user varchar(45))
begin
SELECT TEMPUSER,T_TEMPUSER.NAME AS NAME,PASSWORD, DEPARTMENT, T_DEPARTMENT.NAME AS DEPARTMENT_NAME  FROM T_USER,T_TEMPUSER, T_DEPARTMENT WHERE T_USER.DEPARTMENT=T_DEPARTMENT.ID AND T_USER.TEMPUSER=T_TEMPUSER.EMAIL AND TEMPUSER=_user; 
end$$
DELIMITER ;




USE `KRONOS`;
DROP procedure IF EXISTS searchAllStates;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAllStates()
begin
select  ID , DESCRIPTION from T_STATE;
end$$
DELIMITER ; 



USE `KRONOS`;
DROP procedure IF EXISTS searchAllTypes;
DELIMITER $$
USE `KRONOS`$$
create procedure searchAllTypes()
begin
select  ID , DESCRIPTION from T_TYPE;
end$$
DELIMITER ; 
                                                                                                    
USE `KRONOS`;
DROP procedure IF EXISTS searchExpiredAccords;
DELIMITER $$
USE `KRONOS`$$
create procedure searchExpiredAccords(in actual date, in _limit date)
begin
select ACCNUMBER, INCORDATE, 
DEADLINE, SESSIONDATE, TYPE_ID, OBSERVATIONS, PUBLIC, NOTIFIED,  STATE, T_ACCPDF.URL  from T_ACCORD, T_ACCPDF where ACCNUMBER= T_ACCPDF.ACCORD and T_ACCORD.DEADLINE <=actual and T_ACCORD.DEADLINE >=_limit;
end$$
DELIMITER ;

USE `KRONOS`;
DROP procedure IF EXISTS isActInDB;
DELIMITER $$
USE `KRONOS`$$
create procedure isActInDB(in mydate date)
begin
SELECT SESSIONDATE from T_ACT where SESSIONDATE=mydate;
end$$
DELIMITER ;



USE `KRONOS`;
DROP procedure IF EXISTS insertTempAct;
DELIMITER $$
USE `KRONOS`$$
create procedure insertTempAct(in mydate date)
begin
INSERT INTO T_ACT (SESSIONDATE) values (mydate);
COMMIT;
end$$
DELIMITER ;


USE `KRONOS`;
DROP procedure IF EXISTS deletePdf;
DELIMITER $$
USE `KRONOS`$$
create procedure deletePdf(
in accordNum varchar(45), in path varchar(100))
begin
delete from T_ACCPDF where ACCORD=accordNum and URL= path; 
commit;  
end$$ 
DELIMITER ;

USE `KRONOS`;
DROP procedure IF EXISTS emailInfo;
DELIMiTER $$
USE `KRONOS`$$
create procedure emailInfo(
in today date, in limt date)
begin
  select ACCNUMBER, INCORDATE, DEADLINE
  from T_ACCORD, T_TYPE where T_ACCORD.INCORDATE <= today and T_ACCORD.INCORDATE >=limt and T_TYPE.DESCRIPTION='Administración Municipal';
end$$ 
DELIMITER ;



insert into T_TYPE (ID, DESCRIPTION) values ('A', 'Administración Municipal');
insert into T_TYPE (ID, DESCRIPTION) values ('B', 'Auditoria Interna');
insert into T_TYPE (ID, DESCRIPTION) values ('C', 'Lic');
insert into T_TYPE (ID, DESCRIPTION) values ('D', 'Obras');
insert into T_TYPE (ID, DESCRIPTION) values ('E', 'Plan Regulador');
insert into T_TYPE (ID, DESCRIPTION) values ('F', 'Hacienda');
insert into T_TYPE (ID, DESCRIPTION) values ('G', 'Juridicos');
insert into T_TYPE (ID, DESCRIPTION) values ('H', 'Sociales');
insert into T_TYPE (ID, DESCRIPTION) values ('I', 'Ambiente');
insert into T_TYPE (ID, DESCRIPTION) values ('J', 'Varios');


insert into T_STATE (ID, DESCRIPTION)values (0, 'Cumplido');
insert into T_STATE (ID, DESCRIPTION)values (1, 'Incumplido');
insert into T_STATE (ID, DESCRIPTION)values (2, 'Pendiente');
insert into T_STATE (ID, DESCRIPTION)values (3, 'Recibido');
insert into T_STATE (ID, DESCRIPTION)values (4, 'Desestimado');

insert into T_TEMPUSER(NAME,EMAIL) values ('Concejo Municipal','concejomunicipal@sanpablo.go.cr');
insert into T_TEMPUSER(NAME,EMAIL) values ('Secretaria de Alcaldia','alcaldia@sanpablo.go.cr');
insert T_DEPARTMENT (ID,NAME) values (1,'SUPERUSER');
insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('concejomunicipal@sanpablo.go.cr','{noop}concejo',1,1);
insert into T_USER (TEMPUSER,PASSWORD,DEPARTMENT,STATUS) values ('alcaldia@sanpablo.go.cr','{noop}alcaldia',1,1);
insert into T_ROLE values (1,'Concejo Municipal');
insert into T_ROLE values (2,'Secretaria de Alcaldia');
insert into T_USER_ROLE (USER_ID,ROLE_ID) values ('concejomunicipal@sanpablo.go.cr',1);
insert into T_USER_ROLE (USER_ID,ROLE_ID) values ('alcaldia@sanpablo.go.cr',2);


