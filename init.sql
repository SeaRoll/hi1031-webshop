-- Use postgresql
-- Create a table if not exists an item table with (id, name, price, quantity, description, category) fields
create table if not exists items (
    id serial primary key,
    name varchar(255) not null,
    price integer not null,
    quantity integer not null,
    description varchar(255) not null,
    category varchar(255) not null
);

-- Create a table if not exists a user table with (id, username, password) fields
create table if not exists users (
    id serial primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

-- Create a table if not exists a order table with (id, user_id, status) fields
create table if not exists orders (
    id serial primary key,
    user_id integer not null,
    status varchar(255) not null
);

-- Create a table if not exists a order_item table with (order_id, item_id, quantity) fields and order_id, item_id as a composite primary and foreign key
create table if not exists order_items (
    order_id integer not null,
    item_id integer not null,
    quantity integer not null,
    primary key (order_id, item_id),
    foreign key (order_id) references orders(id) on delete cascade,
    foreign key (item_id) references items(id) on delete cascade
);

-- create some clothes items
insert into items (name, price, quantity, description, category) values ('T-shirt', 100, 10, 'A T-shirt', 'clothes');
insert into items (name, price, quantity, description, category) values ('Pants', 200, 10, 'A Pants', 'clothes');
insert into items (name, price, quantity, description, category) values ('Shoes', 300, 10, 'A Shoes', 'clothes');

-- create some electronic items
insert into items (name, price, quantity, description, category) values ('Laptop', 1000, 10, 'A Laptop', 'electronic');
insert into items (name, price, quantity, description, category) values ('Mobile', 2000, 10, 'A Mobile', 'electronic');
insert into items (name, price, quantity, description, category) values ('Tablet', 3000, 10, 'A Tablet', 'electronic');

-- create an superadmin user
insert into users (username, password) values ('superadmin', 'superadmin');

-- some old table, ignore this
create table T_SOMETABLE
(
    id int
);

insert into T_SOMETABLE(id)
VALUES (1);