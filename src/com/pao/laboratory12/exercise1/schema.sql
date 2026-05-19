DROP TABLE IF EXISTS loan;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS reader;
DROP TABLE IF EXISTS author;

CREATE TABLE author (
                        id   BIGINT AUTO_INCREMENT PRIMARY KEY,   -- MySQL: înlocuiește linia de deasupra
                        name    VARCHAR(200) NOT NULL,
                        country VARCHAR(100)
);

CREATE TABLE book (
                      id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title     VARCHAR(300) NOT NULL,
                      author_id INTEGER NOT NULL,
                      available INTEGER NOT NULL DEFAULT 1,        -- 1 = disponibil, 0 = împrumutat
                      FOREIGN KEY (author_id) REFERENCES author(id)  -- FK #1
);

CREATE TABLE reader (
                        id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name  VARCHAR(200) NOT NULL,
                        email VARCHAR(200)
);

CREATE TABLE loan (
                      id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                      book_id     INTEGER NOT NULL,
                      reader_id   INTEGER NOT NULL,
                      loan_date   VARCHAR(20) NOT NULL,             -- ISO format: YYYY-MM-DD
                      return_date VARCHAR(20),                      -- NULL = împrumut activ
                      FOREIGN KEY (book_id)   REFERENCES book(id),  -- FK #2
                      FOREIGN KEY (reader_id) REFERENCES reader(id) -- FK #3 (bonus față de minim)
);