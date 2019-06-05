import { Moment } from 'moment';
import { IOrderPackageHistory } from 'app/shared/model/order-package-history.model';

export interface IOrderPackage {
  id?: number;
  ladingCode?: string;
  heightPackage?: number;
  widthPackage?: number;
  lengthPackage?: number;
  netWeight?: number;
  status?: string;
  statusName?: string;
  statusStyle?: string;
  createAt?: Moment;
  updateAt?: Moment;
  orderCartId?: number;
  packages?: IOrderPackageHistory[];
  warehouseId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
  deliveryId?: number;
}

export const defaultValue: Readonly<IOrderPackage> = {};
