create database if not exists usm_mechanical_workshop;

use usm_mechanical_workshop;

drop table if exists work_order_has_mechanics;
drop table if exists car_images;
drop table if exists image;
drop table if exists dashboard_light;
drop table if exists work_order_realized_service;
drop table if exists work_service;
drop table if exists work_order;
drop table if exists entry_state_consider_condition;
drop table if exists exterior_condition;
drop table if exists interior_condition;
drop table if exists electrical_system_condition;
drop table if exists entry_state_has_tool;
drop table if exists tool;
drop table if exists out_state;
drop table if exists entry_state;
drop table if exists record_state;
drop table if exists record;
drop table if exists car;
drop table if exists car_model;
drop table if exists car_brand;
drop table if exists mechanic_info;
drop table if exists client_info;

create table client_info
(
    id               int auto_increment primary key,
    rut varchar(11) unique not null,
    first_name       varchar(64)  not null check (first_name != ''),
    last_name        varchar(64),
    email_address    varchar(255) not null check ( '' != email_address),
    address          varchar(128) not null check (address != ''),
    cellphone_number varchar(12)  not null check (cellphone_number != '')
);

create table  mechanic_info
(
    id                  int auto_increment primary key,
    name          varchar(64) not null check ( '' != name),
    rut varchar(11) unique not null
);

create table car_brand
(
    id         int auto_increment primary key,
    brand_name varchar(32) unique not null check (brand_name != '')
    );

create table car_model
(
    id         int auto_increment primary key,
    brand_id   int         not null,
    model_name varchar(78) not null check (model_name != ''),
    model_type varchar(64),
    model_year int,
    foreign key (brand_id) references car_brand (id)
);

create table car
(
    id            int auto_increment primary key,
    model_id      int                not null,
    license_plate varchar(6) unique  not null,
    VIN           varchar(17) unique not null check (VIN != ''),
    foreign key (model_id) references car_model (id)
);

create table record
(
    id             int auto_increment primary key,
    reason         varchar(255) not null,
    car_id         int          not null,
    client_info_id int          not null,
    check (reason != ''),
    foreign key (car_id) references car (id),
    foreign key (client_info_id) references client_info (id)
);

create table record_state
(
    id         int auto_increment primary key,
    record_id  int         not null,
    entry_time datetime(3) NOT NULL,
    mileage    int         not null,
    foreign key (record_id) references record (id)
);

create table entry_state
(
    id              int auto_increment primary key,
    record_state_id int not null,
    valuables       varchar(255),
    observations    varchar(255),
    gas_level       enum ('FULL','THREE_QUARTERS','HALF','ONE_QUARTER','LOW'),
    foreign key (record_state_id) references record_state (id)
);

create table out_state
(
    id                int auto_increment primary key,
    record_state_id   int     not null,
    vehicle_diagnosis varchar(255),
    rating            tinyint not null,
    foreign key (record_state_id) references record_state (id)
);

create table tool(
    id        int auto_increment primary key,
    tool_name varchar(32) unique not null
);

create table entry_state_has_tool
(
    id             int auto_increment primary key,
    tool_id        int not null,
    entry_state_id int not null,
    foreign key (tool_id) references tool (id),
    foreign key (entry_state_id) references entry_state (id)
);

create table exterior_condition
(
    id                   int auto_increment primary key,
    part_name            varchar(64) not null check (part_name != ''),
    part_condition_state varchar(128),
    unique uk_part_name_condition (part_name, part_condition_state)
);

create table interior_condition
(
    id                   int auto_increment primary key,
    part_name            varchar(64) not null check (part_name != ''),
    part_condition_state varchar(128),
    unique uk_part_name_condition (part_name, part_condition_state)
);

create table electrical_system_condition
(
    id                   int auto_increment primary key,
    part_name            varchar(64) not null check (part_name != ''),
    part_condition_state varchar(128),
    unique (part_name, part_condition_state)
);

create table entry_state_consider_condition
(
    id                             int AUTO_INCREMENT primary key,
    exterior_condition_id          int default null,
    interior_condition_id          int default null,
    electrical_system_condition_id int default null,
    entry_state_id                 int not null,
    foreign key (exterior_condition_id) references exterior_condition (id),
    foreign key (interior_condition_id) references interior_condition (id),
    foreign key (electrical_system_condition_id) references electrical_system_condition (id),
    foreign key (entry_state_id) references entry_state (id)
);

create table work_order
(
    id             int auto_increment primary key,
    record_id      int  not null,
    completed boolean not null default false,
    created_at datetime not null,
    estimated_delivery datetime not null,
    signature_path varchar(512) default null,
    foreign key (record_id) references record (id)
);

create table work_service
(
    id             int auto_increment primary key,
    service_name   varchar(32) unique not null check (service_name != ''),
    estimated_time time               not null
);

