import {Role} from './role.model';

export class CustomSocialUser{
  provider: string;
  email: string;
  name: string;
  photoUrl: string;
  roles: Array<Role>;
}
