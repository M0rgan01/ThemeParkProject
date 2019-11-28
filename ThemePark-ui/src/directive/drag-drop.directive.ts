import {Directive, EventEmitter, HostBinding, HostListener, Output} from '@angular/core';

@Directive({
  selector: '[appDragDrop]'
})
export class DragDropDirective {

  @Output() onFileDropped = new EventEmitter<any>();

  // BG PAR DEFAULT
  @HostBinding('style.background-color') private background = '#ffffff';
  @HostBinding('style.opacity') private opacity = '1';

  // HOVER listener
  @HostListener('dragover', ['$event']) onDragOver(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = '#00eeff'; // BG une fois un item survolé
    this.opacity = '1';
  }

  // Dragleave listener
  @HostListener('dragleave', ['$event']) public onDragLeave(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = '#ffffff'; // BG une fois un item survolé, mais pas déposé
    this.opacity = '1';
  }

  // Drop listener
  @HostListener('drop', ['$event']) public ondrop(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    this.background = '#ffffff'; // BG une fois un item déposé
    this.opacity = '1';
    const files = evt.dataTransfer.files;
    if (files.length > 0) {
      this.onFileDropped.emit(files);
    }
  }

}
