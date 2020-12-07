CREATE TABLE ratings
(
    user_reviewer   INT          NOT NULL,
    user_rated      INT          NOT NULL,
    rating          INT          NOT NULL,
    PRIMARY KEY (user_reviewer, user_rated),
    FOREIGN KEY (user_reviewer) REFERENCES users(user_id),
    FOREIGN KEY (user_rated)    REFERENCES users(user_id)
);
