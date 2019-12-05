import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {map} from 'rxjs/operators';
import {Router} from '@angular/router';
import {SocialUser} from 'angularx-social-login';
import {Park} from '../model/park.model';
import {Page} from '../model/page.model';
import {SearchCriteria} from '../model/search-criteria.model';
import {AuthenticationService} from './authentification.service';


@Injectable()
export class APIService {

  public host = 'http://localhost:8088/themeParkAPI';
  private searchCriteria: SearchCriteria;
  private listSearchCriteria: Array<SearchCriteria>;
  private autoCompleteSize = 5;

  constructor(private http: HttpClient,
              private router: Router) {
  }


  /////////////////////////////// AUTHENTICATION /////////////////////////////////

  sendRefreshToken() {
    return this.http.get(this.host + '/auth/refresh', {observe: 'response'});
  }

  /////////////////////////////// RESSOURCES /////////////////////////////////

  getRessources<T>(url): Observable<T> {
    return this.http.get<T>(this.host + url);
  }

  putRessources<T>(url, obj): Observable<T> {
    return this.http.put<T>(this.host + url, obj);
  }

  postRessources<T>(url, obj): Observable<T> {
    return this.http.post<T>(this.host + url, obj);
  }

  deleteRessources<T>(url): Observable<T> {
    return this.http.delete<T>(this.host + url);
  }

  /////////////////////////////// UPLOAD /////////////////////////////////

  uploadPhoto(file: File, url) {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post(this.host + url, formData, {reportProgress: true, observe: 'events'});
  }


  /////////////////////////////// RESSOURCES AUTOCOMPLETE  /////////////////////////////////

  searchPark(term: string) {

    if (term === '') {
      return of([]);
    }

    this.listSearchCriteria = new Array<SearchCriteria>();
    this.searchCriteria = new SearchCriteria('name', ':', term);
    this.listSearchCriteria.push(this.searchCriteria);

    return this.http.get<Page<Park>>(this.host + '/public/parks/0/' + this.autoCompleteSize + '?values=' +
      btoa(JSON.stringify(this.listSearchCriteria))).pipe(map(response => response.content));
  }

  searchCountry(term: string) {

    if (term === '') {
      return of([]);
    }

    this.listSearchCriteria = new Array<SearchCriteria>();
    this.searchCriteria = new SearchCriteria('countryNameEn', ':', term);
    this.listSearchCriteria.push(this.searchCriteria);

    return this.http.get<Page<Park>>(this.host + '/public/countries/0/' + this.autoCompleteSize + '?values=' +
      btoa(JSON.stringify(this.listSearchCriteria))).pipe(map(response => response.content));
  }

  /////////////////////////////// ERROR /////////////////////////////////

  redirectToError(reason: string) {
    if (reason) {
      this.router.navigateByUrl('/error?reason=' + reason);
    } else {
      this.router.navigateByUrl('/error');
    }
  }

  /////////////////////////////// NOTFOUND /////////////////////////////////

  redirectToNotFound() {
    this.router.navigateByUrl('/404');
  }
}
