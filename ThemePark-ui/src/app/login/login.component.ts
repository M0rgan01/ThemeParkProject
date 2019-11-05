import {Component, OnInit} from '@angular/core';
import {AuthService, FacebookLoginProvider, GoogleLoginProvider, SocialUser} from 'angularx-social-login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  public user: SocialUser;
  public loggedIn: boolean;
  
  constructor(private authService: AuthService) {
  }

  ngOnInit() {}

  // https://console.developers.google.com/apis/credentials?project=theme-park-proje-1571093787803
  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(value => {
      this.user = value;
      this.loggedIn = (value != null);
      console.log(value);
    });
  }

  // https://developers.facebook.com/apps/
  signInWithFB(): void {
    this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then(value => {
      this.user = value;
      this.loggedIn = (value != null);
      console.log(value);
    });
  }

  signOut(): void {
    this.authService.signOut().then(value => {
      this.user = null;
      this.loggedIn = false;
    });
  }

}
