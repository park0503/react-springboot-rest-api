CREATE TABLE products
(
    product_id BINARY(16) PRIMARY KEY ,
    product_name VARCHAR(20) not null ,
    category varchar(50) not null ,
    price bigint not null ,
    description varchar(500) default null,
    created_at datetime not null ,
    updated_at datetime default null
);

create table orders
(
    order_id binary(16) primary key,
    email varchar(50) not null ,
    address varchar(200) not null ,
    postcode varchar(200) not null ,
    order_status varchar(50) not null ,
    created_at datetime not null ,
    updated_at datetime default null
);

create table order_items
(
    seq bigint not null primary key auto_increment,
    order_id binary(16) NOT NULL,
    product_id binary(16) NOT NULL ,
    category varchar(50) not null ,
    price bigint not null ,
    quantity int not null ,
    created_at datetime not null ,
    updated_at datetime not null ,
    index(order_id),
    constraint fk_order_items_to_order foreign key (order_id) references orders (order_id) on delete cascade ,
    constraint fk_order_items_to_product foreign key (product_id) references products (product_id)
);