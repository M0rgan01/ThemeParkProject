import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {APIService} from '../../service/api.service';
import {Park} from '../../model/park.model';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import {ToastService} from '../../service/toast.service';
import {Toast} from '../../model/toast.model';

@Component({
  selector: 'app-edit-park',
  templateUrl: './edit-park.component.html',
  styleUrls: ['./edit-park.component.css']
})
export class EditParkComponent implements OnInit, OnDestroy {

  private events;
  private operation: string;
  private park: Park;
  private searching = false;
  private searchFailed = false;
  private title: string;
  private submit: string;

  constructor(public api: APIService,
              public router: Router,
              public activeRoute: ActivatedRoute,
              public toastService: ToastService) {
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
    this.api.postRessources<Park>('/adminRole/park', this.park).subscribe(data => {
      this.toastService.show(new Toast('Succes de la création', 'bg-success text-light', 5000));
    }, error1 => {
      if (error1.error.error) {
        this.toastService.show(new Toast(error1.error.error, 'bg-danger text-light', 10000));
      }
      if (error1.error.errors) {
        for (let i = 0; i < error1.error.errors.length; i++) {
          console.log(error1.error.errors[i]);
          this.toastService.show(new Toast(error1.error.errors[i].message, 'bg-danger text-light', 10000));
        }
      }
    });
  }

  onSubmitEditPark() {
    this.api.putRessources<Park>('/userRole/park/' + this.park.id, this.park).subscribe(data => {
      this.toastService.show(new Toast('Modification réussie !', 'bg-success text-light', 5000));
    }, error1 => {
      if (error1.error.error) {
        this.toastService.show(new Toast(error1.error.error, 'bg-danger text-light', 10000));
      }
      if (error1.error.errors) {
        for (let i = 0; i < error1.error.errors.length; i++) {
          console.log(error1.error.errors[i]);
          this.toastService.show(new Toast(error1.error.errors[i].message, 'bg-danger text-light', 10000));
        }
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
}
