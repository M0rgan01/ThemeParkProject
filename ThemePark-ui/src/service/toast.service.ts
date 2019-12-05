import {Injectable} from '@angular/core';
import {Toast} from '../model/toast.model';

@Injectable()
export class ToastService {
  toasts: Toast[] = [];

  show(toast: Toast) {
    this.toasts.push(toast);
  }

  remove(toast) {
    this.toasts = this.toasts.filter(t => t !== toast);
  }
}
