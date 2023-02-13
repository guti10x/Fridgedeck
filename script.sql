CREATE TABLE IF NOT EXISTS users (
    id            INTEGER PRIMARY KEY AUTOINCREMENT
                          UNIQUE
                          NOT NULL DEFAULT (1),
    type          TEXT,					  
    username      TEXT,
    email         TEXT    UNIQUE,
    password      TEXT,
    name_surname  TEXT,
    fridge_adress TEXT
);
INSERT INTO users (id, type, username, email, password, name_surname, fridge_adress) VALUES (0, 'user', 'Pablo', 'pablov@gmail.com', 'pablopasswd', 'Pablo Garcia', 'Madrid, calle Velazquez 54');
INSERT INTO users (id, type, username, email, password, name_surname, fridge_adress) VALUES (1, 'user', 'leon', 'l.karagishev@gmail.com', 'leonpasswd', 'Leon Karagishev', 'Calle 45, 2B');
INSERT INTO users (id, type, username, email, password, name_surname, fridge_adress) VALUES (2, 'tecnico', 'Alba', 'alba@gmail.com', 'alba', 'Alba Garcia', '-');
INSERT INTO users (id, type, username, email, password, name_surname, fridge_adress) VALUES (3, 'proveedor', 'Ivan', 'ivan@gmail.com', 'ivan', 'Ivan Garcia', '-');