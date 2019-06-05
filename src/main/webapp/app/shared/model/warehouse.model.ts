import { Moment } from 'moment';

export interface IWarehouse {
  id?: number;
  name?: string;
  address?: string;
  createAt?: Moment;
  updateAt?: Moment;
}

export const defaultValue: Readonly<IWarehouse> = {};
