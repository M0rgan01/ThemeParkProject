import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {APIService} from '../../service/api.service';
import {AuthenticationService} from '../../service/authentification.service';
import {Park} from '../../model/park.model';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';

@Component({
  selector: 'app-edit-park',
  templateUrl: './edit-park.component.html',
  styleUrls: ['./edit-park.component.css']
})
export class EditParkComponent implements OnInit, OnDestroy {

  private error: string;
  private errors: Array<any>;
  private success: string;
  private events;
  private operation: string;
  private park: Park;
  private searching = false;
  private searchFailed = false;
  private title: string;
  private submit: string;

  constructor(public api: APIService,
              public router: Router,
              public activeRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.events = this.router.events.subscribe((val) => {
      // si la navigation arrive à terme (il y a plusieur event, donc on en garde que un pour éviter plusieur éxécution)
      if (val instanceof NavigationEnd && val.url.startsWith('/admin/park')) {
        this.setContext();
      }
    });
    this.setContext();
  }

  ngOnDestroy(): void {
    this.events.unsubscribe();
  }

  //////////////////////////// CONTEXT //////////////////////////

  setContext() {
    this.setNullErrorAndSuccess();
    // récupération de l'opération à éffectué
    if (this.activeRoute.snapshot.paramMap.get('operation')) {
      this.operation = this.activeRoute.snapshot.paramMap.get('operation');
      if (this.operation === 'create') {
        this.title = 'Création d\'un parc';
        this.submit = 'Créer';
        this.park = new Park();
      } else if (this.operation === 'edit') {
        this.title = 'Modification d\'un parc';
        this.submit = 'Modifier';
        this.park = undefined;
      } else {
        this.api.redirectToNotFound();
      }
    } else {
      this.api.redirectToNotFound();
    }
  }

  ///////////////////// SUBMIT //////////////////////////

  onSubmitCreatePark() {
    this.setNullErrorAndSuccess();
    this.api.postRessources<Park>('/adminRole/park', this.park).subscribe(data => {
      this.success = 'Création réussie !';
    }, error1 => {
      if (error1.error.error) {
        this.error = error1.error.error;
      }
      if (error1.error.errors) {
        this.errors = error1.error.errors;
      }
    });
  }

  onSubmitEditPark() {
    this.setNullErrorAndSuccess();
    this.api.putRessources<Park>('/userRole/park/' + this.park.id, this.park).subscribe(data => {
      this.success = 'Modification réussie !';
    }, error1 => {
      if (error1.error.error) {
        this.error = error1.error.error;
      }
      if (error1.error.errors) {
        this.errors = error1.error.errors;
      }
    });
  }


  ////////////////////// SEARCH ////////////////////////

  formatterCountry = (x: { countryNameEn: string }) => x.countryNameEn;
  formatterPark = (x: { name: string }) => x.name;

  searchCountry = (text: Observable<string>) =>
    text.pipe(
      // attend avant chaque frappe
      debounceTime(300),
      // échappe text identique
      distinctUntilChanged(),
      tap(() => this.searching = true),
      switchMap(term =>
        this.api.searchCountry(term).pipe(
          tap(() => this.searchFailed = false),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          }))
      ),
      tap(() => this.searching = false)
    );

  searchPark = (text: Observable<string>) =>
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

  //////////////////////// RESET ///////////////////////

  resetCountry() {
    this.park.country = null;
  }

  setNullErrorAndSuccess() {
    this.success = null;
    this.errors = null;
    this.error = null;
  }

}
