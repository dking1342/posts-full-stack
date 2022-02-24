import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, map, Observable, of, startWith } from 'rxjs';
import { Datastate } from 'src/app/enums/datastate';
import { FetchResponse, Post, ResponseAppState, User, userObj } from 'src/app/interfaces/fetch-responses';
import { FetchService } from 'src/app/services/fetch.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  postState$!:Observable<ResponseAppState<FetchResponse<Post>>>;
  form = this.fb.group({
    post_id:[""],
    post_title:["",Validators.required],
    post_body:["",Validators.required],
    post_author:[{},Validators.required],
    post_likes:[0],
    post_dislikes:[0],
    comments:[[]],
    post_createdAt:[new Date()],
    post_votes:[[]]
  });
  get author(){ return this.form.get("post_author")};
  get createdAt(){ return this.form.get("post_createdAt")};
  username:User = userObj;
  isSubmitted:boolean = false;

  constructor(
    private fb:FormBuilder,
    private router:Router,
    private fetch:FetchService,
    private location:Location
  ) { }

  ngOnInit(): void {
    let localState:ResponseAppState<FetchResponse<User>> = JSON.parse(localStorage.getItem("users")!)
    if(localState){
      let isUser = localState.appData?.data![0];
      if(isUser){
        this.username = isUser;
      }
    } else {
      this.router.navigate([""])
    }
  }

  submitForm(){
    this.author?.setValue(this.username);
    this.isSubmitted = true;
    if(this.form.valid){
      this.postState$ = this.fetch.addPost$(this.form.value).pipe(
        map(res=>{
          this.location.back();
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
        catchError((error:any)=>{
          return of({
            dataState:Datastate.ERROR,
            error
          })
        })
      );
    }
  }

}
