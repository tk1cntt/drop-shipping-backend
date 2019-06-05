import { Moment } from 'moment';

export const enum CommentType {
  CUSTOMER_LOG = 'CUSTOMER_LOG',
  CUSTOMER_CHAT = 'CUSTOMER_CHAT',
  STAFF_LOG = 'STAFF_LOG',
  STAFF_CHAT = 'STAFF_CHAT',
  SYSTEM_LOG = 'SYSTEM_LOG'
}

export interface IOrderComment {
  id?: number;
  message?: string;
  sender?: string;
  type?: CommentType;
  createAt?: Moment;
  orderCartId?: number;
}

export const defaultValue: Readonly<IOrderComment> = {};
