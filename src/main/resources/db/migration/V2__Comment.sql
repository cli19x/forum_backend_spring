Create TABLE Comment (
    cid SERIAL PRIMARY KEY,
    uid UUID NOT NULL,
    target_uid UUID NOT NULL,
    pid INT NOT NULL,
    mid INT NOT NULL,
    comment_time TIMESTAMPTZ NOT NULL,
    nickname VARCHAR(200) NOT NULL,
    target_nickname VARCHAR(200) NOT NULL,
    comment VARCHAR(800) NOT NULL,
    post_title VARCHAR(800) NOT NULL
);

