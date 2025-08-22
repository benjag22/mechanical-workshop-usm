drop schema if exists mechanical_workshop_usm;
create schema mechanical_workshop_usm;

use mechanical_workshop_usm;

create table client_info(
	id int AUTO_INCREMENT primary key,
    first_name varchar(64) not null check (first_name!=""),
    last_name varchar(64),
    email_address varchar(255) not null check ( "" != email_address),
    address varchar(128) not null check (address != ""),
    cellphone_number varchar(12) not null check (cellphone_number != "")
);

create table mechanic_info(
	id int AUTO_INCREMENT primary key,
    first_name varchar(64) not null check(first_name != ""),
    last_name varchar(64),
    registration_number varchar(16)
);
create table car_brand(
	id int AUTO_INCREMENT primary key,
    brand_name varchar(32) unique not null check(brand_name != "")
);

create table car_model(
	id int AUTO_INCREMENT primary key,
    brand_id int not null,
	model_name varchar(78) not null check (model_name != ""),
	model_type varchar(64),
    model_year int,
    foreign key (brand_id) references car_brand(id)
);

create table car(
	id int AUTO_INCREMENT primary key,
    model_id int not null,
    license_plate varchar(6) unique not null,
    VIN varchar(17) unique not null check (VIN != ""),
    foreign key(model_id) references car_model(id)
);

create table record(
	id int AUTO_INCREMENT primary key,
    reason varchar(255) not null,
    car_id int not null,
    client_info_id int not null,
    mechanic_info_id int not null,
    check (reason != ""),
    foreign key (car_id) references car(id),
    foreign key (client_info_id) references client_info(id),
    foreign key (mechanic_info_id) references mechanic_info(id)
);

create table record_state(
	id int AUTO_INCREMENT primary key,
    record_id int not null,
    entry_date date,
    entry_time time,
    mileage int not null,
    foreign key (record_id) references record(id)
);

create table entry_state(
	id int AUTO_INCREMENT primary key,
	record_state_id int not null,
    valuables varchar(255),
    gas_level enum("full", "3/4", "half", "1/4", "low"),
	foreign key (record_state_id) references record_state(id)
);

create table out_state(
	id int AUTO_INCREMENT primary key,
	record_state_id int not null,
    vehicle_diagnosis varchar(255),
    rating tinyint not null,
	foreign key (record_state_id) references record_state(id)
);

create table tool(
	id int AUTO_INCREMENT primary key,
    tool_name varchar(32) unique not null
);

create table entry_state_has_tool(
	id int AUTO_INCREMENT primary key,
    tool_id int not null,
    entry_state_id int not null,
    foreign key (tool_id) references tool(id),
    foreign key (entry_state_id) references entry_state(id)
);

create table mechanical_condition(
	id int AUTO_INCREMENT primary key,
    part_name varchar(64) not null check (part_name != ""),
    part_condition_state varchar(128),
	unique uk_part_name_condition (part_name, part_condition_state)
);

create table entry_state_consider_condition(
	id int AUTO_INCREMENT primary key,
    mechanical_condition_id int not null,
    entry_state_id int not null,
    foreign key (mechanical_condition_id) references mechanical_condition(id),
    foreign key (entry_state_id) references entry_state(id)
);

create table exterior_condition(
	id int auto_increment primary key,
    mechanical_condition_id int not null,
    foreign key (mechanical_condition_id) references mechanical_condition(id)
);
create table interior_condition(
	id int auto_increment primary key,
    mechanical_condition_id int not null,
    foreign key (mechanical_condition_id) references mechanical_condition(id)
);
create table electrical_system_condition(
	id int auto_increment primary key,
    mechanical_condition_id int not null,
    foreign key (mechanical_condition_id) references mechanical_condition(id)
);

create table work_order(
	id int auto_increment primary key,
    estimated_date date not null,
    estimated_time time not null
);

create table work_service(
	id int auto_increment primary key,
    service_name varchar(32) not null check(service_name != "")
);

create table work_order_realized_service(
	id int auto_increment primary key,
    work_order_id int not null,
    work_service_id int not null,
    finalized bool not null,
    foreign key(work_order_id) references work_order(id),
    foreign key(work_service_id) references work_service(id)
);


