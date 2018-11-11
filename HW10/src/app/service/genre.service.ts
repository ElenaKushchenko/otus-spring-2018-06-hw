import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {Genre} from "../model/genre";

@Injectable()
export class GenreService {
  private apiBase = '/app/genres';

  constructor(private http: HttpClient) {
  }

  getGenres() {
    return this.http
      .get<Genre[]>(this.apiBase)
      .pipe(map(data => data), catchError(this.catchError));
  }

  getGenre(id: number): Observable<Genre> {
    return this.http.get<Genre>(`${this.apiBase}/${id}`)
      .pipe(map(data => data), catchError(this.catchError));
  }


  private extractData(res: Response) {
    let body = res.json();
    return body || {};
  }

  private catchError(res: HttpErrorResponse | any) {
    let errMsg = (res.message) ? res.message : 'Server error';
    console.error(res.error || res.body.error);
    return observableThrowError(errMsg);
  }
}
