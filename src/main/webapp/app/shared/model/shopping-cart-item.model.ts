import { Moment } from 'moment';

export interface IShoppingCartItem {
  id?: number;
  itemId?: string;
  itemImage?: string;
  itemLink?: string;
  itemPrice?: number;
  itemPriceNDT?: number;
  itemNote?: string;
  propertiesId?: string;
  propertiesImage?: string;
  propertiesMD5?: string;
  propertiesName?: string;
  propertiesType?: string;
  quantity?: number;
  requireMin?: number;
  totalAmount?: number;
  totalAmountNDT?: number;
  createAt?: Moment;
  updateAt?: Moment;
  shoppingCartId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IShoppingCartItem> = {};
