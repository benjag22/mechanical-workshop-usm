USE usm_mechanical_workshop;

drop table if exists work_order_has_mechanics;
drop table if exists car_picture;
drop table if exists picture;
drop table if exists work_order_has_dashboard_light;
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

CREATE TABLE IF NOT EXISTS client_info
(
    id               int AUTO_INCREMENT primary key,
    first_name       varchar(64)  not null check (first_name != ''),
    last_name        varchar(64),
    email_address    varchar(255) not null check ( '' != email_address),
    address          varchar(128) not null check (address != ''),
    cellphone_number varchar(12)  not null check (cellphone_number != '')
    );

CREATE TABLE IF NOT EXISTS mechanic_info
(
    id                  int AUTO_INCREMENT primary key,
    first_name          varchar(64) not null check ( '' != first_name),
    last_name           varchar(64),
    registration_number varchar(16)
    );

CREATE TABLE IF NOT EXISTS car_brand
(
    id         int AUTO_INCREMENT primary key,
    brand_name varchar(32) unique not null check (brand_name !='')
    );

CREATE TABLE IF NOT EXISTS car_model
(
    id         int AUTO_INCREMENT primary key,
    brand_id   int         not null,
    model_name varchar(78) not null check (model_name !=''),
    model_type varchar(64),
    model_year int,
    foreign key (brand_id) references car_brand (id)
    );

CREATE TABLE IF NOT EXISTS car
(
    id            int AUTO_INCREMENT primary key,
    model_id      int                not null,
    license_plate varchar(6) unique  not null,
    VIN           varchar(17) unique not null check (VIN !=''),
    foreign key (model_id) references car_model (id)
    );

CREATE TABLE IF NOT EXISTS record
(
    id               int AUTO_INCREMENT primary key,
    reason           varchar(255) not null,
    car_id           int          not null,
    client_info_id   int          not null,
    check (reason != ''),
    foreign key (car_id) references car (id),
    foreign key (client_info_id) references client_info (id)
    );

CREATE TABLE IF NOT EXISTS record_state
(
    id         int AUTO_INCREMENT primary key,
    record_id  int not null,
    entry_time DATETIME NOT NULL,
    mileage    int not null,
    foreign key (record_id) references record (id)
    );

CREATE TABLE IF NOT EXISTS entry_state
(
    id              int AUTO_INCREMENT primary key,
    record_state_id int not null,
    valuables       varchar(255),
    gas_level       enum ('FULL','THREE_QUARTERS','HALF','ONE_QUARTER','LOW'),
    foreign key (record_state_id) references record_state (id)
    );

CREATE TABLE IF NOT EXISTS out_state
(
    id                int AUTO_INCREMENT primary key,
    record_state_id   int     not null,
    vehicle_diagnosis varchar(255),
    rating            tinyint not null,
    foreign key (record_state_id) references record_state (id)
    );

CREATE TABLE IF NOT EXISTS tool
(
    id        int AUTO_INCREMENT primary key,
    tool_name varchar(32) unique not null
    );

CREATE TABLE IF NOT EXISTS entry_state_has_tool
(
    id             int AUTO_INCREMENT primary key,
    tool_id        int not null,
    entry_state_id int not null,
    foreign key (tool_id) references tool (id),
    foreign key (entry_state_id) references entry_state (id)
    );

CREATE TABLE IF NOT EXISTS exterior_condition
(
    id                      int auto_increment primary key,
    part_name            varchar(64) not null check (part_name != ''),
    part_condition_state varchar(128),
    unique uk_part_name_condition (part_name, part_condition_state)
    );

CREATE TABLE IF NOT EXISTS interior_condition
(
    id                      int auto_increment primary key,
    part_name            varchar(64) not null check (part_name != ''),
    part_condition_state varchar(128),
    unique uk_part_name_condition (part_name, part_condition_state)
    );

CREATE TABLE IF NOT EXISTS electrical_system_condition
(
    id                      int auto_increment primary key,
    part_name            varchar(64) not null check (part_name != ''),
    part_condition_state varchar(128),
    unique uk_part_name_condition (part_name, part_condition_state)
    );

CREATE TABLE IF NOT EXISTS entry_state_consider_condition
(
    id int AUTO_INCREMENT primary key,
    exterior_condition_id int default null,
    interior_condition_id int default null,
    electrical_system_condition_id int default null,
    entry_state_id          int not null,
    foreign key (exterior_condition_id) references exterior_condition (id),
    foreign key (interior_condition_id) references interior_condition (id),
    foreign key (electrical_system_condition_id) references electrical_system_condition (id),
    foreign key (entry_state_id) references entry_state (id)
    );

CREATE TABLE IF NOT EXISTS work_order
(
    id             int auto_increment primary key,
    record_id      int not null,
    estimated_date date not null,
    estimated_time time not null,
    signature_path varchar(512) default null,
    foreign key (record_id) references record (id)
    );

CREATE TABLE IF NOT EXISTS work_service
(
    id           int auto_increment primary key,
    service_name varchar(32) unique not null check (service_name != ''),
    estimated_time time not null
    );

CREATE TABLE IF NOT EXISTS work_order_realized_service
(
    id              int auto_increment primary key,
    work_order_id   int  not null,
    work_service_id int  not null,
    finalized       bool not null,
    foreign key (work_order_id) references work_order (id),
    foreign key (work_service_id) references work_service (id)
    );

