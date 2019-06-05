import { Moment } from 'moment';

export const enum TransactionType {
  DEPOSIT = 'DEPOSIT',
  ORDER_PAYMENT = 'ORDER_PAYMENT',
  REFUND = 'REFUND'
}

export interface IOrderTransaction {
  id?: number;
  amount?: number;
  note?: string;
  status?: TransactionType;
  orderDate?: Moment;
  createAt?: Moment;
  updateAt?: Moment;
  orderCartId?: number;
  approverLogin?: string;
  approverId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IOrderTransaction> = {};
