import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {User} from "../model/user";

@Injectable()
export class UserService {
  private apiBase = '/api/users';

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(this.apiBase)
      .pipe(
        catchError(this.handleError)
      );
  }

  getUser(id: number): Observable<User> {
    return this.http
      .get<User>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  delete(id: number) {
    return this.http
      .delete<User>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  create(user: User) {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });

    return this.http
      .post<User>(this.apiBase, user)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(id: number, user: User) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http
      .put<User>(`${this.apiBase}/${id}`, user)
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
