import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Author} from "../model/author";

@Injectable()
export class AuthorService {
  private apiBase = '/api/authors';

  constructor(private http: HttpClient) {
  }

  getAuthors(): Observable<Author[]> {
    return this.http
      .get<Author[]>(this.apiBase)
      .pipe(
        catchError(this.handleError)
      );
  }

  getAuthor(id: number): Observable<Author> {
    return this.http
      .get<Author>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  delete(id: number) {
    return this.http
      .delete<Author>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  create(author: Author) {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });

    return this.http
      .post<Author>(this.apiBase, author)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(id: number, author: Author) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http
      .put<Author>(`${this.apiBase}/${id}`, author)
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
