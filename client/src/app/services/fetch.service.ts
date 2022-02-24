import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { CommentVote, FetchResponse, Post, User, Vote } from '../interfaces/fetch-responses';

@Injectable({
  providedIn: 'root'
})
export class FetchService {
  apiEndpoint = "http://localhost:8080/api";

  constructor(
    private http:HttpClient
  ) { }

  getPosts$ = <Observable<FetchResponse<Post>>>this.http
    .get<FetchResponse<Post>>(`${this.apiEndpoint}/posts/list`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  getPost$ = (id:string) => <Observable<FetchResponse<Post>>>this.http
    .get<FetchResponse<Post>>(`${this.apiEndpoint}/posts/get/${id}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addPost$ = (post:Post) => <Observable<FetchResponse<Post>>>this.http
    .post<FetchResponse<Post>>(`${this.apiEndpoint}/posts/save`,post)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addComment$ = (id:string,comment:Comment) => <Observable<FetchResponse<Post>>>this.http
    .post<FetchResponse<Post>>(`${this.apiEndpoint}/posts/comment/save/${id}`,comment)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );

  updatePost$ = (post:Post,id:string) => <Observable<FetchResponse<Post>>>this.http
    .put<FetchResponse<Post>>(`${this.apiEndpoint}/posts/update/${id}`,post)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  deletePost$ = (id:string) => <Observable<FetchResponse<Post>>>this.http
    .delete<FetchResponse<Post>>(`${this.apiEndpoint}/posts/delete/${id}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  deleteComment$ = (post_id:string,comment_id:string) => <Observable<FetchResponse<Post>>>this.http
    .delete<FetchResponse<Post>>(`${this.apiEndpoint}/posts/comment/delete/${post_id}/${comment_id}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  getUser$ = (username:string) => <Observable<FetchResponse<User>>>this.http
    .get<FetchResponse<User>>(`${this.apiEndpoint}/users/get/${username}`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addUser$ = (user:User) => <Observable<FetchResponse<User>>>this.http
    .post<FetchResponse<User>>(`${this.apiEndpoint}/users/save`,user)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  
  getVotes$ = <Observable<FetchResponse<Vote>>>this.http
    .get<FetchResponse<Vote>>(`${this.apiEndpoint}/postlikes/list`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addVote$ = (vote:Vote) => <Observable<FetchResponse<Vote>>>this.http
    .post<FetchResponse<Vote>>(`${this.apiEndpoint}/postlikes/save`,vote)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addCommentLikeVote$ = (vote:CommentVote) => <Observable<FetchResponse<Vote>>>this.http
    .post<FetchResponse<Vote>>(`${this.apiEndpoint}/commentlikes/save`,vote)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );

  getDislikeVotes$ = <Observable<FetchResponse<Vote>>>this.http
    .get<FetchResponse<Vote>>(`${this.apiEndpoint}/postdislikes/list`)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addDislikeVote$ = (vote:Vote) => <Observable<FetchResponse<Vote>>>this.http
    .post<FetchResponse<Vote>>(`${this.apiEndpoint}/postdislikes/save`,vote)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );
  addCommentDislikeVote$ = (vote:CommentVote) => <Observable<FetchResponse<Vote>>>this.http
    .post<FetchResponse<Vote>>(`${this.apiEndpoint}/commentdislikes/save`,vote)
    .pipe(
      tap(console.log),
      catchError(this.handleError)
    );


  private handleError(handleError:HttpErrorResponse):Observable<never>{
    return throwError(()=> new Error(`${handleError.error.message}`).message)
  }
}
