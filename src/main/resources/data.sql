Insert into Roles(rid, rname) values(1,'Driver');
commit;
Insert into Roles(rid, rname) values(2,'Rider');
commit;


--Location insert
INSERT INTO locations VALUES(5, '500 Koehler Dr', 'Morgantown', 39.656755, -79.9283087, 'WV', 26505); --VALID
INSERT INTO locations VALUES(7, '496 High St', 'Morgantown', 39.631848,-79.9554057, 'WV', 26505); --VALID
INSERT INTO locations VALUES(6, '444 Someplace Dr', 'SomeTown', 45.123456, -20.1234567, 'II', 33333); --INVALID

--Test data with one user as a rider, one as both, and one as a driver
INSERT INTO users VALUES(10, 'timsemail@revature.com', 'TestFirst', 0, 'TestLast', 'pass', 5); -- RIDER ONLY
INSERT INTO users VALUES(11, 'timsemail2@revature.com', 'TestFirst2', 1, 'TestLast2', 'pass', 6); -- BOTH
INSERT INTO users VALUES(12, 'timsemail3@revature.com', 'TestFirst3', 1, 'TestLast3', 'pass', 7); -- DRIVER ONLY
--Junction Table
INSERT INTO roles_users_jt(2, 10);
INSERT INTO roles_users_jt(1, 11);
INSERT INTO roles_users_jt(2, 11);
INSERT INTO roles_users_jt(1, 12);
