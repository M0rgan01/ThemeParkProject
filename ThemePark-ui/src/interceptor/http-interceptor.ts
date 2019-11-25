import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {catchError, filter, switchMap, take} from 'rxjs/operators';
import {BehaviorSubject, throwError} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthenticationService} from '../service/authentification.service';
import {APIService} from '../service/api.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private authService: AuthenticationService, private api: APIService) {
  }

  // L'ensemble des requete http passe par cette méthode
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      withCredentials: true
    });


    // on ajoute un token d'auth s'il en existe un pour chaque requete
    return next.handle(request).pipe(catchError(error => {
      // console.clear();

      // dans le cas d'une erreur de refresh, on reset le process
      if (request.url === this.api.host + '/auth/refresh') {
        this.isRefreshing = false;
      }

      //////////////////////////////// 401 UNAUTHORIZED ///////////////////////////////////
      if (error instanceof HttpErrorResponse && error.status === 401) {

        // token expiré
        if (error.error.error === 'jwt.expired') {
          // dans le cas d'une erreur http et d'un code 401 on procede a un rafraichissement du token
          return this.handle401Error(request, next);

          // refresh token expiré
        } else if (error.error.error === 'jwt.refresh.expired') {
          this.authService.redirectToLogin('expired-session');

          // token invalid / utilisateur introuvable
        } else {
          this.authService.redirectToLogin('auth-error');
        }

      //////////////////////////////// 403 FORBIDDEN ///////////////////////////////////
      } else if (error instanceof HttpErrorResponse && error.status === 403) {

        // compte utilisateur suspendu
        if (error.error.error === 'user.not.active') {
          this.authService.redirectToLogin('account-suspended');

          // ressource non autorisé
        } else {
          this.api.redirectToError('access-permission');
        }

      //////////////////////////////// 404 NOT FOUND ///////////////////////////////////
      } else if (error instanceof HttpErrorResponse && error.status === 404) {
        this.api.redirectToNotFound();

      //////////////////////////////// 406 NOT_ACCEPTABLE ///////////////////////////////////
      } else if (error instanceof HttpErrorResponse && error.status === 406) {
        return throwError(error);

      //////////////////////////////// 405, 409, 412, 500, 503  ///////////////////////////////////
      } else {
        this.api.redirectToError(null);
      }
      return new Observable<HttpEvent<any>>();
    }));
  }


  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);
      return this.api.sendRefreshToken().pipe(
        switchMap((resp) => {
          this.isRefreshing = false;
          const url = resp.url;
          this.refreshTokenSubject.next(url);
          return next.handle(request);
        }));

    } else {
      return this.refreshTokenSubject.pipe(
        filter(url => url != null),
        take(1),
        switchMap(jwt => {
          return next.handle(request);
        }));
    }
  }
}

