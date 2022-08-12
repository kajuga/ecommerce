

CREATE TABLE user_role
(
    id BIGINT(20)  NOT NULL AUTO_INCREMENT,
    role_name      VARCHAR(55) CHARACTER SET utf8 NOT NULL,
    description VARCHAR(255) CHARACTER SET utf8 DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000;

create table categories
(
    id int auto_increment primary key,
    category_name varchar(255) null,
    description varchar(255) null,
    image_url varchar(255) null
);

create table products
(
    id int auto_increment primary key,
    description varchar(255) null,
    imageurl varchar(255) null,
    name varchar(255) null,
    price double not null,
    category_id int not null,
    foreign key (category_id) references categories (id)
);


create table user
(
    id int auto_increment primary key,
    first_name varchar(255) null,
    last_name varchar(255) null,
    email varchar(255) null,
    role_id BIGINT(20) DEFAULT NULL,
    KEY FK_user_role_id (role_id),
    CONSTRAINT FK_user_role_id FOREIGN KEY (role_id) REFERENCES user_role (id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 70000
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;



create table orders
(
    id int auto_increment primary key,
    created_date datetime(6)  null,
    session_id varchar(255) null,
    total_price double null,
    user_id int null,
    foreign key (user_id) references user (id)
);


create table orderitems
(
    id int auto_increment primary key,
    created_date datetime(6) null,
    price double null,
    quantity int null,
    order_id int null,
    product_id int null,
    foreign key (order_id) references orders (id),
    foreign key (product_id) references products (id)
);

create table cart
(
    id int auto_increment primary key,
    created_date datetime(6) null,
    quantity int not null,
    product_id int not null,
    user_id int not null,
    foreign key (user_id) references user (id),
    foreign key (product_id) references products (id)
);

create table tokens
(
    id int auto_increment primary key,
    created_date datetime(6)  null,
    token varchar(255) null,
    user_id int not null,
    foreign key (user_id) references user (id)
);

create table wishlist
(
    id int auto_increment primary key,
    created_date datetime(6) null,
    product_id int null,
    user_id int not null,
    foreign key (product_id) references products (id),
    foreign key (user_id) references user (id)
);