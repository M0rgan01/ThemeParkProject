import {Component, OnInit} from '@angular/core';
import {APIService} from '../../service/api.service';
import {Page} from '../../model/page.model';
import {Comment} from '../../model/comment.model';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ToastService} from '../../service/toast.service';
import {Toast} from '../../model/toast.model';
import {SearchCriteria} from '../../model/search-criteria.model';
import {AuthenticationService} from '../../service/authentification.service';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css']
})
export class EditCommentComponent implements OnInit {

  private page = 1;
  private size = 6;
  private comments: Page<Comment>;
  private comment: Comment;

  constructor(private api: APIService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private modalService: NgbModal,
              private toastService: ToastService,
              private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(value => {
      this.comments = null;
      this.comment = null;
      const key = 'id';
      if (value[key]) {
        this.getComment(value[key]);
      } else {
        this.getComments();
      }
    });
  }

  getComments() {
    const listSearchCriteria = new Array<SearchCriteria>();
    listSearchCriteria.push(new SearchCriteria('socialUser.email', ':', this.authService.getEmail()));
    listSearchCriteria.push(new SearchCriteria('socialUser.provider', ':', this.authService.getProvider()));
    listSearchCriteria.push(new SearchCriteria('deleteComment', ':', false));

    this.api.getRessources<Page<Comment>>('/public/comments/' + (this.page - 1) + '/' + this.size + '?values=' +
      btoa(JSON.stringify(listSearchCriteria))).subscribe(value => {
      this.comments = value;
    });
  }

  getComment(id) {
    this.api.getRessources<Comment>('/public/comment/' + id).subscribe(value => {
      this.comment = value;
    });
  }

  onSelectedComment(c: Comment) {
    this.router.navigateByUrl('/account/comment?id=' + c.id);
    this.comment = c;
  }

  resetRating() {
    this.comment.notation = 0;
  }

  onUpdateComment(comment: Comment) {
    this.api.putRessources<Comment>('/userRole/comment/' + this.comment.id, comment).subscribe(
      value => {
        this.comment = value;
      },
      error1 => {
      },
      () => {
        this.toastService.show(new Toast('Succès de la mise à jour', 'bg-success text-light', 5000));
      });
  }

  onDelete(content) {
    this.modalService.open(content).result.then(() => {
      this.api.deleteRessources<Comment>('/userRole/comment/' + this.comment.id).subscribe(
        value => {
        },
        error1 => {
        }, () => {
          this.toastService.show(new Toast('Succès de la suppression', 'bg-success text-light', 5000));
          this.router.navigateByUrl('/account/comment');
        });
    }, () => {
    });
  }
}