create table work_order_realized_service
(
    id              int auto_increment primary key,
    work_order_id   int  not null,
    work_service_id int  not null,
    finalized       bool not null,
    foreign key (work_order_id) references work_order (id),
    foreign key (work_service_id) references work_service (id)
);

create table image
(
    id   int auto_increment primary key,
    alt  varchar(255) not null,
    path varchar(512) unique not null
);

create table dashboard_light_present
(
    id   int auto_increment primary key,
    dashboard_light_id int not null,
    work_order_id      int not null,
    is_functional boolean,
    foreign key (work_order_id) references work_order (id),
    foreign key (dashboard_light_id) references image (id)
);

create table car_images
(
    id            int auto_increment primary key,
    work_order_id int not null,
    image_id      int not null,
    foreign key (work_order_id) references work_order (id),
    foreign key (image_id) references image (id)
);


create table work_order_has_mechanics
(
    id               int auto_increment primary key,
    work_order_id    int  not null,
    mechanic_info_id int  not null,
    is_leader        bool not null,
    foreign key (work_order_id) references work_order (id),
    foreign key (mechanic_info_id) references mechanic_info (id),
    unique (work_order_id, mechanic_info_id)
);

INSERT INTO interior_condition (part_name, part_condition_state)
values ('Encendedor', 'Malo'),
       ('Encendedor', 'Trizado'),
       ('Encendedor', 'Abollado'),
       ('Encendedor', 'Rayado'),
       ('Encendedor', 'Roto'),
       ('Encendedor', 'Ausente'),
       ('Radio', 'Malo'),
       ('Radio', 'Trizado'),
       ('Radio', 'Abollado'),
       ('Radio', 'Rayado'),
       ('Radio', 'Roto'),
       ('Radio', 'Ausente'),
       ('Luz Interior', 'Malo'),
       ('Luz Interior', 'Trizado'),
       ('Luz Interior', 'Abollado'),
       ('Luz Interior', 'Rayado'),
       ('Luz Interior', 'Roto'),
       ('Luz Interior', 'Ausente'),
       ('Espejo Interior', 'Malo'),
       ('Espejo Interior', 'Trizado'),
       ('Espejo Interior', 'Abollado'),
       ('Espejo Interior', 'Rayado'),
       ('Espejo Interior', 'Roto'),
       ('Espejo Interior', 'Ausente'),
       ('Pisos de Goma', 'Malo'),
       ('Pisos de Goma', 'Trizado'),
       ('Pisos de Goma', 'Abollado'),
       ('Pisos de Goma', 'Rayado'),
       ('Pisos de Goma', 'Roto'),
       ('Pisos de Goma', 'Ausente'),
       ('Cinturón de Seguridad', 'Malo'),
       ('Cinturón de Seguridad', 'Trizado'),
       ('Cinturón de Seguridad', 'Abollado'),
       ('Cinturón de Seguridad', 'Rayado'),
       ('Cinturón de Seguridad', 'Roto'),
       ('Cinturón de Seguridad', 'Ausente'),
       ('Aire Acondicionado', 'Malo'),
       ('Aire Acondicionado', 'Trizado'),
       ('Aire Acondicionado', 'Abollado'),
       ('Aire Acondicionado', 'Rayado'),
       ('Aire Acondicionado', 'Roto'),
       ('Aire Acondicionado', 'Ausente'),
       ('Cierre Centralizado', 'Malo'),
       ('Cierre Centralizado', 'Trizado'),
       ('Cierre Centralizado', 'Abollado'),
       ('Cierre Centralizado', 'Rayado'),
       ('Cierre Centralizado', 'Roto'),
       ('Cierre Centralizado', 'Ausente'),
       ('Manillas y Botonera', 'Malo'),
       ('Manillas y Botonera', 'Trizado'),
       ('Manillas y Botonera', 'Abollado'),
       ('Manillas y Botonera', 'Rayado'),
       ('Manillas y Botonera', 'Roto'),
       ('Manillas y Botonera', 'Ausente'),
       ('Tapiceria', 'Malo'),
       ('Tapiceria', 'Trizado'),
       ('Tapiceria', 'Abollado'),
       ('Tapiceria', 'Rayado'),
       ('Tapiceria', 'Roto'),
       ('Tapiceria', 'Ausente'),
       ('Accesorios Electrónico', 'Malo'),
       ('Accesorios Electrónico', 'Trizado'),
       ('Accesorios Electrónico', 'Abollado'),
       ('Accesorios Electrónico', 'Rayado'),
       ('Accesorios Electrónico', 'Roto'),
       ('Accesorios Electrónico', 'Ausente');

