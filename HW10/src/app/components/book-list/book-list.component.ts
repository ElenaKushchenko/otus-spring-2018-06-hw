import {Component} from '@angular/core';
import {Book} from "../../model/book";
import {BookService} from "../../service/book.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {BookEditorComponent} from "./book-editor/book-editor.component";
import {ConfirmationComponent} from "../modals/confirmation/confirmation.component";
import {ShortBook} from "../../model/short-book";
import {AuthorService} from "../../service/author.service";
import {GenreService} from "../../service/genre.service";
import {Author} from "../../model/author";
import {Genre} from "../../model/genre";

@Component({
  selector: 'book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent {
  bsModalRef: BsModalRef;
  books: Array<ShortBook>;

  private authors: Array<Author>;
  private genres: Array<Genre>;

  constructor(private modalService: BsModalService,
              private bookService: BookService,
              private authorService: AuthorService,
              private genreService: GenreService) {
  }

  ngOnInit(): void {
    this.getBooks();
    this.getAuthors();
    this.getGenres();
  }

  getBooks() {
    this.bookService.getBooks()
      .subscribe(data =>
        this.books = data
      );
  }

  deleteBook(id: number) {
    this.bookService.delete(id)
      .subscribe(data =>
        this.books = this.books.filter(it => it.id != id)
      );
  }

  createBook(book: Book) {
    this.bookService.create(book)
      .subscribe(data =>
        this.books.push(data)
      );
  }

  updateBook(id: number, book: Book) {
    this.bookService.update(id, book)
      .subscribe(data => {
          this.books = this.books.filter(it => it.id != id);
          this.books.push(data);
        }
      )
    ;
  }

  getAuthors() {
    this.authorService.getAuthors()
      .subscribe(data =>
        this.authors = data,
      );
  }

  getGenres() {
    this.genreService.getGenres()
      .subscribe(data =>
        this.genres = data,
      );
  }

  async openEditorComponent(idToUpdate: number) {
    let toUpdate: Book;

    if (idToUpdate)
      toUpdate = await this.bookService.getBook(idToUpdate).toPromise();

    const initialState = {
      book: Object.assign({}, toUpdate),
      authors: this.authors,
      genres: this.genres
    };

    this.bsModalRef = this.modalService.show(BookEditorComponent, {initialState});
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (!!toUpdate)
          this.updateBook(toUpdate.id, updated);
        else
          this.createBook(updated)
      })
  }

  openConfirmationComponent(idToDelete: number) {
    this.bsModalRef = this.modalService.show(ConfirmationComponent);
    this.bsModalRef.content.output
      .subscribe(updated => {
        if (updated) this.deleteBook(idToDelete);
      })
  }

  joinToString(namedItems: Array<any>) {
    if (namedItems) return namedItems.map(it => it.name).join(", ")
  }
}
