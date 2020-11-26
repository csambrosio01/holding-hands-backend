CREATE TABLE users
(
    id         SERIAL       NOT NULL,
    name       VARCHAR(255) NOT NULL UNIQUE,
    email      VARCHAR      NOT NULL UNIQUE,
    password   VARCHAR      NOT NULL UNIQUE,
    phone      VARCHAR(13)  NOT NULL UNIQUE,
    profession VARCHAR      NOT NULL UNIQUE,
    is_helper  BOOLEAN      NOT NULL UNIQUE,
    help_types VARCHAR,
    gender     VARCHAR      NOT NULL UNIQUE,
    image_id   VARCHAR,
    rating     FLOAT        NOT NULL UNIQUE,
    address_id SERIAL       NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES addresses (address_id)
);
