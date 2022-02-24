import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Datastate } from 'src/app/enums/datastate';
import { GET_USER } from 'src/app/store/actions/userActions';
import { selectUserDataState, selectUserError, selectUsersData } from 'src/app/store/selectors/userSelectors';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  data$ = this.store.select(selectUsersData);
  loadState$ = this.store.select(selectUserDataState);
  error$ = this.store.select(selectUserError);
  readonly Datastate = Datastate;

  form = this.fb.group({
    user_id:[""],
    username:["",Validators.required],
    user_posts:[[]]
  });
  get username(){ return this.form.get("username")};

  constructor(
    private fb:FormBuilder,
    private router:Router,
    private store:Store
  ) { }

  ngOnInit(): void {
    this.checkLoginStatus();
  }
  
  submitForm(){
    if(this.form.valid){
      this.store.dispatch(GET_USER({username:this.username!.value}));
    }
    this.checkLoginStatus();
  }
  
  checkLoginStatus(){
    this.data$.subscribe(item=>{
      let localState = JSON.parse(localStorage.getItem("users")!);
      if(localState || item?.length){
        this.router.navigate([""]);
      }
    });
  }

}
