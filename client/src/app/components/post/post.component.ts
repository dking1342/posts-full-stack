import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import * as moment from 'moment';
import { BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { Datastate } from 'src/app/enums/datastate';
import { FetchResponse, Post, ResponseAppState, User, userObj } from 'src/app/interfaces/fetch-responses';
import { FetchService } from 'src/app/services/fetch.service';
import { selectUsersData } from 'src/app/store/selectors/userSelectors';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css','../posts/posts.component.css']
})
export class PostComponent implements OnInit {
  data$ = this.store.select(selectUsersData);
  appState$!: Observable<ResponseAppState<FetchResponse<Post>>>;
  deleteState$!: Observable<ResponseAppState<FetchResponse<Post>>>;
  readonly Datastate = Datastate;
  username = new BehaviorSubject<User>(userObj);
  username$ = this.username.asObservable();
  post_id = this.router.url.split("/")[this.router.url.split("/").length -1];
  showCommentForm:boolean=false;
  isLoggedIn:boolean = false;
  

  constructor(
    private store:Store,
    private fetch:FetchService,
    private router:Router,
    private fb:FormBuilder
  ) { }


  ngOnInit(): void {
    this.onGetPost();
    let localState:ResponseAppState<FetchResponse<User>> = JSON.parse(localStorage.getItem("users")!)
    this.data$.subscribe(observer=>{
      if(localState){
        this.isLoggedIn = true;
        if(localState.appData?.data){
          observer = localState.appData.data;
          this.username.next(observer![0])
        }
      } else {
        this.username.next({user_id:"",username:"",user_posts:[],user_votes:[]});
      }
    });
  }

  onGetPost(){
    this.appState$ = this.fetch.getPost$(this.post_id)
      .pipe(
        map(res=>{

          res.data?.map(item=>{
            let postCreateAt = new Date(item.post_createdAt).getTime();
            let offset = new Date(item.post_createdAt).getTimezoneOffset() * 60 * 1000;
            let hours = new Date(item.post_createdAt).getHours() * 60 * 1000;
            let newTime = postCreateAt  + (hours - offset);
            item.post_momentAt =  moment(moment.parseZone(newTime).format()).fromNow();
            return item.comments.map(comment=>{
              comment.comment_momentAt = moment(comment.comment_createdAt).fromNow();
            })
          })
          return {
            dataState:Datastate.LOADED,
            appData:{
              ...res
            },
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING
        }),
        catchError((error:string)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      )
  }

  onDelete(id:string){
    if(confirm("Are you sure you want to delete this comment?")){
      this.deleteState$ = this.fetch.deleteComment$(this.post_id,id).pipe(
        map(res=>{
          this.onGetPost();
          return {
            dataState:Datastate.LOADED,
            appData:{
              ...res,
            },
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING
        }),
        catchError((error:string)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      )
    }
  }

  closeForm(){
    this.showCommentForm = !this.showCommentForm;
    this.onGetPost();
  }

}
