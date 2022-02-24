import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { PostComponent } from './components/post/post.component';
import { PostsComponent } from './components/posts/posts.component';
import { RegisterComponent } from './components/register/register.component';

const routes: Routes = [
  {
    path:"",
    component:PostsComponent
  },
  {
    path:"hot",
    component:PostsComponent
  },
  {
    path:"cold",
    component:PostsComponent
  },
  {
    path:"latest",
    component:PostsComponent
  },
  {
    path:"oldest",
    component:PostsComponent
  },
  {
    path:"popular",
    component:PostsComponent
  },
  {
    path:"post/new",
    component:CreatePostComponent
  },
  {
    path:"post/:id",
    component:PostComponent
  },
  {
    path:"login",
    component:LoginComponent
  },
  {
    path:"register",
    component:RegisterComponent
  },
  {
    path:"logout",
    component:LogoutComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
