import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {ParkComponent} from './park/park.component';
import {HomeComponent} from './home/home.component';
import {EditParkComponent} from './edit-park/edit-park.component';
import {AuthGuardService} from '../service/auth-guard.service';
import {AboutComponent} from './about/about.component';
import {ErrorComponent} from './error/error.component';
import {FourHoFourComponent} from './four-ho-four/four-ho-four.component';
import {AdminGuardService} from '../service/admin-guard.service';
import {EditPhotoComponent} from './edit-photo/edit-photo.component';
import {EditCommentComponent} from './edit-comment/edit-comment.component';


export const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'park/:name', component: ParkComponent},
  {path: 'about', component: AboutComponent},
  {path: 'error', component: ErrorComponent},
  {path: 'admin', canActivate: [AuthGuardService, AdminGuardService], children: [
      {path: 'park/:operation', component: EditParkComponent},
      {path: 'photo', component: EditPhotoComponent}]},
  {path: 'account', canActivate: [AuthGuardService], children: [
      {path: 'comment', component: EditCommentComponent}]},
  {path: 'not-found', component: FourHoFourComponent},
  {path: '**', redirectTo: 'not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
