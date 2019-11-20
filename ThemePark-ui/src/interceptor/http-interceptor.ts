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

    // on ajoute un token d'auth s'il en existe un pour chaque requete
    return next.handle(this.addToken(request, this.authService.getTokenAuth())).pipe(catchError(error => {

      // dans le cas d'une erreur http et d'un code 401 on procede a un rafraichissement du token
      if (error instanceof HttpErrorResponse && error.status === 401) {
        return this.handle401Error(request, next);
      } else {
        return throwError(error);
      }
    }));
  }


  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.api.sendRefreshToken().pipe(
        switchMap((resp) => {
          this.isRefreshing = false;

          const token: string = resp.headers.get('Authorization');
          this.authService.saveAuthToken(token);

          this.refreshTokenSubject.next(token);
          return next.handle(this.addToken(request, token));
        }));

    } else {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(jwt => {
          return next.handle(this.addToken(request, jwt));
        }));
    }
  }


  addToken(req: HttpRequest<any>, token: string): HttpRequest<any> {
    if (token) {
      return req = req.clone({
        setHeaders: {
          Authorization: token
        }
      });
    }
    return req;
  }

}

