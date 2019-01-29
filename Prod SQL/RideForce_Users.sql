/* User-service */

drop table users cascade constraints;
drop table contact_info cascade constraints;
drop table contact_type cascade constraints;
drop table role cascade constraints;
drop table office cascade constraints;
drop table car cascade constraints;

create table users (
	user_id number(10) not null primary key,
	active varchar2(255 char),
	address varchar2(255 char) not null,
	batchEnd date,
	bio varchar2(255 char),
	email varchar2(40 char) not null,
	firstname varchar2(25 char) not null,
	lastname varchar2(30 char) not null,
	password varchar2(70 char) not null,
	photoUrl varchar2(200 char),
	startTime float(126) not null,
	venmo varchar2(30 char),
	office_id number(10) not null,
	role_id number(10) not null
);

create table contact_info (
	contact_info_id number(10) not null primary key,
	info varchar2(100 char),
	contact_type_id number(10) not null,
	user_id number(10) not null
);

create table contact_type (
	contact_type_id number(10) not null primary key,
	type varchar2(20 char) not null
);

create table role (
	role_id number (10) not null primary key,
	type varchar2(30 char) not null
);

create table office (
	office_id number(10) not null primary key,
	address varchar2(100 char) not null,
	name varchar2(30 char) not null
);

create table car (
	car_id number(10) not null primary key,
	make varchar2(35 char),
	model varchar2(30 char),
	year number(10),
	user_id number(10) not null
);

alter table users add constraint uk_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users add constraint fk4qu1gr772nnf6ve5af002rwya foreign key (role_id) references role(role_id);
alter table users add constraint fkqkpp0ulm5tj5l7rvxwb6jw6t4 foreign key (office_id) references office(office_id) on delete cascade;

create unique index uk_6dotkott2kjsp8vw4d0m25fb7 on users (email asc);

alter table contact_info add constraint fk8b9r676lain0grbn1jacg4t26 foreign key (user_id) references users(user_id) on delete cascade;
alter table contact_info add constraint fkg0ycabphn86rwcuxirtbphs8k foreign key (contact_type_id) references contact_type(contact_type_id) on delete cascade;

alter table car add constraint fkc2osr9qmb46vr8pjyps6weii0 foreign key (user_id) references users(user_id) on delete cascade;

drop sequence cardid;
drop sequence contactinfoid;
drop sequence contacttypeid;
drop sequence officeid;
drop sequence roleid;
drop sequence userid;

create sequence cardid;
create sequence contactinfoid;
create sequence contacttypeid;
create sequence officeid;
create sequence roleid;
create sequence userid;

create or replace function getSubtotal(total in number) 
return number
is
begin
  return total+total*(0.06);
end;
/

create or replace function strcat(str in varchar2, n in number)
return varchar2
is 
temp number := 0;
newstr varchar2(50);
begin
  loop
    newstr := newstr ||' ' || str;
    temp := temp+1;
    if temp = n then
      exit;
    end if;
  end loop;
  return newstr;
end;

/

commit;