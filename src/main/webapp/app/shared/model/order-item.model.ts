import { Moment } from 'moment';

export interface IOrderItem {
  id?: number;
  avatar?: string;
  originLink?: string;
  name?: string;
  note?: string;
  price?: number;
  priceNDT?: number;
  propertiesId?: string;
  propertiesImage?: string;
  propertiesMD5?: string;
  propertiesName?: string;
  propertiesType?: string;
  quantityOrder?: number;
  quantityPending?: number;
  quantityReceived?: number;
  totalPrice?: number;
  totalPriceNDT?: number;
  website?: string;
  createAt?: Moment;
  updateAt?: Moment;
  orderCartId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IOrderItem> = {};
