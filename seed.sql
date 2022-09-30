-- create some clothes items
insert into items (name, price, quantity, description, category)
values ('T-shirt', 100, 10, 'A T-shirt', 'clothes');
insert into items (name, price, quantity, description, category)
values ('Pants', 200, 10, 'A Pants', 'clothes');
insert into items (name, price, quantity, description, category)
values ('Shoes', 300, 10, 'A Shoes', 'clothes');

-- create some electronic items
insert into items (name, price, quantity, description, category)
values ('Laptop', 1000, 10, 'A Laptop', 'electronic');
insert into items (name, price, quantity, description, category)
values ('Mobile', 2000, 10, 'A Mobile', 'electronic');
insert into items (name, price, quantity, description, category)
values ('Tablet', 3000, 10, 'A Tablet', 'electronic');

-- create an superadmin user
insert into users (username, password)
values ('superadmin', 'superadmin');