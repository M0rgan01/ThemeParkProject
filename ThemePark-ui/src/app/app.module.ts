import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AuthServiceConfig, FacebookLoginProvider, GoogleLoginProvider, SocialLoginModule} from 'angularx-social-login';
import {LoginComponent} from './login/login.component';
import {HttpClientModule} from '@angular/common/http';
import {environment} from '../environments/environment';
import {APIService} from '../service/api.service';
import {AppRoutingModule} from './app-routing.module';
import {JwtModule, JwtModuleOptions} from '@auth0/angular-jwt';
import {AuthGuardService} from '../service/auth-gard.service';
import {AuthenticationService} from '../service/authentification.service';
import {ParkComponent} from './park/park.component';
import {FourHoFourComponent} from './four-ho-four/four-ho-four.component';
import {ErrorComponent} from './error/error.component';
import {AccountComponent} from './account/account.component';
import {AboutComponent} from './about/about.component';
import {EditParkComponent} from './edit-park/edit-park.component';
import {HomeComponent} from './home/home.component';

const config = new AuthServiceConfig([
  {
    id: GoogleLoginProvider.PROVIDER_ID,
    provider: new GoogleLoginProvider(environment.googleId)
  },
  {
    id: FacebookLoginProvider.PROVIDER_ID,
    provider: new FacebookLoginProvider(environment.facebookId)
  }
]);

export function provideConfig() {
  return config;
}

const JWTModuleOptions: JwtModuleOptions = {
  config: {
    tokenGetter: tokenGetters,
    whitelistedDomains: ['localhost:4200']
  }
};

export function tokenGetters() {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ParkComponent,
    FourHoFourComponent,
    ErrorComponent,
    AccountComponent,
    AboutComponent,
    EditParkComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule, SocialLoginModule, HttpClientModule, AppRoutingModule,  JwtModule.forRoot(JWTModuleOptions),
  ],
  providers: [ APIService, AuthGuardService, AuthenticationService, {
    provide: AuthServiceConfig,
    useFactory: provideConfig
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
