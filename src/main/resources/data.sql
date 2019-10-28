Insert into Roles(rid, rname) values(1,'Driver');
commit;
Insert into Roles(rid, rname) values(2,'Rider');
commit;

--Location test inserts
INSERT INTO locations VALUES(-1, '500 Koehler Dr', 'Morgantown', 39.656755, -79.9283087, 'WV', 26505); --VALID
INSERT INTO locations VALUES(-2, '496 High St', 'Morgantown', 39.631848,-79.9554057, 'WV', 26505); --VALID
INSERT INTO locations VALUES(-3, '444 Someplace Dr', 'SomeTown', 45.123456, -20.1234567, 'II', 33333); --INVALID

--Test data with one user as a rider, one as both, and one as a driver
INSERT INTO users VALUES(-1, 'timsemail@revature.com', 'TestFirst', 0, 'TestLast', 'pass', -1); -- RIDER ONLY
INSERT INTO users VALUES(-2, 'timsemail2@revature.com', 'TestFirst2', 1, 'TestLast2', 'pass', -2); -- BOTH
INSERT INTO users VALUES(-3, 'timsemail3@revature.com', 'TestFirst3', 1, 'TestLast3', 'pass', -3); -- DRIVER ONLY
--Junction Table tests
INSERT INTO roles_users_jt VALUES(-1, 2);
INSERT INTO roles_users_jt VALUES(-2, 1);
INSERT INTO roles_users_jt VALUES(-2, 2);
INSERT INTO roles_users_jt VALUES(-3, 1);
commit;
--SEQUENCES--------------------
--USERS
--CREATE OR REPLACE SEQUENCE user_id_maker MINVALUE 50 START WITH 50 INCREMENT BY 1;
--ROLES
--CREATE OR REPLACE SEQUENCE role_id_maker MINVALUE 50 START WITH 50 INCREMENT BY 1;
--LOCATIONS
--CREATE OR REPLACE SEQUENCE location_id_maker MINVALUE 50 START WITH 50 INCREMENT BY 1;