CREATE TABLE IF NOT EXISTS dashboard_light
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    alt VARCHAR(255) NOT NULL,
    path VARCHAR(512) NOT NULL
    );

CREATE TABLE IF NOT EXISTS work_order_has_dashboard_light
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    work_order_id INT NOT NULL,
    dashboard_light_id INT NOT NULL,
    present BOOLEAN NOT NULL DEFAULT FALSE,
    operates BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_wodl_work_order FOREIGN KEY (work_order_id) REFERENCES work_order(id),
    CONSTRAINT fk_wodl_dashboard_light FOREIGN KEY (dashboard_light_id) REFERENCES dashboard_light(id),
    UNIQUE KEY uk_wodl_pair (work_order_id, dashboard_light_id)
    );

CREATE TABLE IF NOT EXISTS picture (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       alt VARCHAR(255) NOT NULL,
    path VARCHAR(512) NOT NULL
    );

CREATE TABLE IF NOT EXISTS car_picture (
    id INT AUTO_INCREMENT PRIMARY KEY,
    work_order_id INT NOT NULL,
    path VARCHAR(512) NOT NULL,
    CONSTRAINT fk_car_picture_work_order FOREIGN KEY (work_order_id) REFERENCES work_order(id)
    );

CREATE TABLE IF NOT EXISTS work_order_has_mechanics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    work_order_id INT NOT NULL,
    mechanic_info_id INT NOT NULL,
    CONSTRAINT fk_womh_work_order FOREIGN KEY (work_order_id) REFERENCES work_order(id),
    CONSTRAINT fk_womh_mechanic_info FOREIGN KEY (mechanic_info_id) REFERENCES mechanic_info(id),
    UNIQUE KEY uk_womh_pair (work_order_id, mechanic_info_id)
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

insert into exterior_condition (part_name,  part_condition_state) values
                                                                      ('Luneta Delantera', 'Malo'),
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

insert into electrical_system_condition (part_name, part_condition_state) values
                                                                              ('Control de Niveles Motor', 'Malo'),
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

INSERT IGNORE INTO tool (tool_name) VALUES
  ('Botiquín'),
  ('Extintor'),
  ('Equipo de Levante'),
  ('Herramientas'),
  ('Triángulos'),
  ('Chaqueta Reflectante'),
  ('Rueda de Repuesto');

INSERT INTO car_brand (brand_name) VALUES ('Toyota');
SET @brand_id = LAST_INSERT_ID();

INSERT INTO car_model (brand_id, model_name, model_type, model_year)
VALUES (@brand_id, 'Corolla', 'Sedan', 2020);
SET @model_id = LAST_INSERT_ID();

INSERT INTO car (model_id, license_plate, VIN)
VALUES (@model_id, 'ABC123', '1HGCM82633A004352');
SET @car_id = LAST_INSERT_ID();

INSERT INTO client_info (first_name, last_name, email_address, address, cellphone_number)
VALUES ('Juan', 'Pérez', 'juan.perez@example.com', 'Av. Siempre Viva 123', '09912345678');
SET @client_id = LAST_INSERT_ID();

INSERT INTO dashboard_light (alt, path) VALUES
                                            ('ABS', 'http://localhost:8081/images/dashboard/abs.svg'),
                                            ('Airbag', 'http://localhost:/images/dashboard/airbag-warning.svg'),
                                            ('Batería / alternador', 'http://localhost:8081/images/dashboard/battery-light.svg'),
                                            ('Check engine (motor)', 'http://localhost:8081/images/dashboard/check-engine-light.svg'),
                                            ('Temperatura refrigerante', 'http://localhost:8081/images/dashboard/coolant-temperature.svg'),
                                            ('Filtro de partículas / emisiones (diésel)', 'http://localhost:8081/images/dashboard/diesel-particulate-filter-warning.svg'),
                                            ('Puerta abierta', 'http://localhost:8081/images/dashboard/door-ajar-warning-light.svg'),
                                            ('Sistema de tracción (ESP/ESC)', 'http://localhost:8081/images/dashboard/electronic-stability-control-warning-light.svg'),
                                            ('Intermitentes', 'http://localhost:8081/images/dashboard/flashing-warning-light.svg'),
                                            ('Freno de mano / freno', 'http://localhost:8081/images/dashboard/hand-brake-warning.svg'),
                                            ('Luces altas', 'http://localhost:8081/images/dashboard/high-beam-warning-light.svg'),
                                            ('Capó abierto', 'http://localhost:8081/images/dashboard/hood-poppet-warning-light.svg'),
                                            ('Luces bajas', 'http://localhost:8081/images/dashboard/low-beam-warning-light.svg'),
                                            ('Combustible bajo', 'http://localhost:8081/images/dashboard/low-fuel-warning-light.svg'),
                                            ('Aceite (presión de aceite)', 'http://localhost:8081/images/dashboard/oil-light.svg'),
                                            ('Dirección asistida', 'http://localhost:8081/images/dashboard/power-steering-warning-light.svg'),
                                            ('Cinturón de seguridad', 'http://localhost:8081/images/dashboard/seat-belt-warning.svg'),
                                            ('Presión neumáticos (TPMS)', 'http://localhost:8081/images/dashboard/TPMS_warning-light.svg'),
                                            ('Maletero abierto', 'http://localhost:8081/images;/dashboard/TPMS_warning-light.svg');
