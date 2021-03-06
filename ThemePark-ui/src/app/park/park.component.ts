import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {APIService} from '../../service/api.service';
import {Park} from '../../model/park.model';
import {Comment} from '../../model/comment.model';
import {AuthenticationService} from '../../service/authentification.service';
import {Page} from '../../model/page.model';
import {SearchCriteria} from '../../model/search-criteria.model';
import {ToastService} from '../../service/toast.service';
import {Toast} from '../../model/toast.model';

@Component({
  selector: 'app-park',
  templateUrl: './park.component.html',
  styleUrls: ['./park.component.css']
})
export class ParkComponent implements OnInit {

  private comment: Comment;
  private comments: Page<Comment>;
  private park: Park;

  private page = 0;
  private size = 10;
  private infiniteScrollDisable;
  private isCollapsedComment = false;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private api: APIService,
              private authService: AuthenticationService,
              private toastService: ToastService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(value => {
      this.api.getRessources<Park>('/public/park/' + value.get('name')).subscribe(value1 => {
        this.park = value1;
        this.resetContext();
        this.loadComments(this.page, this.size);
      });
    });
    this.comment = new Comment();
    this.comment.notation = 0;
  }

  onSubmitComment() {
    this.comment.park = this.park;
    this.api.postRessources<Comment>('/userRole/comment', this.comment).subscribe(value => {
      this.toastService.show(new Toast('Succès de l\'ajout du commentaire', 'bg-success text-light', 5000));
      this.comment = new Comment();
      this.comment.notation = 0;
      this.resetContext();
      this.loadComments(this.page, this.size);
    }, error1 => {
      if (error1.error.error) {
        this.toastService.show(new Toast(error1.error.error, 'bg-danger text-light', 10000));
      }
      if (error1.error.errors) {
        for (let i = 0; i < error1.error.errors.length; i++) {
          this.toastService.show(new Toast(error1.error.errors[i].message, 'bg-danger text-light', 10000));
        }
      }
    });
  }

  resetRating() {
    this.comment.notation = 0;
  }

  loadComments(page: number, size: number) {

    const listSearchCriteria = new Array<SearchCriteria>();
    listSearchCriteria.push(new SearchCriteria('park.id', ':', this.park.id));
    listSearchCriteria.push(new SearchCriteria('date', 'ORDER_BY_DESC', null));
    listSearchCriteria.push(new SearchCriteria('deleteComment', ':', false));

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

      this.page++;
    });
  }

  resetContext() {
    this.page = 0;
    this.comments = undefined;
    this.isCollapsedComment = false;
    this.infiniteScrollDisable = false;
  }

  navigateCommentEdit(id: number) {
    this.router.navigateByUrl('/account/comment?id=' + id);
  }
}
