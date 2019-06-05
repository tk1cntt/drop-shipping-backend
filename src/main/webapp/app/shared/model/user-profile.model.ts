import { Moment } from 'moment';
import { IUserShippingAddress } from 'app/shared/model/user-shipping-address.model';

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE',
  OTHER = 'OTHER'
}

export interface IUserProfile {
  id?: number;
  fullName?: string;
  gender?: Gender;
  email?: string;
  mobile?: string;
  createAt?: Moment;
  updateAt?: Moment;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
  cityName?: string;
  cityId?: number;
  districtName?: string;
  districtId?: number;
  addresses?: IUserShippingAddress[];
}

export const defaultValue: Readonly<IUserProfile> = {};
