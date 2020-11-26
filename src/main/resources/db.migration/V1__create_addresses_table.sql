CREATE TABLE addresses
(
    address_id   SERIAL  NOT NULL,
    address      VARCHAR NOT NULL,
    complement   VARCHAR,
    neighborhood VARCHAR NOT NULL,
    city         VARCHAR NOT NULL,
    state        VARCHAR NOT NULL,
    zip_code     CHAR(8) NOT NULL,
    country      VARCHAR NOT NULL,
    number       VARCHAR NOT NULL,
    PRIMARY KEY (address_id)
);
