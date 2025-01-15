DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);