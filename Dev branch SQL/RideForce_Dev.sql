
/*******************************************************************************
   Create Tables
********************************************************************************/

drop table address cascade constraints;
drop table car cascade constraints;
drop table contact_info cascade constraints;
drop table dislikes cascade constraints;
drop table likes cascade constraints;
drop table contact_type cascade constraints;
drop table favorite_addresses cascade constraints;
drop table office cascade constraints;
drop table role cascade constraints;
drop table users cascade constraints;

CREATE TABLE address (
    cached_id    NUMBER(10) NOT NULL,
    address      VARCHAR2(85 CHAR),
    city         VARCHAR2(85 CHAR),
    latitude     FLOAT(126),
    longitude    FLOAT(126),
    state_code   VARCHAR2(2 CHAR),
    zip_code     VARCHAR2(255 CHAR)
);

CREATE TABLE car (
    car_id    NUMBER(10) NOT NULL,
    make      VARCHAR2(35 CHAR),
    model     VARCHAR2(30 CHAR),
    year      NUMBER(10),
    user_id   NUMBER(10) NOT NULL
);

CREATE TABLE contact_info (
    contact_info_id   NUMBER(10) NOT NULL,
    info              VARCHAR2(100 CHAR),
    contact_type_id   NUMBER(10) NOT NULL,
    user_id           NUMBER(10) NOT NULL
);

CREATE TABLE dislikes (
    affected_id   NUMBER(10) NOT NULL,
    user_id       NUMBER(10) NOT NULL
);

CREATE TABLE contact_type (
    contact_type_id   NUMBER(10) NOT NULL,
    type              VARCHAR2(20 CHAR) NOT NULL
);

CREATE TABLE favorite_addresses (
    location_id               NUMBER(10) NOT NULL,
    address                   VARCHAR2(85 CHAR),
    latitude                  FLOAT(126),
    longitude                 FLOAT(126),
    favorited_location_name   VARCHAR2(255 CHAR),
    user_id                   NUMBER(10)
);

CREATE TABLE office (
    office_id   NUMBER(10) NOT NULL,
    address     VARCHAR2(100 CHAR) NOT NULL,
    name        VARCHAR2(30 CHAR) NOT NULL
);
CREATE TABLE likes (
    affected_id   NUMBER(10) NOT NULL,
    user_id       NUMBER(10) NOT NULL
);

CREATE TABLE role (
    role_id   NUMBER(10) NOT NULL,
    type      VARCHAR2(30 CHAR) NOT NULL
);

CREATE TABLE users (
    user_id      NUMBER(10) NOT NULL,
    active       VARCHAR2(255 CHAR),
    address      VARCHAR2(255 CHAR) NOT NULL,
    batch_end    DATE,
    bio          VARCHAR2(255 CHAR),
    email        VARCHAR2(40 CHAR) NOT NULL,
    first_name   VARCHAR2(25 CHAR) NOT NULL,
    last_name    VARCHAR2(30 CHAR) NOT NULL,
    password     VARCHAR2(70 CHAR) NOT NULL,
    photo_url    VARCHAR2(200 CHAR),
    start_time   FLOAT(126) DEFAULT 9.0 NOT NULL,
    office_id    NUMBER(10) NOT NULL,
    role_id      NUMBER(10) NOT NULL
);

/* Alter tables */

