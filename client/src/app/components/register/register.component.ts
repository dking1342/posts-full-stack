import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Datastate } from 'src/app/enums/datastate';
import { ADD_USER } from 'src/app/store/actions/userActions';
import { selectUsersData } from 'src/app/store/selectors/userSelectors';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css','../login/login.component.css']
})
export class RegisterComponent implements OnInit {
  data$ = this.store.select(selectUsersData);
  hasError:boolean = false;
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
    this.checkLoginStatus("init");
  }
  
  submitForm(){
    if(this.form.valid){
      this.store.dispatch(ADD_USER({user:this.form.value}));
    }
    this.checkLoginStatus("submit");
  }
  
  checkLoginStatus(status:string){
    this.data$.subscribe(item=>{
      if(status === "submit"){
        let localState = JSON.parse(localStorage.getItem("users")!);
        if(localState){
          this.router.navigate([""]);
        } else if (localState && item?.length){
          this.router.navigate([""])
        } else {
          this.hasError = true;
        }
      }
      if(status === "init"){
        let localState = JSON.parse(localStorage.getItem("users")!);
        if(localState){
          this.router.navigate([""]);
        }
        if (localState && item?.length){
          this.router.navigate([""])
        } 
      }
    });
  }

}
