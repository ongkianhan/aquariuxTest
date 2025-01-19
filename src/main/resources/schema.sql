CREATE TABLE users (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    user_id BIGINT NOT NULL,         -- User ID
    txn_id BIGINT NOT NULL,          -- Transaction ID
    ticker VARCHAR(255) NOT NULL,    -- Stock Ticker Symbol
    txn_time TIMESTAMP NOT NULL,     -- Transaction Time
    txn_volume DECIMAL(19,2) NOT NULL, -- Transaction Volume
    txn_price DECIMAL(19,2) NOT NULL, -- Transaction Price
    total_amt_txn DECIMAL(19,2) NOT NULL, -- Total Amount Transacted
    txn_type CHAR(1) NOT NULL,       -- Transaction Type: 'B' (Buy) or 'S' (Sell)
    PRIMARY KEY (user_id, txn_id)    -- Composite primary key
);


CREATE TABLE wallet (
    wallet_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL
);


CREATE TABLE wallet_curr (
    id BIGINT AUTO_INCREMENT,
    ticker VARCHAR(255),
    wallet_id BIGINT,
    volume DECIMAL(19, 4),
    PRIMARY KEY (id, ticker),
    CONSTRAINT fk_wallet FOREIGN KEY (wallet_id) REFERENCES wallet(wallet_id)
);


CREATE TABLE ticker (
    symbol VARCHAR(255) PRIMARY KEY,
    bidPrice DECIMAL(19, 4),
    bidQty DECIMAL(19, 4),
    askPrice DECIMAL(19, 4),
    askQty DECIMAL(19, 4)
);