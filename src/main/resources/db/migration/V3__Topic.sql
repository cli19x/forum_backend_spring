Create TABLE Topic (
    pid SERIAL PRIMARY KEY,
    uid UUID Not NULL,
    mid INT NOT NULL,
    create_time VARCHAR(100) NOT NULL,
    nickname VARCHAR(200) NOT NULL,
    post_title VARCHAR(400),
    post_data VARCHAR(800) NOT NULL,
    comment_count INT NOT NULL,
    deal_date VARCHAR(100) NOT NULL,
    week INT NOT NULL,
    level_count INT NOT NULL
);

