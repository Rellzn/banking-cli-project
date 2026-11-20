CREATE TABLE IF NOT EXISTS users (
            user_id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            first_name VARCHAR(60) NOT NULL,
            last_name  VARCHAR(60),
            user_name  VARCHAR(60) NOT NULL UNIQUE,
            password   VARCHAR     NOT NULL
        );
 CREATE TABLE IF NOT EXISTS employees (
            employee_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            first_name  VARCHAR(60) NOT NULL,
            last_name   VARCHAR(60),
            user_name   VARCHAR(60) NOT NULL UNIQUE,
            password    VARCHAR     NOT NULL
        );
CREATE TABLE IF NOT EXISTS accounts (
            account_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            balance    NUMERIC(19,4) NOT NULL DEFAULT 0 CHECK (balance >= 0),
            status     VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE'
                       CHECK (status IN ('ACTIVE','FROZEN','CLOSED')),
            created_at TIMESTAMPTZ   NOT NULL DEFAULT now(),
            updated_at TIMESTAMPTZ   NOT NULL DEFAULT now(),
            CONSTRAINT closed_requires_zero_balance CHECK (status <> 'CLOSED' OR balance = 0)
        );
CREATE TABLE IF NOT EXISTS user_accounts_join (
            user_id    BIGINT NOT NULL REFERENCES users(user_id)       ON DELETE CASCADE,
            account_id BIGINT NOT NULL REFERENCES accounts(account_id) ON DELETE CASCADE,
            PRIMARY KEY (user_id, account_id)
        );
CREATE TABLE IF NOT EXISTS transactions (
            transaction_id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
            account_id               BIGINT        NOT NULL REFERENCES accounts(account_id),
            transaction_type         VARCHAR(20)   NOT NULL
                                     CHECK (transaction_type IN ('DEPOSIT','WITHDRAWAL','TRANSFER','SERVICES')),
            quantity                 NUMERIC(19,4) NOT NULL CHECK (quantity > 0),
            related_account_id       BIGINT REFERENCES accounts(account_id),
            initiated_by_employee_id BIGINT REFERENCES employees(employee_id) ON DELETE SET NULL,
            created_at               TIMESTAMPTZ   NOT NULL DEFAULT now(),
            -- A transfer goes account_id -> related_account_id; nothing else has a related account.
            CONSTRAINT transfer_has_destination CHECK (
                (transaction_type = 'TRANSFER') = (related_account_id IS NOT NULL)),
            CONSTRAINT transfer_to_other_account CHECK (related_account_id IS DISTINCT FROM account_id)
        );