import {Component, OnInit} from '@angular/core';
import {AuthService, FacebookLoginProvider, GoogleLoginProvider, SocialUser} from 'angularx-social-login';
import {APIService} from '../../service/api.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../../service/authentification.service';
import {CustomSocialUser} from '../../model/custom-social-user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  private paramRedirect;

  constructor(private socialAuthService: AuthService,
              private authService: AuthenticationService,
              private api: APIService,
              private router: Router,
              private activeRoute: ActivatedRoute) {
  }

  ngOnInit() {
      this.activeRoute.queryParamMap.subscribe(params => {
       this.paramRedirect = params.get('redirect');
      });
  }


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
      this.authService.removeUserAndCookies();
    }

    this.api.postRessources<CustomSocialUser>('/auth/login', user).subscribe(resp => {
      this.authService.saveUser(resp);
      this.router.navigateByUrl('/');
      console.log('success');
    });
  }

  // https://console.developers.google.com/apis/credentials?project=theme-park-proje-1571093787803
  signInWithGoogle(): void { }

  // https://developers.facebook.com/apps/
  signInWithFB(): void { }
}
