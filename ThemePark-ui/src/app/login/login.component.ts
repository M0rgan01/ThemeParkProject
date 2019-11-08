import {Component, OnInit} from '@angular/core';
import {AuthService, FacebookLoginProvider, GoogleLoginProvider, SocialUser} from 'angularx-social-login';
import {APIService} from '../../service/api.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  public user: SocialUser;
  public loggedIn: boolean;

  constructor(private authService: AuthService, private api: APIService, private router: Router) {
  }

  ngOnInit() {}


  signIn(provider: string) {
    if (provider === 'google') {
      this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then(value => {
        this.apiLogin(value);
      });
    } else if (provider === 'facebook') {
      this.authService.signIn(FacebookLoginProvider.PROVIDER_ID).then(value => {
        this.apiLogin(value);
      });
    } else {
      console.log('provider.error');
    }

  }

  apiLogin(user: SocialUser) {
    this.user = user;
    this.loggedIn = (user != null);
    this.api.login(user).subscribe(value => {
      console.log('success');
    }, error1 => {
      console.log(error1);
    });
  }

  signOut(): void {
    this.authService.signOut().then(value => {
      this.user = null;
      this.loggedIn = false;
    });
  }

  // https://console.developers.google.com/apis/credentials?project=theme-park-proje-1571093787803
  signInWithGoogle(): void { }

  // https://developers.facebook.com/apps/
  signInWithFB(): void { }
}
