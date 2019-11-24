import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {APIService} from '../../service/api.service';
import {Park} from '../../model/park.model';
import {Comment} from '../../model/comment.model';
import {AuthenticationService} from '../../service/authentification.service';
import {SocialUser} from 'angularx-social-login';

@Component({
  selector: 'app-park',
  templateUrl: './park.component.html',
  styleUrls: ['./park.component.css']
})
export class ParkComponent implements OnInit {

  private comment: Comment;
  private park: Park;
  currentRate = 5;

  constructor( private activatedRoute: ActivatedRoute,
               private router: Router,
               private api: APIService,
               private authService: AuthenticationService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {
      this.api.getRessources<Park>('/public/park/' + value.get('name')).subscribe(value1 => {
        this.park = value1;
        console.log(this.park);
      });
    });
    this.comment = new Comment();
    this.comment.notation = 0;
    /*infinite scroll*/
  }

  onSubmitComment() {
    this.comment.park = this.park;
    this.comment.socialUser = new SocialUser();
    this.comment.socialUser.email = this.authService.getEmail();
    this.comment.socialUser.provider = this.authService.getProvider();
    this.api.postRessources<Comment>('/userRole/comment', this.comment).subscribe(value => {
      this.comment = new Comment();
      this.comment.notation = 0;
    });
  }

  resetRating() {
    this.comment.notation = 0;
  }
}