ALTER TABLE address
    ADD CONSTRAINT address_pk PRIMARY KEY ( cached_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE car
    ADD CONSTRAINT car_pk PRIMARY KEY ( car_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE contact_info ADD CHECK ( contact_info_id >= 1 );

ALTER TABLE contact_info
    ADD CONSTRAINT contact_info_pk PRIMARY KEY ( contact_info_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE contact_type ADD CHECK ( contact_type_id >= 1 );

ALTER TABLE contact_type
    ADD CONSTRAINT contact_type_pk PRIMARY KEY ( contact_type_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE dislikes
    ADD CONSTRAINT dislikes_pk PRIMARY KEY ( affected_id,
                                             user_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE favorite_addresses ADD CHECK ( user_id >= 1 );

ALTER TABLE favorite_addresses
    ADD CONSTRAINT favorite_addresses_pk PRIMARY KEY ( location_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT );

ALTER TABLE likes
    ADD CONSTRAINT likes_pk PRIMARY KEY ( affected_id,
                                          user_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE office ADD CHECK ( office_id >= 1 );

ALTER TABLE office
    ADD CONSTRAINT office_pk PRIMARY KEY ( office_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE role ADD CHECK ( role_id >= 1 );

ALTER TABLE role
    ADD CONSTRAINT role_pk PRIMARY KEY ( role_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE users ADD CHECK ( user_id >= 1 );

CREATE UNIQUE INDEX uk_6dotkott2kjsp8vw4d0m25fb7 ON
    users (
        email
    ASC )
        TABLESPACE users PCTFREE 10
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT )
        LOGGING;

ALTER TABLE users
    ADD CONSTRAINT users_pk PRIMARY KEY ( user_id )
        USING INDEX PCTFREE 10 INITRANS 2 TABLESPACE users LOGGING
            STORAGE ( INITIAL 65536 NEXT 1048576 PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS 2147483645 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL
            DEFAULT );

ALTER TABLE users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE ( email )
        USING INDEX uk_6dotkott2kjsp8vw4d0m25fb7;

ALTER TABLE users
    ADD CONSTRAINT fk4qu1gr772nnf6ve5af002rwya FOREIGN KEY ( role_id )
        REFERENCES role ( role_id )
    NOT DEFERRABLE;

ALTER TABLE contact_info
    ADD CONSTRAINT fk8b9r676lain0grbn1jacg4t26 FOREIGN KEY ( user_id )
        REFERENCES users ( user_id )
    NOT DEFERRABLE;

ALTER TABLE car
    ADD CONSTRAINT fkc2osr9qmb46vr8pjyps6weii0 FOREIGN KEY ( user_id )
        REFERENCES users ( user_id )
    NOT DEFERRABLE;

ALTER TABLE contact_info
    ADD CONSTRAINT fkg0ycabphn86rwcuxirtbphs8k FOREIGN KEY ( contact_type_id )
        REFERENCES contact_type ( contact_type_id )
    NOT DEFERRABLE;

ALTER TABLE users
    ADD CONSTRAINT fkqkpp0ulm5tj5l7rvxwb6jw6t4 FOREIGN KEY ( office_id )
        REFERENCES office ( office_id )
    NOT DEFERRABLE;

ALTER TABLE users
    ADD CONSTRAINT fk4qu1gr772nnf6ve5af002rwya FOREIGN KEY ( role_id )
        REFERENCES role ( role_id )
    NOT DEFERRABLE;

ALTER TABLE contact_info
    ADD CONSTRAINT fk8b9r676lain0grbn1jacg4t26 FOREIGN KEY ( user_id )
        REFERENCES users ( user_id )
    NOT DEFERRABLE;

ALTER TABLE car
    ADD CONSTRAINT fkc2osr9qmb46vr8pjyps6weii0 FOREIGN KEY ( user_id )
        REFERENCES users ( user_id )
    NOT DEFERRABLE;

ALTER TABLE contact_info
    ADD CONSTRAINT fkg0ycabphn86rwcuxirtbphs8k FOREIGN KEY ( contact_type_id )
        REFERENCES contact_type ( contact_type_id )
    NOT DEFERRABLE;

ALTER TABLE users
    ADD CONSTRAINT fkqkpp0ulm5tj5l7rvxwb6jw6t4 FOREIGN KEY ( office_id )
        REFERENCES office ( office_id )
    NOT DEFERRABLE;
	
/* Sequences */
drop sequence hibernate_sequence;
drop sequence cardid;
drop sequence contactinfoid;
drop sequence contacttypeid;
drop sequence officeid;
drop sequence roleid;
drop sequence userid;

create sequence hibernate_sequence;
create sequence cardid;
create sequence contactinfoid;
create sequence contacttypeid;
create sequence officeid;
create sequence roleid;
create sequence userid;

/* Populate tables */

Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (3,'Slack');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (1,'Cell Phone');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (2,'Email');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (4,'Skype');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (5,'Discord');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (6,'Facebook');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (7,'GroupMe');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (8,'Other');
Insert into CONTACT_TYPE (CONTACT_TYPE_ID,TYPE) values (9,'Venmo');
commit;

Insert into OFFICE (OFFICE_ID,NAME,ADDRESS) values (3,'Arlington','701 W Nedderman Dr, Arlington, TX 76019');
Insert into OFFICE (OFFICE_ID,NAME,ADDRESS) values (1,'Reston','11730 Plaza America Dr #205, Reston, VA 20190');
Insert into OFFICE (OFFICE_ID,NAME,ADDRESS) values (2,'Tampa','4202 E Fowler Ave, Tampa, FL 33620');
Insert into OFFICE (OFFICE_ID,NAME,ADDRESS) values (4,'Flushing','65-30 Kissena Blvd, Flushing, NY 11367');
Insert into OFFICE (OFFICE_ID,NAME,ADDRESS) values (5,'New York','119 W 31st St, New York, NY 10001');
commit;

Insert into ROLE (ROLE_ID,TYPE) values (1,'Admin');
Insert into ROLE (ROLE_ID,TYPE) values (2,'Trainer');
Insert into ROLE (ROLE_ID,TYPE) values (3,'Driver');
Insert into ROLE (ROLE_ID,TYPE) values (4,'Rider');
commit;

Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (1,'Admin','Admin','admin@revature.com','$2a$10$CULLu.ZrqfVPxNld9tP9h.hOcTKB/55wZasCW6YXDRQvpXVf898W6','11730 Plaza America Dr. Reston, VA',null,'ACTIVE',1,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (57,'Sammy','Washere','samiam@yahoo.com','abcdefg','505 Pride Ave, Herndon, VA 20170',to_date('28-SEP-18','DD-MON-RR'),'ACTIVE',3,1,null,'Looking for ride',9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (58,'Bill','Nye','science@gmail.com','12345678','508 Pride Ave, Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',4,1,null,null,9.5);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (59,'Genesis','Bonds','genesis.bonds@gmail.com','12345678','11730 Plaza America Dr #205, Reston, VA 20190',null,'ACTIVE',2,1,null,null,9.5);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (60,'Jeffrey','Zhong','zhong62@gmail.com','12345678','2550 Astoria Cir Herndon, VA 20170',to_date('28-SEP-18','DD-MON-RR'),'ACTIVE',3,1,null,null,9.5);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (74,'FinalTest','TestFinal','finaltest','$2a$10$VTT5..4jxKRq3mBklhIkMOBPRTpYsr3b0TdtdqnpxQeMjSprwEyEy','The Townes at Hernodon, 508 Pride Ave, Herndon, VA 20170, USA',to_date('31-OCT-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-2.amazonaws.com/rydeforce/rydeforce-s3/67324979206code.png',null,9.5);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (62,'Will','Gentry','wilgen@gmail.com','12345678','2105 Raven Tower CtHerndon, VA 20170',null,'ACTIVE',2,1,null,null,9.5);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (63,'Dominick','Rowe','drowe@hotmail.com','123Password','2175 San Moritz Cir Herndon, VA 20170',to_date('17-OCT-18','DD-MON-RR'),'ACTIVE',4,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (64,'Bill','Larson','billrow@yahoo.com','Password123','2200 San Moritz Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',3,1,null,'Hi, anyone can come to my address for a free ride, 5 spots left.',9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (65,'Lorena','Campbell','campbells@hotmail.com','12345678','2161 San Moritz Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',4,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (66,'Penny','Hoffman','PennyRHoffman@jourrapide.com','12345678','2165 San Moritz Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',3,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (67,'Vern','Johnson','VernRJohnson@dayrep.com','12345678','2169 San Moritz Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',4,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (68,'Walter','Morillo','WalterPMorillo@dayrep.com','12345678','2204 Astoria Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',3,1,null,'Need temporary ride for a week',9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (69,'Gary','Jones','GaryHJones@teleworm.us','12345678','2208 Astoria Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',4,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (70,'David','Owens','DavidMOwens@jourrapide.com','12345678','2220 Astoria Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',3,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (71,'Kyle','Anderson','KyleSAnderson@jourrapide.com','12345678','2240 Astoria Cir Herndon, VA 20170',to_date('15-NOV-18','DD-MON-RR'),'ACTIVE',4,1,null,'I have 3 open seats.',9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (72,'Ray','Lin','Rayl@gmail.com','12345678','2204 Astoria Cir Herndon, VA 20170',to_date('26-SEP-18','DD-MON-RR'),'INACTIVE',4,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (56,'Ian','Johnson','ianprime0509@gmail.com','$2a$10$62a/AweddhJp9nXWfNf/r.VCAr0BjFu9FR.5NOp6R6YIWG4k1zXfS','2925 Rensselaer Ct. Vienna, VA 22181',to_date('26-SEP-18','DD-MON-RR'),'ACTIVE',3,1,null,null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (75,'Test','Test','test@email.com','$2a$10$E1dDYLO3sKWnZLwxUO/EROpFXcvBdZerZzdmZkkPG/ZfRoyzmNgLa','The Townes at Hernodon, 508 Pride Ave, Herndon, VA 20170, USA',to_date('31-OCT-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-2.amazonaws.com/rydeforce/rydeforce-s3/5196977272code.png',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (76,'Logan','Smith','lorgansmith@gmail.com','$2a$10$ggoF3Sg9Ekx/CMxMAnd7V.9qN5ei.TthIRFYxb/eBpdVqHi0T9ZMe','2212 Astoria Cir, Herndon, VA 20170, USA',to_date('26-SEP-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-2.amazonaws.com/rydeforce/rydeforce-s3/34580819262Chibiterasu - Copy.jpg',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (77,'Vien','Ly','vien@email.com','$2a$10$DFiIuhKgsmsWDOygPea2FuD3sB0LVZ0OP0RiqgQUJFBs.ZnTBjJ9.','The Townes at Hernodon, 508 Pride Ave, Herndon, VA 20170, USA',to_date('28-SEP-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/48399112913board.jpg',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (78,'Vien','Ly','v@email.com','$2a$10$xQYqExC2ZW5GkdQPgRkBjO8qqkBbOi6ioUCdTWZ7gd/Lc7XPdCbmq','The Townes at Hernodon, 508 Pride Ave, Herndon, VA 20170, USA',to_date('28-SEP-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/77506783097board.jpg',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (79,'Gen','Bonds','gab12@duke.edu','$2a$10$VPnROAPyvT3sdkIuswm1wutXRhqVYaJXmkhBghDQKOndtOf7bh4hu','2150 Astoria Cir, Herndon, VA 20170, USA',to_date('02-OCT-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/22036328474Genesis-167.jpg',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (80,'T','T','t','$2a$10$cAHb4FUSWo/F1aCpq7oDZ.zBU30DBkwIcHq.5F5GLFDjGEN6IB1la','The Townes at Hernodon, 508 Pride Ave, Herndon, VA 20170, USA',to_date('27-SEP-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/28851340820board.jpg',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (81,'demo','demo','demo','$2a$10$uT13JKyYKtFgLmXQOIIXLuelVe81giPQjR.3c1opqJZ.sbgGxn1zi','demo',to_date('03-JAN-01','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/3862600105board.jpg',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (82,'Zack','Ritchie','zack@revature.com','$2a$10$ZA7FGz7NHrFBoalW3CvUweIEU16vYbXxo9MLZheWXgcSONQVmTwEu','2224 Astoria Cir, Herndon, VA 20170, USA',to_date('19-OCT-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/68969697102Lucian.png',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (83,'Zackary','Ritchie','zack1@revature.com','$2a$10$9c0NRviAL9DHE.55muHqBe746k0faJbIUVvyFhdels3QNncPVf.3m','2224 Astoria Cir, Herndon, VA 20170, USA',to_date('07-NOV-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/70429139824Lucian.png',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (84,'Zachary','Ritchie','zack2@revature.com','$2a$10$E5T2QZFB9LNaLNpgTeHz/OvDLwfEl4/fxlos7go0X2c875cztn5di','2224 Astoria Cir, Herndon, VA 20170, USA',to_date('14-NOV-18','DD-MON-RR'),'ACTIVE',4,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/53658477382Lucian.png',null,9);
Insert into USERS (USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ADDRESS,BATCH_END,ACTIVE,ROLE_ID,OFFICE_ID,PHOTO_URL,BIO,START_TIME) values (101,'Martin','Smallwood','smallwoodmartin@gmail.com','$2a$10$UULo1LQFTNQMp2Q9zQFCweEJcvcVTDGCWfa6YMoPxk23XCUEPTg9.','508 Pride Ave, Herndon, VA',to_date('23-OCT-18','DD-MON-RR'),'ACTIVE',3,1,'https://s3.us-east-1.amazonaws.com/rydeforce/rydeforce-s3/25612686609Lucius Token.png',null,9);
commit;

----------------------------------------------------



Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (33,57,'Toyota','Land Cruiser',2000);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (34,60,'Honda','Civc',2012);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (35,64,'Infiniti','fx35',2012);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (36,66,'Hyundai','Veloster',2013);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (37,68,'Dodge','Charger',2014);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (38,68,'Toyota','Rav4',2014);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (39,70,'Porsche','Panamera',2018);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (42,38,'Ford','Fusion',2016);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (43,38,'Ford','Fusion',2016);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (44,38,'Ford','Fusion',2016);
Insert into CAR (CAR_ID,USER_ID,MAKE,MODEL,YEAR) values (45,38,'Ford2','Fusion2',2016);
commit;

Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (137,58,5,'bilnye123');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (138,58,2,'billmessage@hotmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (139,58,1,'630-554-4155');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (140,57,1,'630-123-4563');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (141,57,2,'sammy123s@gmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (142,57,3,'SamE');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (143,57,4,'Sammy WasHere');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (144,57,5,'ConcernedDad#5786');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (145,57,7,'610-123-4563');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (146,57,8,'BBlahbiddyBoopBop');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (147,59,1,'928-553-0191');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (148,59,2,'genesis.bonds@gmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (149,59,3,'Genesis Bonds');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (150,59,4,'GenniferBonds');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (151,59,7,'JehovaBonds');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (152,59,5,'Trainer#3434');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (153,59,6,'Geneva Bones');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (154,60,1,'630-544-6513');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (155,60,2,'IamJeffZhong@hotmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (156,60,3,'Jeffey');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (157,60,4,'HiMyNamesJeff');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (158,60,5,'Jefbert#4251');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (159,63,1,'21-777-2012');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (160,63,2,'dom@runmail.co');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (161,63,3,'Dommy');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (162,63,6,'Dominic Rowe');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (163,64,1,'808-209-2645');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (164,64,2,'eskimo@blankmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (165,64,3,'BulletBill');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (166,64,5,'MrRevature#8885');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (167,65,1,'365-090-2713');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (168,65,2,'LorenaCampbell@verizon.net');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (169,65,3,'GreetNMeet');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (170,65,6,'Lorena Campbell');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (171,66,1,'512-218-2898');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (172,66,2,'juggler@yahoo.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (173,66,3,'LorenaC');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (174,66,6,'Lorena Campbell');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (175,67,1,'314-684-7568');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (176,67,2,'loverboy31@gmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (177,68,1,'412-856-8867');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (178,68,2,'lovergirl31@gmail.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (179,69,1,'175-994-2311');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (180,69,2,'thedangler@aol.com');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (181,70,1,'553-592-5925');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (182,70,2,'feedme@radio76.net');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (183,71,1,'439-193-6011');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (184,71,2,'copypasta@pasta.net');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (185,60,9,'jeffz123');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (186,64,9,'blarson1');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (187,65,9,'campbell44');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (188,66,9,'Hoffyy');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (189,69,9,'Jones4Gary');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (190,71,9,'AnderKyle');
Insert into CONTACT_INFO (CONTACT_INFO_ID,USER_ID,CONTACT_TYPE_ID,INFO) values (191,57,9,'smmy100');
commit;
