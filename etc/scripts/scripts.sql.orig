CREATE DATABASE  IF NOT EXISTS zyb2;

CREATE USER 'zyb-site'@'localhost' IDENTIFIED BY 'site';
GRANT ALL PRIVILEGES ON *.* TO 'zyb-site'@'localhost' WITH GRANT OPTION;
CREATE USER 'zyb-site'@'%' IDENTIFIED BY 'site';
GRANT ALL PRIVILEGES ON *.* TO 'zyb-site'@'%' WITH GRANT OPTION;

use zyb2;

drop table students;

create table students(
    cid int NOT NULL AUTO_INCREMENT,
    name VARCHAR(200) not null,
    description text not null,
    image longblob,
    imageName VARCHAR(200),
    weight int default 0 not null,
    constraint pk_students primary key(cid)
);

-- drop table zyb_personnel_type;

create table zyb_personnel_type(
    name VARCHAR(200) not null,
    description text not null,
    caption VARCHAR(200) not null,
    constraint pk_zyb_personnel_type primary key(name)
);

insert into zyb_personnel_type (name, description, caption) values ('advisoryboard', 'Advisory Board', 'Advisory Board');
insert into zyb_personnel_type (name, description, caption) values ('managementteam', 'Management Team', 'Management Team');
insert into zyb_personnel_type (name, description, caption) values ('trainers', 'Trainers goes here', 'Trainers');

-- drop table zyb_personnel;

create table zyb_personnel(
    cid int NOT NULL AUTO_INCREMENT,
    p_type VARCHAR(200) not null,
    name VARCHAR(200) not null,
    designation VARCHAR(200) not null,
    company VARCHAR(200) not null,
    image longblob,
    imageName VARCHAR(200),
    item_order int default 0 not null,
    summary text,
    constraint pk_zyb_personnel primary key(cid),
    constraint fk_zyb_personnel_type foreign key (p_type) references zyb_personnel_type(name)
);

-- drop table placement_openings
create table placement_openings(
    cid int NOT NULL AUTO_INCREMENT,
    createddate timestamp,
    company VARCHAR(200),
    position VARCHAR(200),
    noofopenings int default 1 not null,
    location VARCHAR(200),
    contactperson VARCHAR(200),
    contactemail VARCHAR(200),
    contactnumber VARCHAR(200),
    jobdescription text,
    desiredprofile text,
    constraint pk_placement_openings primary key (cid)
);

-- drop table ebrouchers
create table ebrouchers(
    cid int NOT NULL AUTO_INCREMENT,
    createddate timestamp not null, 
    modifiedDate timestamp,
    item_order int not null,
    caption VARCHAR(200) not null,
    name VARCHAR(200) not null,
    broucher longblob not null,
    constraint pk_ebrouchers primary key (cid)
);

-- -------------------------------------------------------------------------------
insert into zyb_personnel (p_type, name, designation, company, image, item_order, summary)
values ('managementteam', 'Sunil P Johny', 'Managing Partner', 'Zybotech Solutions',
    '{contextPath}/resources/photos/management/sunil.jpg', 2,
    'Sunil is an MBA marketing graduate, having three plus years of experience in marketing IN IT and ITES sector. After worked with some of ht leading IT service providers, Sunil with his friends started up the most successful mobile application development and training company in Cochin zybotech solutions. currently engaged with zybotech solutions business development and client management.'
);
insert into zyb_personnel (p_type, name, designation, company, image, item_order, summary)
values ('managementteam', 'Sagar S', 'Managing Partner', 'Zybotech Solutions',
    '{contextPath}/resources/photos/management/sagar.jpg', 1,
    'Sagar an MBA graduate from UK, with experienced in handling complex projects in many MNC''s now engaged with Zybotech solutions project management.'
);
insert into zyb_personnel (p_type, name, designation, company, image, item_order, summary)
values ('trainers', 'Dixon James Melitt', 'Sr. Android Developer', 'Zybotech Solutions',
    '{contextPath}/resources/photos/trainer/dixon.jpg', 1,
    'Dixon, an experienced java developer, has been involved with the Android horizon for the past 3 years since he has been with us. He has achieved projects in various skills in Android, Java and gaming APIs. He is also responsible for the preparation of the Android course material and has organized training sessions for working professionals and students in educational intitutions. His interests and current pursuation of mobile application development technologies has been duly seen to the syllabus giving the trainee a vast and indepth knowledge of the Android operating system.'
);
