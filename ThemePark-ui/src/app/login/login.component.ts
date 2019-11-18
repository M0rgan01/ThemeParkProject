import {Component, OnInit} from '@angular/core';
import {AuthService, FacebookLoginProvider, GoogleLoginProvider, SocialUser} from 'angularx-social-login';
import {APIService} from '../../service/api.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../service/authentification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  public error: string = null;

  constructor(private socialAuthService: AuthService,
              private authService: AuthenticationService,
              private api: APIService,
              private router: Router) {
  }

  ngOnInit() { }


  signIn(provider: string) {
    if (provider === 'google') {
      this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID).then(value => {
        this.apiLogin(value);
      });
    } else if (provider === 'facebook') {
      this.socialAuthService.signIn(FacebookLoginProvider.PROVIDER_ID).then(value => {
        this.apiLogin(value);
      });
    } else {
      console.log('provider.error');
    }

  }

  apiLogin(user: SocialUser) {

    // si on est déja connecté, alors on logout
    if (this.authService.isAuth()) {
      this.authService.removeToken();
    }

    this.api.login(user).subscribe(resp => {
      // nous définisson un object jwt qui aura pour valeur l'en-tete Authorization
      const jwtAuth = resp.headers.get('Authorization');
      const jwtRefresh = resp.headers.get('refresh');
      // on enregistrons le jwt dans le localStorage d'angular
      this.authService.saveToken(jwtAuth, jwtRefresh);
      this.router.navigateByUrl('/');
      console.log('success');
    }, err => {
      this.error = err.error.error;
    });
  }

  // https://console.developers.google.com/apis/credentials?project=theme-park-proje-1571093787803
  signInWithGoogle(): void { }

  // https://developers.facebook.com/apps/
  signInWithFB(): void { }
}
