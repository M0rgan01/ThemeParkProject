import {Component, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {catchError, debounceTime, distinctUntilChanged, switchMap, tap} from 'rxjs/operators';
import {Park} from '../../model/park.model';
import {APIService} from '../../service/api.service';
import {Photo} from '../../model/photo.model';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {Toast} from '../../model/toast.model';
import {ToastService} from '../../service/toast.service';

@Component({
  selector: 'app-edit-photo',
  templateUrl: './edit-photo.component.html',
  styleUrls: ['./edit-photo.component.css']
})
export class EditPhotoComponent implements OnInit {

  private files: Array<any> = new Array<any>();
  private park: Park;
  private searching = false;
  private searchFailed = false;

  constructor(private api: APIService, private toastService: ToastService) {
  }

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
    if (event.length > 6) {
      this.toastService.show(new Toast('Vous ne pouvez envoyer que 6 fichiers maximum', 'bg-danger text-light', 10000));
    } else {
      for (let index = 0; index < event.length; index++) {
        if (this.files.length === 6) {
          this.toastService.show(new Toast('Vous ne pouvez envoyer que 6 fichiers maximum', 'bg-danger text-light', 10000));
          break;
        } else {
          const element = event[index];
          if (element.type === 'image/jpeg' || element.type === 'image/png') {
            if ((element.size / Math.pow(1024, 2)) < 5) {
              this.files.push(element);
            } else {
              this.toastService.show(new Toast('Le fichier ' + element.name + ' est trop volumineux', 'bg-danger text-light', 10000));
            }
          } else {
            this.toastService.show(new Toast('Le fichier ' + element.name + ' n\'a pas le bon format', 'bg-danger text-light', 10000));
          }
        }
      }
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



      }, error1 => {
      }, () => {
        if (index + 1 === size) {
          this.toastService.show(new Toast('Succes de l\'ajout', 'bg-success text-light', 5000));
          this.reloadPhotos();
        }
        this.files.splice(0, 1);
      });
    }
  }

  ///////////////////// SUBMIT FILE //////////////////////////

  onDeleteFile(id: number) {
    this.api.deleteRessources<Photo>('/userRole/photo/' + id).subscribe(value => {
      this.toastService.show(new Toast('Succes de la suppression', 'bg-success text-light', 5000));
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
