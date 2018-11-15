import {Component} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap";
import {Subject} from "rxjs";
import {Book} from "../../../model/book";
import {Author} from "../../../model/author";
import {Genre} from "../../../model/genre";

@Component({
  selector: 'book-editor',
  templateUrl: './book-editor.component.html',
  styleUrls: ['./book-editor.component.scss']
})
export class BookEditorComponent {
  public output: Subject<Book>;

  book: Book;
  authors: Array<Author>;
  genres: Array<Genre>;

  constructor(public bsModalRef: BsModalRef) {}

  ngOnInit(): void {
    this.output = new Subject();
  }

  onConfirm(): void {
    this.output.next(this.book);
    this.bsModalRef.hide();
  }

  onCancel(): void {
    this.bsModalRef.hide();
  }

  joinToString(namedItems: Array<any>) {
    if (namedItems) return namedItems.map(it => it.name).join(", ")
  }

  selectGenre(genre: Genre) {
    if (this.book.genres == null) this.book.genres = [];

    if (this.book.genres.findIndex(it => it.id == genre.id) != -1) {
      this.book.genres = this.book.genres.filter(it => it != genre)
    } else {
      this.book.genres.push(genre)
    }
  }

  selectAuthor(author: Author) {
    if (this.book.authors == null) this.book.authors = [];

    if (this.book.authors.findIndex(it => it.id == author.id) != -1) {
      this.book.authors = this.book.authors.filter(it => it != author)
    } else {
      this.book.authors.push(author)
    }
  }

  isAuthorSelected(author: Author) {
    if (this.book.authors) return this.book.authors.findIndex(it => it.id == author.id) != -1
  }

  isGenreSelected(genre: Genre) {
    if (this.book.genres) return this.book.genres.findIndex(it => it.id == genre.id) != -1
  }
}
