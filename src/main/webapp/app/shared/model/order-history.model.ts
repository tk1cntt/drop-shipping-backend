import { Moment } from 'moment';

export const enum OrderStatus {
  DEPOSITED = 'DEPOSITED',
  ARE_BUYING = 'ARE_BUYING',
  PURCHASED = 'PURCHASED',
  SELLER_DELIVERY = 'SELLER_DELIVERY',
  WAREHOUSE_CHINA = 'WAREHOUSE_CHINA',
  DELIVERING_CHINA_VIETNAM = 'DELIVERING_CHINA_VIETNAM',
  WAREHOUSE_VIETNAM = 'WAREHOUSE_VIETNAM',
  DELIVERY_REQUIREMENTS = 'DELIVERY_REQUIREMENTS',
  DELIVERING_VIETNAM = 'DELIVERING_VIETNAM',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED',
  LOST = 'LOST',
  RETURN = 'RETURN'
}

export interface IOrderHistory {
  id?: number;
  status?: OrderStatus;
  description?: string;
  createAt?: Moment;
  orderCartId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IOrderHistory> = {};
