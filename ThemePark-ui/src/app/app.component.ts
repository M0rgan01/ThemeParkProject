import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../service/authentification.service';
import {NavigationEnd, Router} from '@angular/router';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import {APIService} from '../service/api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public title = 'Park Trip';

  private isToggledNavBar = false;
  private isToggledSideBar = false;
  private isCollapsedAccount = false;
  private isCollapsedAdmin = false;

  // affichage component sans side/nav bar
  private listEmptyViewPath = ['login'];
  private isHide = false;

  private searching = false;
  private searchFailed = false;

  public formatter = (x: {name: string}) => x.name;

  constructor(private authService: AuthenticationService,
              public route: Router,
              public api: APIService) { }

  ngOnInit(): void {
    this.route.events.subscribe((val) => {
      this.listEmptyViewPath.forEach(value => {
        // si la navigation arrive à terme (il y a plusieur event, donc on en garde que un pour éviter plusieur éxécution)
        if (val instanceof NavigationEnd && val.url.startsWith('/' + value)) {
          console.log('true');
          this.isHide = true;
        } else if (val instanceof NavigationEnd && !val.url.startsWith('/' + value)) {
          console.log('false');
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
        this.api.search(term).pipe(
          tap(() => this.searchFailed = false),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searching = false)
    )

  submitSearch(name: string) {
    console.log(name);
  }
}
