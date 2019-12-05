import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {CustomSocialUser} from '../model/custom-social-user.model';
import {APIService} from './api.service';

@Injectable()
export class AuthenticationService {

  private user: CustomSocialUser;
  private roles: Array<any>;

  constructor(private http: HttpClient,
              private router: Router,
              private api: APIService) {
  }


  ///////////////////////// GESTION DES TOKENS ////////////////////

  saveUser(user: CustomSocialUser) {
    localStorage.setItem('userInfo', JSON.stringify(user));
  }

  // chargement du token d'auth
  loadUser() {
    this.user = JSON.parse(localStorage.getItem('userInfo'));
  }

  ///////////////////////// GESTION DES PAYLOAD ////////////////////

  // chargement du pseudo stoké dans le token
  getUserName() {
    if (this.user == null) {
      this.loadUser();
    }
    return this.user.name;
  }

  // chargement du pseudo stoké dans le token
  getEmail() {
    if (this.user == null) {
      this.loadUser();
    }
    return this.user.email;
  }

  // chargement du provider stoké dans le token
  getProvider() {
    if (this.user == null) {
      this.loadUser();
    }
    return this.user.provider;
  }

  // chargement du pseudo stoké dans le token
  getPhotoURL() {
    if (this.user == null) {
      this.loadUser();
    }
    return this.user.photoUrl;
  }

  // chargement des roles stockés dans le token
  loadRoles() {
    if (this.user == null) {
      this.loadUser();
    }
    this.roles = this.user.roles;
  }


  //////////// ADMINISTRATION /////////////////


  // verification de connection d'un utilisateur
  isAuth() {
    this.loadUser();
    return this.user;
  }

  // vérification du role ADMIN
  isAdmin() {
    this.loadRoles();
    for (const r of this.roles) {
      if (r === 'ROLE_ADMIN') {
        return true;
      }
    }
    return false;
  }

  //////////// LOGOUT /////////////////

  removeUserAndCookies() {
    this.api.getRessources('/auth/logout').subscribe(value => {
      this.user = null;
      localStorage.removeItem('userInfo');
    });
  }

  signOut(): void {
    this.removeUserAndCookies();
    this.router.navigateByUrl('/login');
  }

  redirectToLogin(reason: string) {
    console.log('redirect for error');
    this.removeUserAndCookies();
    this.router.navigateByUrl('/login?redirect=' + reason);
  }
}

