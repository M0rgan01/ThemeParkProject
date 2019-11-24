import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthenticationService} from './authentification.service';
import {APIService} from './api.service';

@Injectable()
export class AdminGuardService implements CanActivate {

  constructor(private authService: AuthenticationService,
              private api: APIService) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    /*try {
      if (this.authService.isAuth() && this.authService.isAdmin()) {
        return true;
      } else {
        this.api.redirectToError('access-permission');
        return false;
      }
    } catch (e) {
      this.authService.redirectToLogin('auth-error');
    }*/
    return true;
    }
}
