CREATE TABLE IF NOT EXISTS crypto_currencies (
  symbol VARCHAR PRIMARY KEY,
  name VARCHAR,
  slug VARCHAR
);

INSERT INTO crypto_currencies (symbol, name, slug)
VALUES ('BTC', 'Bitcoin', 'bitcoin');