insert into exterior_condition (part_name, part_condition_state)
values ('Luneta Delantera', 'Malo'),
       ('Luneta Delantera', 'Trizado'),
       ('Luneta Delantera', 'Abollado'),
       ('Luneta Delantera', 'Rayado'),
       ('Luneta Delantera', 'Roto'),
       ('Luneta Delantera', 'Ausente'),
       ('Luneta Trasera', 'Malo'),
       ('Luneta Trasera', 'Trizado'),
       ('Luneta Trasera', 'Abollado'),
       ('Luneta Trasera', 'Rayado'),
       ('Luneta Trasera', 'Roto'),
       ('Luneta Trasera', 'Ausente'),
       ('Luneta Laterales', 'Malo'),
       ('Luneta Laterales', 'Trizado'),
       ('Luneta Laterales', 'Abollado'),
       ('Luneta Laterales', 'Rayado'),
       ('Luneta Laterales', 'Roto'),
       ('Luneta Laterales', 'Ausente'),
       ('Limpia Parabrisas', 'Malo'),
       ('Limpia Parabrisas', 'Trizado'),
       ('Limpia Parabrisas', 'Abollado'),
       ('Limpia Parabrisas', 'Rayado'),
       ('Limpia Parabrisas', 'Roto'),
       ('Limpia Parabrisas', 'Ausente'),
       ('Retrovisores Exteriores', 'Malo'),
       ('Retrovisores Exteriores', 'Trizado'),
       ('Retrovisores Exteriores', 'Abollado'),
       ('Retrovisores Exteriores', 'Rayado'),
       ('Retrovisores Exteriores', 'Roto'),
       ('Retrovisores Exteriores', 'Ausente'),
       ('Neumáticos Delanteros', 'Malo'),
       ('Neumáticos Delanteros', 'Trizado'),
       ('Neumáticos Delanteros', 'Abollado'),
       ('Neumáticos Delanteros', 'Rayado'),
       ('Neumáticos Delanteros', 'Roto'),
       ('Neumáticos Delanteros', 'Ausente'),
       ('Neumáticos Traseros', 'Malo'),
       ('Neumáticos Traseros', 'Trizado'),
       ('Neumáticos Traseros', 'Abollado'),
       ('Neumáticos Traseros', 'Rayado'),
       ('Neumáticos Traseros', 'Roto'),
       ('Neumáticos Traseros', 'Ausente'),
       ('Luminaria Delantera', 'Malo'),
       ('Luminaria Delantera', 'Trizado'),
       ('Luminaria Delantera', 'Abollado'),
       ('Luminaria Delantera', 'Rayado'),
       ('Luminaria Delantera', 'Roto'),
       ('Luminaria Delantera', 'Ausente'),
       ('Luminaria Trasera', 'Malo'),
       ('Luminaria Trasera', 'Trizado'),
       ('Luminaria Trasera', 'Abollado'),
       ('Luminaria Trasera', 'Rayado'),
       ('Luminaria Trasera', 'Roto'),
       ('Luminaria Trasera', 'Ausente'),
       ('Antena', 'Malo'),
       ('Antena', 'Trizado'),
       ('Antena', 'Abollado'),
       ('Antena', 'Rayado'),
       ('Antena', 'Roto'),
       ('Antena', 'Ausente'),
       ('Llantas', 'Malo'),
       ('Llantas', 'Trizado'),
       ('Llantas', 'Abollado'),
       ('Llantas', 'Rayado'),
       ('Llantas', 'Roto'),
       ('Llantas', 'Ausente'),
       ('Molduras', 'Malo'),
       ('Molduras', 'Trizado'),
       ('Molduras', 'Abollado'),
       ('Molduras', 'Rayado'),
       ('Molduras', 'Roto'),
       ('Molduras', 'Ausente'),
       ('Emblemas', 'Malo'),
       ('Emblemas', 'Trizado'),
       ('Emblemas', 'Abollado'),
       ('Emblemas', 'Rayado'),
       ('Emblemas', 'Roto'),
       ('Emblemas', 'Ausente'),
       ('Manillas y Botoneras', 'Malo'),
       ('Manillas y Botoneras', 'Trizado'),
       ('Manillas y Botoneras', 'Abollado'),
       ('Manillas y Botoneras', 'Rayado'),
       ('Manillas y Botoneras', 'Roto'),
       ('Manillas y Botoneras', 'Ausente'),
       ('Tapón de Combustibles', 'Malo'),
       ('Tapón de Combustibles', 'Trizado'),
       ('Tapón de Combustibles', 'Abollado'),
       ('Tapón de Combustibles', 'Rayado'),
       ('Tapón de Combustibles', 'Roto'),
       ('Tapón de Combustibles', 'Ausente'),
       ('Tapa de Combustible', 'Malo'),
       ('Tapa de Combustible', 'Trizado'),
       ('Tapa de Combustible', 'Abollado'),
       ('Tapa de Combustible', 'Rayado'),
       ('Tapa de Combustible', 'Roto'),
       ('Tapa de Combustible', 'Ausente');

