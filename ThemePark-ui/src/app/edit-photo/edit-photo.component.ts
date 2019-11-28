import { Component, OnInit } from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import {Park} from '../../model/park.model';
import {APIService} from '../../service/api.service';
import {Photo} from '../../model/photo.model';
import {HttpEventType, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-edit-photo',
  templateUrl: './edit-photo.component.html',
  styleUrls: ['./edit-photo.component.css']
})
export class EditPhotoComponent implements OnInit {

  private title = 'Edition de photo';
  private success: string;
  private files: Array<any> = new Array<any>();
  private park: Park;
  private searching = false;
  private searchFailed = false;

  constructor(private api: APIService) { }

  ngOnInit() {
  }

///////////////////// SEARCH PARK //////////////////////////

  formatterPark = (x: { name: string }) => x.name;
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

  ///////////////////// SUBMIT FILE //////////////////////////

  addFile(event) {
    for (let index = 0; index < event.length; index++) {
      const element = event[index];
      this.files.push(element);
    }
  }

  deleteAttachment(index) {
    this.files.splice(index, 1);
  }

  uploadFile() {
    const size = this.files.length;
    for (let index = 0; index < size; index++) {
      this.api.uploadPhoto(this.files[index], '/userRole/photo/' + this.park.id).subscribe(value => {
        // si le type est un événement de UploadProgress
        if (value.type === HttpEventType.UploadProgress) {
          console.log(Math.round(100 * value.loaded / value.total));
          // this.progress = ;
          // si c'est terminé
        } else if (value instanceof HttpResponse) {

          // this.successUpload = 'Mise à jour réussie !';
          // this.product.photo = ' ';
        }
      }, error1 => {}, () => {
        if (index + 1 === size) {
          console.log('Ajout réussi !');
          this.success = 'Ajout réussi !';
          this.reloadPhotos();
        }
        this.files.splice(0, 1);
      });
    }
  }

  ///////////////////// SUBMIT FILE //////////////////////////

  onDeleteFile(id: number) {
    this.api.deleteRessources<Photo>('/userRole/photo/' + id).subscribe(value => {
      this.reloadPhotos();
    });
  }

  ///////////////////// RELOAD PHOTOS //////////////////////////

  reloadPhotos() {
    this.api.getRessources<Array<Photo>>('/public/photos/parc/' + this.park.id).subscribe(value1 => {
      this.park.photos = value1;
    });
  }
}
