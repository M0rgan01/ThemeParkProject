import {Park} from './park.model';

export class Country {
  id: number;
  countryNameEn: string;
  countryNameFr: string;
  code: string;
  parks: Array<Park>;
}
