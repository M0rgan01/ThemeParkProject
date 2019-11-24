import {SocialUser} from 'angularx-social-login';
import {Park} from './park.model';

export class Comment {
  id: number;
  content: string;
  date: Date;
  notation: number;
  socialUser: SocialUser;
  park: Park;
}
