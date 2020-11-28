CREATE TABLE users
(
    user_id    SERIAL       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR      NOT NULL UNIQUE,
    password   VARCHAR      NOT NULL,
    phone      VARCHAR(13)  NOT NULL UNIQUE,
    profession VARCHAR      NOT NULL,
    is_helper  BOOLEAN      NOT NULL,
    help_types VARCHAR,
    gender     VARCHAR      NOT NULL,
    image_id   VARCHAR,
    rating     FLOAT        NOT NULL,
    latitude   DOUBLE,
    longitude  DOUBLE,
    address_id SERIAL       NOT NULL,
    PRIMARY KEY (user_id),
    FOREIGN KEY (address_id) REFERENCES addresses (address_id)
);
