import { Moment } from 'moment';
import { ICity } from 'app/shared/model/city.model';

export interface ICountry {
  id?: number;
  name?: string;
  enabled?: boolean;
  createAt?: Moment;
  updateAt?: Moment;
  cities?: ICity[];
  regionId?: number;
}

export const defaultValue: Readonly<ICountry> = {
  enabled: false
};
