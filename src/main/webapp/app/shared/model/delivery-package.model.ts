import { Moment } from 'moment';

export interface IDeliveryPackage {
  id?: number;
  createAt?: Moment;
  updateAt?: Moment;
  deliveryId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IDeliveryPackage> = {};
