import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Book} from "../model/book";
import {ShortBook} from "../model/short-book";

@Injectable()
export class BookService {
  private apiBase = '/api/books';

  constructor(private http: HttpClient) {
  }

  getBooks(): Observable<ShortBook[]> {
    return this.http
      .get<Book[]>(`${this.apiBase}/all`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getBook(id: number): Observable<Book> {
    return this.http
      .get<Book>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  delete(id: number) {
    return this.http
      .delete<Book>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  create(book: Book) {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });

    return this.http
      .post<Book>(this.apiBase, book)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(id: number, book: Book) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http
      .put<Book>(`${this.apiBase}/${id}`, book)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(res: HttpErrorResponse | any) {
    let errMsg = (res.message) ? res.message : 'Server error';
    console.error(res.error || res.body.error);
    return observableThrowError(errMsg);
  }
}
