CREATE SCHEMA IF NOT EXISTS otus_spring_hw9;

-- Author table creation
CREATE TABLE IF NOT EXISTS otus_spring_hw9.Author (
  Id   SERIAL       PRIMARY KEY,
  Name VARCHAR(100) NOT NULL
);

-- Genre table creation
CREATE TABLE IF NOT EXISTS otus_spring_hw9.Genre (
  Id   SERIAL      PRIMARY KEY,
  Name VARCHAR(50) NOT NULL
);

-- Book table creation
CREATE TABLE IF NOT EXISTS otus_spring_hw9.Book (
  Id           SERIAL       PRIMARY KEY,
  Name         VARCHAR(200) NOT NULL,
  OriginalName VARCHAR(200),
  Paperback    INT
);

-- Comment table creation
CREATE TABLE IF NOT EXISTS otus_spring_hw9.Comment (
  Id       SERIAL  PRIMARY KEY,
  Text     VARCHAR NOT NULL,
  Date     DATE    NOT NULL,
  BookId   INT     NOT NULL,
  UserName VARCHAR NOT NULL,
  FOREIGN KEY (BookId) REFERENCES otus_spring_hw9.Book (Id) ON DELETE CASCADE
);

-- Book-Author table creation
CREATE TABLE IF NOT EXISTS otus_spring_hw9.BookAuthor (
  BookId   INT NOT NULL,
  AuthorId INT NOT NULL,
  PRIMARY KEY (BookId, AuthorId),
  FOREIGN KEY (BookId) REFERENCES otus_spring_hw9.Book (Id) ON DELETE CASCADE,
  FOREIGN KEY (AuthorId) REFERENCES otus_spring_hw9.Author (Id) ON DELETE CASCADE
);

-- Book-Genre table creation
CREATE TABLE IF NOT EXISTS otus_spring_hw9.BookGenre (
  BookId  INT NOT NULL,
  GenreId INT NOT NULL,
  PRIMARY KEY (BookId, GenreId),
  FOREIGN KEY (BookId) REFERENCES otus_spring_hw9.Book (Id) ON DELETE CASCADE,
  FOREIGN KEY (GenreId) REFERENCES otus_spring_hw9.Genre (Id) ON DELETE CASCADE
);

-- Indexes creation
CREATE INDEX IF NOT EXISTS ix_author_name
  ON otus_spring_hw9.Author (Name);

CREATE INDEX IF NOT EXISTS ix_genre_name
  ON otus_spring_hw9.Genre (Name);

CREATE INDEX IF NOT EXISTS ix_book_name
  ON otus_spring_hw9.Book (Name);

CREATE INDEX IF NOT EXISTS ix_comment_book
  ON otus_spring_hw9.Comment (BookId);