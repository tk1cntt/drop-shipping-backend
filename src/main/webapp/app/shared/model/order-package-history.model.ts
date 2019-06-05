import { Moment } from 'moment';

export interface IOrderPackageHistory {
  id?: number;
  status?: string;
  statusName?: string;
  statusStyle?: string;
  createAt?: Moment;
  updateAt?: Moment;
  orderPackageId?: number;
  warehouseId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IOrderPackageHistory> = {};
