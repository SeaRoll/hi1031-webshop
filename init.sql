-- Use postgresql
-- Create a table if not exists an item table with (id, name, price, quantity, description, category) fields
create table if not exists items
(
    id          varchar(255) primary key,
    name        varchar(255) not null,
    price       integer      not null,
    quantity    integer      not null,
    description varchar(255) not null,
    category    varchar(255) not null
);

-- Create a user role enum (User, Staff, Admin) fields
create type user_role as enum ('USER', 'STAFF', 'ADMIN');

-- Create a table if not exists a user table with (id, username, password) fields
create table if not exists users
(
    id       varchar(255) primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    role     user_role    not null
);

create type order_status as enum ('PLACED', 'PACKAGING', 'SENT', 'RETURNED');

-- Create a table if not exists a order table with (id, user_id, status) fields
create table if not exists orders
(
    id      varchar(255) primary key,
    user_id varchar(255) not null,
    status  order_status not null,
    foreign key (user_id) references users (id) on delete cascade
);

-- Create a table if not exists a order_item table with (order_id, item_id, quantity) fields and order_id, item_id as a composite primary and foreign key
create table if not exists order_items
(
    order_id varchar(255) not null,
    item_id  varchar(255) not null,
    quantity integer      not null,
    primary key (order_id, item_id),
    foreign key (order_id) references orders (id) on delete cascade,
    foreign key (item_id) references items (id) on delete cascade
);

-- create an superadmin user
insert into users (id, username, password, role)
values ('random-admin-id', 'superadmin', 'superadmin', 'ADMIN');