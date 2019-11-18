import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../service/authentification.service';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public title = 'Park Trip';
  public isToggled = false;
  public listEmptyViewPath = ['login'];
  public isHide = false;

  constructor(private authService: AuthenticationService, public route: Router) {
  }

// déroulement de la sidebar
  onSwitchToggle() {
    if (this.isToggled) {
      this.isToggled = false;
    } else if (!this.isToggled) {
      this.isToggled = true;
    }
  }

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
}
