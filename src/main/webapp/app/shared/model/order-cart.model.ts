import { Moment } from 'moment';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { IOrderComment } from 'app/shared/model/order-comment.model';
import { IOrderHistory } from 'app/shared/model/order-history.model';
import { IOrderPackage } from 'app/shared/model/order-package.model';
import { IOrderTransaction } from 'app/shared/model/order-transaction.model';

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

export interface IOrderCart {
  id?: number;
  code?: string;
  avatar?: string;
  amountDiscount?: number;
  amountPaid?: number;
  depositAmount?: number;
  depositRatio?: number;
  depositTime?: Moment;
  domesticShippingChinaFeeNDT?: number;
  domesticShippingChinaFee?: number;
  domesticShippingVietnamFee?: number;
  quantityOrder?: number;
  quantityPending?: number;
  quantityReceived?: number;
  rate?: number;
  receiverName?: string;
  receiverAddress?: string;
  receiverMobile?: string;
  receiverNote?: string;
  refundAmountByAlipay?: number;
  refundAmountByComplaint?: number;
  refundAmountByOrder?: number;
  refundAmountPending?: number;
  shippingChinaVietnamFee?: number;
  shippingChinaVietnamFeeDiscount?: number;
  shopAliwang?: string;
  shopId?: string;
  shopLink?: string;
  shopName?: string;
  website?: string;
  websiteCode?: string;
  websiteLadingCode?: string;
  status?: OrderStatus;
  statusName?: string;
  statusStyle?: string;
  tallyFee?: number;
  totalAmount?: number;
  totalAmountNDT?: number;
  totalPaidByCustomer?: number;
  serviceFee?: number;
  serviceFeeDiscount?: number;
  totalServiceFee?: number;
  finalAmount?: number;
  createAt?: Moment;
  updateAt?: Moment;
  items?: IOrderItem[];
  comments?: IOrderComment[];
  histories?: IOrderHistory[];
  packages?: IOrderPackage[];
  transactions?: IOrderTransaction[];
  buyerLogin?: string;
  buyerId?: number;
  chinaStockerLogin?: string;
  chinaStockerId?: number;
  vietnamStockerLogin?: string;
  vietnamStockerId?: number;
  exporterLogin?: string;
  exporterId?: number;
  createByLogin?: string;
  createById?: number;
  updateByLogin?: string;
  updateById?: number;
}

export const defaultValue: Readonly<IOrderCart> = {};
