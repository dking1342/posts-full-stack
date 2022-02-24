import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BehaviorSubject, catchError, map, Observable, of, startWith } from 'rxjs';
import { Datastate } from 'src/app/enums/datastate';
import { FetchResponse, Post, ResponseAppState, User } from 'src/app/interfaces/fetch-responses';
import { FetchService } from 'src/app/services/fetch.service';

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})
export class CreateCommentComponent implements OnInit {
  @Input() username = new BehaviorSubject<User>({user_id:"",username:"",user_posts:[],user_votes:[]});;
  @Input() post_id = "";
  @Input() isLoggedIn = false;
  @Output() closeForm = new EventEmitter<Post[]>();
  @Output() minimizeComments = new EventEmitter<number>();
  commentState$!: Observable<ResponseAppState<FetchResponse<Post>>>;
  readonly Datastate = Datastate;
  
  form = this.fb.group({
    comment_id:[""],
    comment_author:["",Validators.required],
    comment_body:["",Validators.required],
    comment_likes:[0],
    comment_dislikes:[0],
    comment_createdAt:[new Date()],
    // comment_votes:[[]]
  });
  get author(){ return this.form.get("comment_author")};
  get createdAt(){ return this.form.get("comment_createdAt")};

  isSubmitted:boolean = false;

  constructor(
    private fb:FormBuilder,
    private fetch:FetchService,
  ) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.author?.setValue(this.username.value);
    this.isSubmitted = true;
    if(this.form.valid){
      this.commentState$ = this.fetch.addComment$(this.post_id,this.form.value).pipe(
        map(res=>{
          this.closeForm.emit(res.data);
          this.form.reset();
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
          console.log(error);
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      );
    }
  }

  onClickMinimize(e:any){
    this.minimizeComments.emit(e.target.getBoundingClientRect().top);
  }

}
