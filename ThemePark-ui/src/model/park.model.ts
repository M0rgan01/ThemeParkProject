import {Country} from './country.model';

export class Park {
  id: number;
  name: string;
  globalNotation: number;
  country: Country;
  location: string;
  gps: string;
  attractionNumber: string;
  officialUrl: string;
  opening: string;
  dateCreation: Date;
  openingDate: Date;
}
