import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../service/authentification.service';
import {NavigationEnd, Router} from '@angular/router';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import {APIService} from '../service/api.service';
import {Park} from '../model/park.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit  {
  public title = 'Park Trip';

  // affichage menu déroulant / petite résolution
  private isToggledNavBar = false;
  private isToggledSideBar = false;
  private isCollapsedAdmin = false;
  private isCollapsedAccount = false;

  // affichage component sans side/nav bar
  private listEmptyViewPath = ['login'];
  private isHide = false;

  // barre de recherche
  private park: Park;
  private searching = false;
  private searchFailed = false;

  // Authentication info
  private isAuth;
  private isAdmin;
  private photoUrl;
  private userName;

  public formatter = (x: { name: string }) => x.name;

  constructor(private authService: AuthenticationService,
              public route: Router,
              public api: APIService) {
  }

  ngOnInit(): void {
    this.route.events.subscribe((val) => {
      this.listEmptyViewPath.forEach(value => {
        // si la navigation arrive à terme (il y a plusieur event, donc on en garde que un pour éviter plusieur éxécution)
        if (val instanceof NavigationEnd && val.url.startsWith('/' + value)) {
          this.isHide = true;
        } else if (val instanceof NavigationEnd && !val.url.startsWith('/' + value)) {
          try {
            this.isAuth = this.authService.isAuth();
            if (this.isAuth) {
            //  this.isAdmin = this.authService.isAdmin();
              this.photoUrl = this.authService.getPhotoURL();
              this.userName = this.authService.getUserName();
            }
          } catch (e) {
            this.authService.redirectToLogin('auth-error');
          }
          this.isHide = false;
        }
      });
    });
  }

  search = (text: Observable<string>) =>
    text.pipe(
      // attend avant chaque frappe
      debounceTime(300),
      // échappe text identique
      distinctUntilChanged(),
      tap(() => this.searching = true),
      switchMap(term =>
        this.api.searchPark(term).pipe(
          tap(() => this.searchFailed = false),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searching = false)
    );

  submitSearch(park: Park) {
    if (park.urlName) {
      this.route.navigateByUrl('/park/' + park.urlName);
      // this.park = null;
    } else {
      this.api.redirectToNotFound();
    }
  }


}
