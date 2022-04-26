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