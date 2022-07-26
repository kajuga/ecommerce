create table categories
(
    id            int auto_increment
        primary key,
    category_name varchar(255) null,
    description   varchar(255) null,
    image_url     varchar(255) null
);

create table products
(
    id          int auto_increment
        primary key,
    description varchar(255) null,
    imageurl    varchar(255) null,
    name        varchar(255) null,
    price       double       not null,
    category_id int          not null,
    foreign key (category_id) references categories (id)
);


create table users
(
    id         int auto_increment
        primary key,
    email      varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(255) null
);

create table orders
(
    id           int auto_increment
        primary key,
    created_date datetime(6)  null,
    session_id   varchar(255) null,
    total_price  double       null,
    user_id      int          null,
    foreign key (user_id) references users (id)
);


create table orderitems
(
    id           int auto_increment
        primary key,
    created_date datetime(6) null,
    price        double      null,
    quantity     int         null,
    order_id     int         null,
    product_id   int         null,
    foreign key (order_id) references orders (id),
    foreign key (product_id) references products (id)
);

create table cart
(
    id           int auto_increment
        primary key,
    created_date datetime(6) null,
    quantity     int         not null,
    product_id   int         not null,
    user_id      int         not null,
    foreign key (user_id) references users (id),
    foreign key (product_id) references products (id)
);

create table tokens
(
    id           int auto_increment
        primary key,
    created_date datetime(6)  null,
    token        varchar(255) null,
    user_id      int          not null,
    foreign key (user_id) references users (id)
);

create table wishlist
(
    id           int auto_increment
        primary key,
    created_date datetime(6) null,
    product_id   int         null,
    user_id      int         not null,
    foreign key (product_id) references products (id),
    foreign key (user_id) references users (id)
);