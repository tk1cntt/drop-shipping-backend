import { Moment } from 'moment';
import { IShoppingCartItem } from 'app/shared/model/shopping-cart-item.model';

export interface IShoppingCart {
  id?: number;
  aliwangwang?: string;
  depositAmount?: number;
  depositRatio?: number;
  serviceFee?: number;
  serviceFeeDiscount?: number;
  itemChecking?: boolean;
  itemWoodCrating?: boolean;
  shopId?: string;
  shopLink?: string;
  shopName?: string;
  shopNote?: string;
  sourceData?: string;
  totalAmount?: number;
  totalLink?: number;
  totalQuantity?: number;
  finalAmount?: number;
  website?: string;
  createAt?: Moment;
  updateAt?: Moment;
  items?: IShoppingCartItem[];
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IShoppingCart> = {
  itemChecking: false,
  itemWoodCrating: false
};
