import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthServiceConfig, FacebookLoginProvider, GoogleLoginProvider} from 'angularx-social-login';
import {LoginProvider} from '../model/login.provider';

@Injectable()
export class ConfigService {

  private configUrl = '../assets/config/social.login.json';

  constructor(private http: HttpClient) {}

  public getAuthConfig() {
    return this.http.get<LoginProvider>(this.configUrl).subscribe(value => {
      const config = new AuthServiceConfig([
        {
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider(value.googleId)
        },
        {
          id: FacebookLoginProvider.PROVIDER_ID,
          provider: new FacebookLoginProvider(value.facebookId)
        }
      ]);
      return config;
    });
  }
}