insert into electrical_system_condition (part_name, part_condition_state)
values ('Control de Niveles Motor', 'Malo'),
       ('Control de Niveles Motor', 'Trizado'),
       ('Control de Niveles Motor', 'Abollado'),
       ('Control de Niveles Motor', 'Rayado'),
       ('Control de Niveles Motor', 'Roto'),
       ('Control de Niveles Motor', 'Ausente'),
       ('Control de Niveles Accesorios', 'Malo'),
       ('Control de Niveles Accesorios', 'Trizado'),
       ('Control de Niveles Accesorios', 'Abollado'),
       ('Control de Niveles Accesorios', 'Rayado'),
       ('Control de Niveles Accesorios', 'Roto'),
       ('Control de Niveles Accesorios', 'Ausente'),
       ('Estado Fluido líquido de Frenos', 'Malo'),
       ('Estado Fluido líquido de Frenos', 'Trizado'),
       ('Estado Fluido líquido de Frenos', 'Abollado'),
       ('Estado Fluido líquido de Frenos', 'Rayado'),
       ('Estado Fluido líquido de Frenos', 'Roto'),
       ('Estado Fluido líquido de Frenos', 'Ausente'),
       ('Estado del Sistema de Carga', 'Malo'),
       ('Estado del Sistema de Carga', 'Trizado'),
       ('Estado del Sistema de Carga', 'Abollado'),
       ('Estado del Sistema de Carga', 'Rayado'),
       ('Estado del Sistema de Carga', 'Roto'),
       ('Estado del Sistema de Carga', 'Ausente'),
       ('Luces Indicadoras de Falla', 'Malo'),
       ('Luces Indicadoras de Falla', 'Trizado'),
       ('Luces Indicadoras de Falla', 'Abollado'),
       ('Luces Indicadoras de Falla', 'Rayado'),
       ('Luces Indicadoras de Falla', 'Roto'),
       ('Luces Indicadoras de Falla', 'Ausente');

INSERT IGNORE INTO tool (tool_name)
VALUES ('Botiquín'),
       ('Extintor'),
       ('Equipo de Levante'),
       ('Herramientas'),
       ('Triángulos'),
       ('Chaqueta Reflectante'),
       ('Rueda de Repuesto');

INSERT INTO car_brand (brand_name)
VALUES ('Toyota');
SET @brand_id = LAST_INSERT_ID();

INSERT INTO car_model (brand_id, model_name, model_type, model_year)
VALUES (@brand_id, 'Corolla', 'Sedan', 2020);
SET @model_id = LAST_INSERT_ID();

INSERT INTO car (model_id, license_plate, VIN)
VALUES (@model_id, 'ABC123', '1HGCM82633A004352');
SET @car_id = LAST_INSERT_ID();

INSERT INTO client_info (first_name, rut, last_name, email_address, address, cellphone_number)
VALUES ('Juan','21222333-5' ,'Pérez', 'juan.perez@example.com', 'Av. Siempre Viva 123', '09912345678');
SET @client_id = LAST_INSERT_ID();

INSERT INTO mechanic_info (name, rut)
VALUES ('Matias Fernandez', '21333888-2');
SET @client_id = LAST_INSERT_ID();

INSERT INTO image (alt, path)
VALUES ('ABS', 'icons/abs.svg'),
       ('Airbag', 'icons/airbag-warning.svg'),
       ('Batería / alternador', 'icons/battery-light.svg'),
       ('Check engine (motor)', 'icons/check-engine-light.svg'),
       ('Temperatura refrigerante', 'icons/coolant-temperature.svg'),
       ('Filtro de partículas / emisiones (diésel)', 'icons/diesel-particulate-filter-warning.svg'),
       ('Puerta abierta', 'icons/door-ajar-warning-light.svg'),
       ('Sistema de tracción (ESP/ESC)', 'icons/electronic-stability-control-warning-light.svg'),
       ('Intermitentes', 'icons/flashing-warning-light.svg'),
       ('Freno de mano / freno', 'icons/hand-brake-warning.svg'),
       ('Luces altas', 'icons/high-beam-warning-light.svg'),
       ('Capó abierto', 'icons/hood-poppet-warning-light.svg'),
       ('Luces bajas', 'icons/low-beam-warning-light.svg'),
       ('Combustible bajo', 'icons/low-fuel-warning-light.svg'),
       ('Aceite (presión de aceite)', 'icons/oil-light.svg'),
       ('Dirección asistida', 'icons/power-steering-warning-light.svg'),
       ('Cinturón de seguridad', 'icons/seat-belt-warning.svg'),
       ('Presión neumáticos (TPMS)', 'icons/TPMS_warning-light.svg'),
       ('Maletero abierto', 'icons/trunk-open-warning-light.svg');
