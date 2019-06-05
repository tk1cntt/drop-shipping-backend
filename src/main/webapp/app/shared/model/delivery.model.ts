import { Moment } from 'moment';
import { IDeliveryPackage } from 'app/shared/model/delivery-package.model';
import { IOrderPackage } from 'app/shared/model/order-package.model';

export interface IDelivery {
  id?: number;
  deliveryMethod?: string;
  deliveryMethodName?: string;
  exportTime?: Moment;
  packedTime?: Moment;
  status?: string;
  statusName?: string;
  statusStyle?: string;
  totalWeight?: number;
  createAt?: Moment;
  updateAt?: Moment;
  packages?: IDeliveryPackage[];
  orders?: IOrderPackage[];
  warehouseId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IDelivery> = {};
