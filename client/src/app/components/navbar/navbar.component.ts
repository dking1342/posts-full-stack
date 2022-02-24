import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { CLEAR_USER } from 'src/app/store/actions/userActions';
import { selectUsersData } from 'src/app/store/selectors/userSelectors';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  data$ = this.store.select(selectUsersData);
  isLoggedIn:boolean = false;

  constructor(
    private store:Store,
    private router:Router,
  ) { }

  ngOnInit(): void {
    this.checkLoginStatus();
  }

  // methods  
  checkLoginStatus(){
    this.data$.forEach(item=>
      (item?.length) ? this.isLoggedIn = true : this.isLoggedIn = false
    );
    let localState = JSON.parse(localStorage.getItem("users")!);
    (localState) ? this.isLoggedIn = true : this.isLoggedIn = false;
  }

  logout(){
    this.store.dispatch(CLEAR_USER());
    localStorage.removeItem("users");
    if(JSON.parse(localStorage.getItem("users")!) === null){
      this.router.navigate(["logout"])
    }
  }

}
