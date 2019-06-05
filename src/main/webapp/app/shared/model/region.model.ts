import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';
import { IUser } from 'app/shared/model/user.model';

export interface IRegion {
  id?: number;
  name?: string;
  enabled?: boolean;
  createAt?: Moment;
  updateAt?: Moment;
  countries?: ICountry[];
  users?: IUser[];
}

export const defaultValue: Readonly<IRegion> = {
  enabled: false
};
