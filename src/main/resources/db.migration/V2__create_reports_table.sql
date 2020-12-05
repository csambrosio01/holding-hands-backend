CREATE TABLE reports
(
    user_reporter   INT       NOT NULL,
    user_reported   INT       NOT NULL,
    message      VARCHAR      NOT NULL,
    PRIMARY KEY (user_reporter, user_reported),
    FOREIGN KEY (user_reporter) REFERENCES users(user_id),
    FOREIGN KEY (user_reported) REFERENCES users(user_id)
);
