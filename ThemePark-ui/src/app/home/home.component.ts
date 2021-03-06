import {Component, OnInit} from '@angular/core';
import {APIService} from '../../service/api.service';
import {Park} from '../../model/park.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private park: Park;
  constructor(private api: APIService) {}

  ngOnInit(): void {
  }

  getPark() {
    this.api.getRessources<Park>('/public/parkById/1').subscribe(value => {
      this.park = value;
    });
  }

}

