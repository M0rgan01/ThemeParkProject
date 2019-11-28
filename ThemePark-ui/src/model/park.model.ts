import {Country} from './country.model';
import {Photo} from './photo.model';

export class Park {
  id: number;
  name: string;
  urlName: string;
  globalNotation: number;
  country: Country;
  location: string;
  gps: string;
  attractionNumber: string;
  officialUrl: string;
  opening: string;
  dateCreation: Date;
  openingDate: Date;
  comments: Array<Comment>;
  photos: Array<Photo>;
}
