CREATE TABLE match
(
    match_id      SERIAL  NOT NULL,
    user_sent     INT     NOT NULL,
    user_received INT     NOT NULL,
    status        VARCHAR NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (match_id),
    FOREIGN KEY (user_sent) REFERENCES users (user_id),
    FOREIGN KEY (user_received) REFERENCES users (user_id)
);
