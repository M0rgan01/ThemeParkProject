import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {APIService} from '../../service/api.service';
import {Park} from '../../model/park.model';
import {Comment} from '../../model/comment.model';
import {AuthenticationService} from '../../service/authentification.service';
import {SocialUser} from 'angularx-social-login';
import {Page} from '../../model/page.model';
import {SearchCriteria} from '../../model/search-criteria.model';

@Component({
  selector: 'app-park',
  templateUrl: './park.component.html',
  styleUrls: ['./park.component.css']
})
export class ParkComponent implements OnInit {

  private comment: Comment;
  private comments: Page<Comment>;
  private park: Park;
  currentRate = 5;

  private page = 0;
  private size = 10;
  private infiniteScrollDisable;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private api: APIService,
              private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {
      this.api.getRessources<Park>('/public/park/' + value.get('name')).subscribe(value1 => {
        this.park = value1;
        this.comments = undefined;
        this.infiniteScrollDisable = false;
        this.loadComments(this.page, this.size);
      });
    });
    this.comment = new Comment();
    this.comment.notation = 0;
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

  loadComments(page: number, size: number) {

    const listSearchCriteria = new Array<SearchCriteria>();
    const searchCriteria = new SearchCriteria('park.id', ':', this.park.id);
    listSearchCriteria.push(searchCriteria);

    this.api.getRessources<Page<Comment>>('/public/comments/' + page + '/' + size + '?values=' +
      btoa(JSON.stringify(listSearchCriteria))).subscribe(value => {

      if (!this.comments) {
        this.comments = value;
      } else {
        value.content.forEach(comment => {
          this.comments.content.push(comment);
        });
      }

      if (value.numberOfElements < this.size) {
        this.infiniteScrollDisable = true;
      }

    });
  }

}
