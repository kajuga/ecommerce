
-- user roles
INSERT INTO userservice_bd.user_role (role_name, description)
VALUES  ('ADMINISTRATOR','Department Manager - administrator'),
        ('MANAGER', 'Department Chief'),
        ('SPECIALIST', 'Department Manager'),
        ('EXTERNAL_USER', 'External user');

-- users
INSERT INTO userservice_bd.user (first_name, last_name, email, role_id, password)
VALUES  ('User', 'System', 'admin@mail.ru', 1000, '0D05A319E82A1A461079CFEC354A3E00');
INSERT INTO userservice_bd.user (first_name, last_name, email, role_id)
VALUES  ('Ivan', 'Ivanov', 'ivanov@mail.ru', 1001),
        ('Petr', 'Petrov', 'petrov@mail.ru', 1002),
        ('Sergey', 'Sergeev', 'sergeev@mail.ru', 1003);


