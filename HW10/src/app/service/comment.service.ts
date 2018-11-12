import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError as observableThrowError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Comment} from "../model/comment";

@Injectable()
export class CommentService {
  private apiBase = '/api/comments';

  constructor(private http: HttpClient) {
  }

  getComments(): Observable<Comment[]> {
    return this.http
      .get<Comment[]>(this.apiBase)
      .pipe(
        catchError(this.handleError)
      );
  }

  getComment(id: number): Observable<Comment> {
    return this.http
      .get<Comment>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  delete(id: number) {
    return this.http
      .delete<Comment>(`${this.apiBase}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  create(comment: Comment) {
    const headers = new Headers({
      'Content-Type': 'application/json'
    });

    return this.http
      .post<Comment>(this.apiBase, comment)
      .pipe(
        catchError(this.handleError)
      );
  }

  update(id: number, comment: Comment) {
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http
      .put<Comment>(`${this.apiBase}/${id}`, comment)
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
