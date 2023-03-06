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

CREATE TABLE datos_nevera (
    id_dato            INTEGER PRIMARY KEY AUTOINCREMENT
                          UNIQUE
                          NOT NULL,
    temperatura   INTEGER,
    humedad       INTEGER,
    estado        TEXT,
    id_user       INTEGER,
    CONSTRAINT FK_userId FOREIGN KEY (id_user)
    REFERENCES users(id)
);

INSERT INTO datos_nevera (temperatura, humedad, estado, id_user) VALUES (-3, 22, 'abierta', 0);
INSERT INTO datos_nevera (temperatura, humedad, estado, id_user) VALUES (-5, 25, 'cerrado', 1)

CREATE TABLE lista_productos (
    id            INTEGER PRIMARY KEY AUTOINCREMENT
                          UNIQUE
                          NOT NULL,
    name          TEXT,
    cantidad      TEXT,
    id_user       INTEGER,
    CONSTRAINT FK_userId FOREIGN KEY (id_user)
    REFERENCES users(id)
);

INSERT INTO lista_productos (name, cantidad, id_user) VALUES ('Banana', '1 kilo', 0);
INSERT INTO lista_productos (name, cantidad, id_user) VALUES ('Potato', '6 kilos', 0);

INSERT INTO lista_productos (name, cantidad, id_user) VALUES ('Pollo', '2 kilos', 1);
INSERT INTO lista_productos (name, cantidad, id_user) VALUES ('Agua', '1 litro', 1);

CREATE TABLE lista_compras (
    id            INTEGER PRIMARY KEY AUTOINCREMENT
                          UNIQUE
                          NOT NULL,
    name          TEXT,
    cantidad      TEXT,
    id_user       INTEGER,
    CONSTRAINT FK_userId FOREIGN KEY (id_user)
    REFERENCES users(id)
);

INSERT INTO lista_compras (name, cantidad, id_user) VALUES ('Arroz', '3 kilos', 0);
INSERT INTO lista_compras (name, cantidad, id_user) VALUES ('Agua', '3 litros', 0);
INSERT INTO lista_compras (name, cantidad, id_user) VALUES ('Pepsi', '1 litro', 0);

INSERT INTO lista_compras (name, cantidad, id_user) VALUES ('Manzana', '2 kilos', 1);
INSERT INTO lista_compras (name, cantidad, id_user) VALUES ('Agua mineral', '2 litros', 1);