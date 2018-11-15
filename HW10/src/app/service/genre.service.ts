import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Genre} from "../model/genre";

@Injectable()
export class GenreService {
  private apiBase = '/api/genres';

  constructor(private http: HttpClient) {
  }

  getGenres(): Observable<Genre[]> {
    return this.http
      .get<Genre[]>(this.apiBase)
      .pipe(
        catchError(this.handleError)
      );
  }

  getGenre(id: number): Observable<Genre> {
    return this.http
      .get<Genre>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  delete(id: number) {
    return this.http
      .delete<Genre>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  create(genre: Genre) {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });

    return this.http
      .post<Genre>(this.apiBase, genre)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(id: number, genre: Genre) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http
      .put<Genre>(`${this.apiBase}/${id}`, genre)
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
