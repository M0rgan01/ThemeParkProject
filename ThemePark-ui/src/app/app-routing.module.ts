import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {ParkComponent} from './park/park.component';
import {HomeComponent} from './home/home.component';
import {EditParkComponent} from './edit-park/edit-park.component';
import {AuthGuardService} from '../service/auth-gard.service';
import {AboutComponent} from './about/about.component';
import {ErrorComponent} from './error/error.component';
import {FourHoFourComponent} from './four-ho-four/four-ho-four.component';


export const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'park/:id', component: ParkComponent},
  {path: 'about', component: AboutComponent},
  {path: 'error', component: ErrorComponent},
  {path: 'admin', canActivate: [AuthGuardService], children: [
    {path: 'park/:operation', component: EditParkComponent}]},
  {path: 'not-found', component: FourHoFourComponent},
  {path: '**', redirectTo: 'not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
