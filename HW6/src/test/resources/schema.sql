CREATE SCHEMA IF NOT EXISTS otus_spring;

-- Author table creation
CREATE TABLE IF NOT EXISTS otus_spring.Author (
  Id   INT AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(100) NOT NULL
);

-- Genre table creation
CREATE TABLE IF NOT EXISTS otus_spring.Genre (
  Id   INT AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(50) NOT NULL
);

-- User table creation
CREATE TABLE IF NOT EXISTS otus_spring.User (
  Id   INT AUTO_INCREMENT PRIMARY KEY,
  Name VARCHAR(100) NOT NULL
);

-- Book table creation
CREATE TABLE IF NOT EXISTS otus_spring.Book (
  Id           INT AUTO_INCREMENT PRIMARY KEY,
  Name         VARCHAR(200) NOT NULL,
  OriginalName VARCHAR(200),
  Paperback    INT,
  UserId       INT,
  FOREIGN KEY (UserId) REFERENCES otus_spring.User (Id) ON DELETE SET NULL
);

-- Comment table creation
CREATE TABLE IF NOT EXISTS otus_spring.Comment (
  Id     INT AUTO_INCREMENT PRIMARY KEY,
  Text   VARCHAR NOT NULL,
  Date   DATE    NOT NULL,
  BookId INT,
  UserId INT,
  FOREIGN KEY (BookId) REFERENCES otus_spring.Book (Id) ON DELETE CASCADE,
  FOREIGN KEY (UserId) REFERENCES otus_spring.User (Id) ON DELETE SET NULL
);

-- Book-Author table creation
CREATE TABLE IF NOT EXISTS otus_spring.BookAuthor (
  BookId   INT NOT NULL,
  AuthorId INT NOT NULL,
  PRIMARY KEY (BookId, AuthorId),
  FOREIGN KEY (BookId) REFERENCES otus_spring.Book (Id) ON DELETE CASCADE,
  FOREIGN KEY (AuthorId) REFERENCES otus_spring.Author (Id) ON DELETE CASCADE
);

-- Book-Genre table creation
CREATE TABLE IF NOT EXISTS otus_spring.BookGenre (
  BookId  INT NOT NULL,
  GenreId INT NOT NULL,
  PRIMARY KEY (BookId, GenreId),
  FOREIGN KEY (BookId) REFERENCES otus_spring.Book (Id) ON DELETE CASCADE,
  FOREIGN KEY (GenreId) REFERENCES otus_spring.Genre (Id) ON DELETE CASCADE
);

-- Indexes creation
CREATE INDEX IF NOT EXISTS ix_author_name
  ON otus_spring.Author (Name);

CREATE INDEX IF NOT EXISTS ix_genre_name
  ON otus_spring.Genre (Name);

CREATE INDEX IF NOT EXISTS ix_reader_name
  ON otus_spring.User (Name);

CREATE INDEX IF NOT EXISTS ix_book_name
  ON otus_spring.Book (Name);

CREATE INDEX IF NOT EXISTS ix_book_user
  ON otus_spring.Book (UserId);

CREATE INDEX IF NOT EXISTS ix_comment_book
  ON otus_spring.Comment (BookId);

CREATE INDEX IF NOT EXISTS ix_comment_user
  ON otus_spring.Comment (UserId);