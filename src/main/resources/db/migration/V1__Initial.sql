CREATE TABLE "app_user" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(255) UNIQUE,
    birthday VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE "company" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    company_email VARCHAR(255) UNIQUE
);

CREATE TABLE "app_user_company" (
    id SERIAL PRIMARY KEY,
    app_user_id INT NOT NULL,
    company_id INT NOT NULL,

    CONSTRAINT fk_app_user
        FOREIGN KEY (app_user_id)
        REFERENCES "app_user"(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_company
        FOREIGN KEY (company_id)
        REFERENCES "company"(id)
        ON DELETE CASCADE
);




INSERT INTO "company" (name, company_email)
VALUES
    ('Woke', 'woke@company.com'),
    ('Stone', 'stone@company.com'),
    ('PicPay', 'picpay@company.com'),
    ('Nexxera', 'nexxera@company.com'),
    ('Nubank', 'nubank@company.com'),
    ('Linkedin', 'linkedin@company.com');

