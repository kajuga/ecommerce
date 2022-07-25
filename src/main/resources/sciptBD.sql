-- categories
INSERT INTO ecommerce_v2.categories (id, category_name, description, image_url) VALUES (1, 'Cars', 'Cars in our store', 'http://localhost:8080/pictures/category/cars.png');
INSERT INTO ecommerce_v2.categories (id, category_name, description, image_url) VALUES (2, 'Laptops', 'Laptops in our store', 'http://localhost:8080/pictures/category/laptops.png');
INSERT INTO ecommerce_v2.categories (id, category_name, description, image_url) VALUES (3, 'Phones', 'Phones in our store', 'http://localhost:8080/pictures/category/phones.png');
INSERT INTO ecommerce_v2.categories (id, category_name, description, image_url) VALUES (4, 'Shoes', 'Shoes in our store', 'http://localhost:8080/pictures/category/shoes.png');
INSERT INTO ecommerce_v2.categories (id, category_name, description, image_url) VALUES (5, 'Wear', 'Wear in our store', 'http://localhost:8080/pictures/category/wear.png');
INSERT INTO ecommerce_v2.categories (id, category_name, description, image_url) VALUES (6, 'Food', 'Food in our store', 'http://localhost:8080/pictures/category/food.png');

-- products

INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (4, 'Красный автомобиль', 'http://localhost:8080/pictures/product/car_1.png', 'Red vehicle', 1500, 1);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (5, 'Зеленый автомобиль', 'http://localhost:8080/pictures/product/car_2.png', 'Green vehicle', 3200, 1);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (6, 'Синий автомобиль', 'http://localhost:8080/pictures/product/car_3.png', 'Blue vehicle', 4500, 1);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (7, '15", i7, RAM 32', 'http://localhost:8080/pictures/product/laptop_1.png', 'DELL Inspirion 1720', 1700, 2);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (8, '15", i5, RAM 16', 'http://localhost:8080/pictures/product/laptop_2.png', 'Lenovo Thinkpad p51', 700, 2);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (9, '17", i9, RAM 63', 'http://localhost:8080/pictures/product/laptop_3.png', 'Sony Vaio', 1200, 2);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (10, '6,2, 6/64', 'http://localhost:8080/pictures/product/phone_1.jpg', 'Apple Iphone 12 Max', 300, 3);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (11, '6,5", 8/128', 'http://localhost:8080/pictures/product/phone_2.jpg', 'Huawei Mate 20', 250, 3);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (12, '7,2", 4/256', 'http://localhost:8080/pictures/product/phone_3.jpg', 'Honor View 30', 100, 3);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (13, '39', 'http://localhost:8080/pictures/product/shoes_1.jpg', 'Noname shoes', 50, 4);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (14, '42', 'http://localhost:8080/pictures/product/shoes_2.jpg', 'Nike sneakers', 35, 4);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (15, '43', 'http://localhost:8080/pictures/product/shoes_3.jpg', 'Converse ALL Star', 14, 4);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (16, 'XS', 'http://localhost:8080/pictures/product/wear_1.jpg', 'Noname polo', 26, 5);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (17, 'XXL', 'http://localhost:8080/pictures/product/wear_2.jpg', 'T-shirt', 200, 5);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (18, 'Blue, 34/36', 'http://localhost:8080/pictures/product/wear_3.jpg', 'Montana Jeans', 120, 5);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (19, 'Grilled turkey from turkey', 'http://localhost:8080/pictures/product/food_1.png', 'Turkey', 10, 6);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (20, '3/4 pound, chicken, catchup', 'http://localhost:8080/pictures/product/food_2.png', 'Le Big Mac', 3, 6);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (21, '3,5 kg', 'http://localhost:8080/pictures/product/food_3.png', 'Green melone', 7, 6);
INSERT INTO ecommerce_v2.products (id, description, imageurl, name, price, category_id) VALUES (22, '4,3", 3 RAM', 'http://localhost:8080/pictures/product/phone_3.jpg', 'Apple Iphone mini', 555, 3);

-- users

INSERT INTO ecommerce_v2.users (id, email, first_name, last_name, password) VALUES (1, 'sashok@mail.ru', 'Aleksandr', 'Fedorov', '698D51A19D8A121CE581499D7B701668');

-- wishlist

INSERT INTO ecommerce_v2.wishlist (id, created_date, product_id, user_id) VALUES (1, '2022-07-12 18:00:15.480000', 20, 1);