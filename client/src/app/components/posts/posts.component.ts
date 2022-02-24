import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import * as moment from 'moment';
import { BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { Datastate } from 'src/app/enums/datastate';
import { Comment, CommentVote, FetchResponse, Post, postObj, ResponseAppState, User, userObj, Vote, voteObj } from 'src/app/interfaces/fetch-responses';
import { FetchService } from 'src/app/services/fetch.service';
import { selectUsersData } from 'src/app/store/selectors/userSelectors';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  data$ = this.store.select(selectUsersData);
  appState$!: Observable<ResponseAppState<FetchResponse<Post>>>;
  deleteState$!: Observable<ResponseAppState<FetchResponse<Post>>>;
  readonly Datastate = Datastate;

  username = new BehaviorSubject<User>(userObj);
  username$ = this.username.asObservable();
  voteList = new BehaviorSubject<Vote[]>([voteObj]);
  voteList$ = this.voteList.asObservable();
  postList = new BehaviorSubject<Post[]>([postObj]);
  postList$ = this.postList.asObservable();
  commentResponse = new BehaviorSubject<Post[]>([postObj]);
  commentResponse$ = this.commentResponse.asObservable();

  isLoggedIn:boolean = false;
  showComments:boolean = false;
  showPostId:string = "";
  offsetTime:number = 61.5;
  sortType:string = this.router.url.slice(1,);

  constructor(
    private fetch:FetchService,
    private store:Store,
    private router:Router,
    @Inject(DOCUMENT) private document:Document,
  ) { }

  // lifecycle hooks
  ngOnInit(): void {
    this.onGetPosts();
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

  // methods
  onGetPosts(){
    this.appState$ = this.fetch.getPosts$
      .pipe(
        map(res=>{
          if(res.data){
            res.data
              .sort((a,b)=>{
                return this.sortingPostHelper(a,b);
              })
              .map(item=>{
                let postCreateAt = new Date(item.post_createdAt).getTime();
                let offset = new Date(item.post_createdAt).getTimezoneOffset() * this.offsetTime * 1000;
                let hours = new Date(item.post_createdAt).getHours() * 60 * 1000;
                let newTime = postCreateAt  + (hours - offset);
                item.post_momentAt =  moment(moment.parseZone(newTime).format()).fromNow();
    
                item.comments
                  .sort((a,b)=>{
                    return new Date(b.comment_createdAt).getTime() - new Date(a.comment_createdAt).getTime()
                  })                  
                  .map(comment=>{
                    comment.comment_momentAt = moment(comment.comment_createdAt).fromNow();
                  });
                return item;              
              });
              this.postList.next(res.data!);
          }

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
        catchError((error:any)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      )
  }

  onDelete(id:string){
    if(confirm("Are you sure you want to delete this post?")){
      this.deleteState$ = this.fetch.deletePost$(id).pipe(
        map(res=>{
          this.onGetPosts();
          return {
            dataState:Datastate.LOADED,
            appData:{...res},
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING
        }),
        catchError((error:string)=>{
          console.log(error);
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      )
    }
  }

  onDeleteComment(post_id:string,comment_id:string){
    if(confirm("Are you sure you want to delete this comment?")){
      this.deleteState$ = this.fetch.deleteComment$(post_id,comment_id).pipe(
        map(res=>{
          this.onGetPosts();
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

  onClickLike(post:Post){
    if(this.isLoggedIn){
      let userVote:Vote = {
        vote_id:"",
        value:1,
        user_id:this.username.value.user_id,
        user:this.username.value,
        post_id:post.post_id,
        post
      };

      let apiCall:any = this.fetch.addVote$(userVote).pipe(
        map(res=>{
          return {
            dataState:Datastate.LOADED,
            appData:{
              ...res,
            },
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING,
          appData:{},
          error:""
        }),
        catchError((error:string)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      )
      apiCall.subscribe((observer:ResponseAppState<FetchResponse<Post>>)=>{
        if(observer.dataState === Datastate.LOADED){
          this.voteHelper(observer.appData!.data!);
        }
      })
    } else {
      alert("You must be logged in to like a post");
      this.router.navigate(["login"]);
    }
  }

  onClickCommentLike(post_id:string,comment:Comment){
    if(this.isLoggedIn){
      let userVote:CommentVote = {
        vote_id:"",
        value:1,
        user_id:this.username.value.user_id,
        user:this.username.value,
        post_id,
        comment_id:comment.comment_id,
        comment
      };

      let apiCall:any = this.fetch.addCommentLikeVote$(userVote).pipe(
        map(res=>{
          return {
            dataState:Datastate.LOADED,
            appData:{
              ...res,
            },
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING,
          appData:{},
          error:""
        }),
        catchError((error:string)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      );
      
      apiCall.subscribe((observer:ResponseAppState<FetchResponse<Post>>)=>{
        if(observer.dataState === Datastate.LOADED){
          this.voteHelper(observer.appData!.data!);
        }
      });
    } else {
      alert("You must be logged in to like a commment");
      this.router.navigate(["login"]);
    }
  }

  onClickDislike(post:Post){
    if(this.isLoggedIn){
      let userVote:Vote = {
        vote_id:"",
        value:1,
        user_id:this.username.value.user_id,
        user:this.username.value,
        post_id:post.post_id,
        post
      };

      let apiCall:any = this.fetch.addDislikeVote$(userVote).pipe(
        map(res=>{
          return {
            dataState:Datastate.LOADED,
            appData:{
              ...res,
            },
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING,
          appData:{},
          error:""
        }),
        catchError((error:string)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      );

      apiCall.subscribe((observer:ResponseAppState<FetchResponse<Post>>)=>{
        if(observer.dataState === Datastate.LOADED){
          this.voteHelper(observer.appData!.data!);
        }
      })

    } else {
      alert("You must be logged in to dislike a post");
      this.router.navigate(["login"]);
    }
  }

  onClickCommentDislike(post_id:string,comment:Comment){
    if(this.isLoggedIn){
      let userVote:CommentVote = {
        vote_id:"",
        value:1,
        user_id:this.username.value.user_id,
        user:this.username.value,
        post_id,
        comment_id:comment.comment_id,
        comment
      };

      let apiCall:any = this.fetch.addCommentDislikeVote$(userVote).pipe(
        map(res=>{
          return {
            dataState:Datastate.LOADED,
            appData:{
              ...res,
            },
            error:""
          }
        }),
        startWith({
          dataState:Datastate.LOADING,
          appData:{},
          error:""
        }),
        catchError((error:string)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      );

      apiCall.subscribe((observer:ResponseAppState<FetchResponse<Post>>)=>{
        if(observer.dataState === Datastate.LOADED){
          this.voteHelper(observer.appData!.data!);
        }
      });
  

    } else {
      alert("You must be logged in to dislike a comment");
      this.router.navigate(["login"]);
    }

  }

  // helper functions
  voteHelper(state:Post[]){
    let postArray:Post[] = state!
      .sort((a,b)=>{
        return this.sortingPostHelper(a,b);
      })
      .map(item=>{
        let postCreateAt = new Date(item.post_createdAt).getTime();
        let offset = new Date(item.post_createdAt).getTimezoneOffset() * this.offsetTime * 1000;
        let hours = new Date(item.post_createdAt).getHours() * 60 * 1000;
        let newTime = postCreateAt  + (hours - offset);
        item.post_momentAt =  moment(moment.parseZone(newTime).format()).fromNow();

        item.comments
          .sort((a,b)=>{
            return new Date(b.comment_createdAt).getTime() - new Date(a.comment_createdAt).getTime()
          })
          .map(comment=>{
            return comment.comment_momentAt = moment(comment.comment_createdAt).fromNow();
          })   
        return item;             
      });
    this.postList.next(postArray);
  }

  sortingPostHelper(a:Post,b:Post){
    switch (this.sortType) {
      case "latest":
        return new Date(b.post_createdAt).getTime() - new Date(a.post_createdAt).getTime()
      case "oldest":
        return new Date(a.post_createdAt).getTime() - new Date(b.post_createdAt).getTime()
      case "hot":
        return new Date(b.post_likes).getTime() - new Date(a.post_likes).getTime()
      case "cold":
        return new Date(b.post_dislikes).getTime() - new Date(a.post_dislikes).getTime()
      case "popular":
        return new Date(b.comments.length).getTime() - new Date(a.comments.length).getTime()
      default:
        return new Date(b.post_createdAt).getTime() - new Date(a.post_createdAt).getTime()
    }
  }


  // util functions
  commentEvent(posts:Post[]){
    let updatedPosts = this.postList.value
      .map((post:Post)=>{
        if(post.post_id === posts[0].post_id){
          posts[0].comments.sort((a,b)=> new Date(b.comment_createdAt).getTime() - new Date(a.comment_createdAt).getTime())
          return posts[0]
        } else {
          post.comments.sort((a,b)=> new Date(b.comment_createdAt).getTime() - new Date(a.comment_createdAt).getTime())
          return post;
        }

      })
    this.postList.next(updatedPosts);
  }

  scrollToTop(e:any) {
    let y = e.target.getBoundingClientRect().top;
    setTimeout(()=>{
      window.scrollTo({top:y,behavior:'smooth'});
    },20);
  }

  commentMinimize(yPos:number){
    this.showComments = false;
    setTimeout(()=>{
      window.scrollTo({top:yPos,behavior:'smooth'});
    },100);
  }


}
