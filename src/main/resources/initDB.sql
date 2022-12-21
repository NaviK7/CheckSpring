CREATE TABLE IF NOT EXISTS product
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(200)     NOT NULL,
    price           DOUBLE PRECISION NOT NULL,
    discount_status BOOLEAN          NOT NULL
);

INSERT INTO product
VALUES (1, 'Капучино в кофейне', 4.45, true);
INSERT INTO product
VALUES (2, 'Молоко (1 литр)', 2, true);
INSERT INTO product
VALUES (3, 'Буханка свежего белого хлеба (500 г)', 1.51, false);
INSERT INTO product
VALUES (4, 'Белый рис (1 кг)', '3.55', false);
INSERT INTO product
VALUES (5, 'Куриные яйца (10 шт.)', 3.7, false);
INSERT INTO product
VALUES (6, 'Местный сыр (1 кг) ', 17, false);
INSERT INTO product
VALUES (7, 'Куриное филе (1 кг)', 11.5, false);
INSERT INTO product
VALUES (8, 'Говяжий окорок (1 кг)', 20, true);
INSERT INTO product
VALUES (9, 'Яблоки (1 кг)', 4, false);
INSERT INTO product
VALUES (10, 'Бананы (1 кг)', 5, false);
INSERT INTO product
VALUES (11, 'Апельсины (1 кг)', 5, false);
INSERT INTO product
VALUES (12, 'Помидоры (1 кг)', 7.7, false);
INSERT INTO product
VALUES (13, 'Картофель (1 кг)', 2, false);
INSERT INTO product
VALUES (14, 'Лук (1 кг)', 1.9, false);
INSERT INTO product
VALUES (15, 'Салат-латук (1 головка)', 4.3, false);
INSERT INTO product
VALUES (16, 'Вода (1,5 л)', 1.6, false);

CREATE TABLE IF NOT EXISTS discount_card
(
    id                 SERIAL PRIMARY KEY,
    name             INTEGER NOT NULL,
    discount_percentage INTEGER NOT NULL
);
INSERT INTO discount_card
VALUES (1, 1234, 3);
INSERT INTO discount_card
VALUES (2, 1235, 3);
INSERT INTO discount_card
VALUES (3, 1236, 3);
INSERT INTO discount_card
VALUES (4, 1237, 5);
INSERT INTO discount_card
VALUES (5, 1238, 5);