INSERT INTO users (user_id, username, password) VALUES (1, 'john_doe', 'password123');
INSERT INTO users (user_id, username, password) VALUES (2, 'jane_doe', 'password456');


INSERT INTO transactions (user_id, txn_id, ticker, txn_time, txn_volume, txn_price, total_amt_txn, txn_type) VALUES
(1, 1001, 'ETHUSDT', '2025-01-01 10:00:00', 50, 150.25, 7512.50, 'B'),
(1, 1002, 'ETHUSDT', '2025-01-01 10:30:00', 20, 2800.75, 56015.00, 'S'),
(1, 2001, 'BTCUSDT', '2025-01-02 09:45:00', 100, 310.10, 31010.00, 'B');


INSERT INTO wallet (user_id) VALUES (1);
INSERT INTO wallet (user_id) VALUES (2);

INSERT INTO wallet_curr (ticker, wallet_id, volume)
VALUES ('BTCUSDT', 1, 1.5),
       ('ETHUSDT', 1, 10.0),
       ('BTCUSDT', 2, 0.5),
       ('ETHUSDT', 2, 7.5),
       ('USDT', 1, 50000.00);


INSERT INTO ticker (symbol, bidPrice, bidQty, askPrice, askQty) VALUES
('BTCUSD', 45000.55, 0.25, 45010.00, 0.50),
('ETHUSD', 3000.25, 1.0, 3005.75, 1.5);