import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AuthServiceConfig, FacebookLoginProvider, GoogleLoginProvider, SocialLoginModule} from 'angularx-social-login';
import {LoginComponent} from './login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {environment} from '../environments/environment';
import {APIService} from '../service/api.service';
import {AppRoutingModule} from './app-routing.module';
import {JwtModule, JwtModuleOptions} from '@auth0/angular-jwt';
import {AuthGuardService} from '../service/auth-guard.service';
import {AuthenticationService} from '../service/authentification.service';
import {ParkComponent} from './park/park.component';
import {FourHoFourComponent} from './four-ho-four/four-ho-four.component';
import {ErrorComponent} from './error/error.component';
import {AccountComponent} from './account/account.component';
import {AboutComponent} from './about/about.component';
import {EditParkComponent} from './edit-park/edit-park.component';
import {HomeComponent} from './home/home.component';
import {FormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {TokenInterceptor} from '../interceptor/http-interceptor';
import {AdminGuardService} from '../service/admin-guard.service';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import { DateAgoPipe } from '../pipe/date-ago.pipe';
import {DragDropDirective} from '../directive/drag-drop.directive';
import { EditPhotoComponent } from './edit-photo/edit-photo.component';

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
    HomeComponent,
    DateAgoPipe,
    DragDropDirective,
    EditPhotoComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    SocialLoginModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule,
    InfiniteScrollModule,
    JwtModule.forRoot(JWTModuleOptions)
  ],
  providers: [APIService, AuthGuardService, AdminGuardService, AuthenticationService, {
    provide: AuthServiceConfig,
    useFactory: provideConfig
  },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
