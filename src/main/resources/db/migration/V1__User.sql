Create TABLE UserInfo (
    uid UUID Not NULL PRIMARY KEY,
    access_time TIMESTAMPTZ NULL,
    create_time TIMESTAMPTZ NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    nickname VARCHAR(200) NOT NULL UNIQUE,
    signature VARCHAR(600) NULL,
    password VARCHAR(500) NOT NULL,
    role VARCHAR(100) NOT NULL,
    avatar_key VARCHAR(900),
    background_key VARCHAR(900),
    is_account_non_expired BOOLEAN,
    is_account_non_locked BOOLEAN,
    is_credentials_non_expired BOOLEAN,
    is_enabled BOOLEAN
);
