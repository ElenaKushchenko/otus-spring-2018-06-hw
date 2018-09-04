DELETE FROM BookAuthor;
DELETE FROM BookGenre;
DELETE FROM Book;
DELETE FROM Reader;
DELETE FROM Author;
DELETE FROM Genre;

INSERT INTO Author (Id, Name) VALUES (1, 'Author1');
INSERT INTO Author (Id, Name) VALUES (2, 'Author2');
INSERT INTO Author (Id, Name) VALUES (3, 'Author3');

INSERT INTO Genre (Id, Name) VALUES (1, 'Genre1');
INSERT INTO Genre (Id, Name) VALUES (2, 'Genre2');
INSERT INTO Genre (Id, Name) VALUES (3, 'Genre3');

INSERT INTO Reader (Id, Name) VALUES (1, 'Reader1');
INSERT INTO Reader (Id, Name) VALUES (2, 'Reader2');

INSERT INTO Book (Id, Name, OriginalName, Paperback, ReaderId) VALUES (1, 'Book1', 'Book1 Original Name', 111, 1);
INSERT INTO Book (Id, Name, OriginalName, Paperback) VALUES (2, 'Book2', 'Book2 Original Name', 222);

INSERT INTO BookAuthor (BookId, AuthorId) VALUES (1, 1);
INSERT INTO BookAuthor (BookId, AuthorId) VALUES (1, 2);

INSERT INTO BookAuthor (BookId, AuthorId) VALUES (2, 2);
INSERT INTO BookAuthor (BookId, AuthorId) VALUES (2, 3);

INSERT INTO BookGenre (BookId, GenreId) VALUES (1, 1);
INSERT INTO BookGenre (BookId, GenreId) VALUES (1, 2);

INSERT INTO BookGenre (BookId, GenreId) VALUES (2, 2);
INSERT INTO BookGenre (BookId, GenreId) VALUES (2, 3);