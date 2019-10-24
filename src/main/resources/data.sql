Insert into Roles(rid, rname) values(1,'Driver');
commit;
Insert into Roles(rid, rname) values(2,'Rider');
commit;


--Location insert
INSERT INTO locations VALUES(5, "500 Koehler Dr", "Morgantown", "WV", 26505, 39.656755, -79.9283087); --VALID
INSERT INTO locations VALUES(7, "496 High St", "Morgantown", "WV", 26505, 39.631848,-79.9554057); --VALID
INSERT INTO locations VALUES(6, "444 Someplace Dr", "SomeTown", "II", 33333, 45.123456, -20.1234567); --INVALID

--Test data with one user as a rider, one as both, and one as a driver
INSERT INTO users VALUES(10, "timsemail@revature.com", "pass", "TestFirst", "TestLast", 5); -- RIDER ONLY
INSERT INTO users VALUES(11, "timsemail2@revature.com", "pass", "TestFirst2", "TestLast2", 6); -- BOTH
INSERT INTO users VALUES(12, "timsemail3@revature.com", "pass", "TestFirst3", "TestLast3", 7); -- DRIVER ONLY
--Junction Table
INSERT INTO roles_users_jt(2, 10);
INSERT INTO roles_users_jt(1, 11);
INSERT INTO roles_users_jt(2, 11);
INSERT INTO roles_users_jt(1, 12);
