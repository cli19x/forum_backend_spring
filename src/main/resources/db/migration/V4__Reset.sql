Create TABLE Reset (
    id SERIAL PRIMARY KEY,
    email VARCHAR(200) NOT NULL,
    v_code int NOT NULL,
    v_code_create_time TIMESTAMPTZ NOT NULL
);

