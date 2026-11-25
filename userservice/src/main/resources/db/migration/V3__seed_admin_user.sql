INSERT INTO users (
    role_id,
    name,
    email,
    phone_no,
    dob,
    username,
    password,
    is_soft_deleted,
    is_banned,
) VALUES (
    (SELECT id FROM roles WHERE role = 'ROLE_ADMIN'),
    'Platform Admin',
     mygrocerymart@gmail.com,
    '+91 123456789',
     '1999-06-2000',
    'platform-admin',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
);