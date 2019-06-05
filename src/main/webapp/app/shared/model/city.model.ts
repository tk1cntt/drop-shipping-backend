import { Moment } from 'moment';
import { IDistrict } from 'app/shared/model/district.model';

export interface ICity {
  id?: number;
  name?: string;
  index?: number;
  enabled?: boolean;
  createAt?: Moment;
  updateAt?: Moment;
  countryId?: number;
  districts?: IDistrict[];
}

export const defaultValue: Readonly<ICity> = {
  enabled: false
};
