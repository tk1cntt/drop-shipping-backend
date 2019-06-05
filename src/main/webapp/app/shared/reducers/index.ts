import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
// prettier-ignore
import region, {
  RegionState
} from 'app/entities/region/region.reducer';
// prettier-ignore
import city, {
  CityState
} from 'app/entities/city/city.reducer';
// prettier-ignore
import district, {
  DistrictState
} from 'app/entities/district/district.reducer';
// prettier-ignore
import ward, {
  WardState
} from 'app/entities/ward/ward.reducer';
// prettier-ignore
import userProfile, {
  UserProfileState
} from 'app/entities/user-profile/user-profile.reducer';
// prettier-ignore
import userShippingAddress, {
  UserShippingAddressState
} from 'app/entities/user-shipping-address/user-shipping-address.reducer';
// prettier-ignore
import orderCart, {
  OrderCartState
} from 'app/entities/order-cart/order-cart.reducer';
// prettier-ignore
import orderHistory, {
  OrderHistoryState
} from 'app/entities/order-history/order-history.reducer';
// prettier-ignore
import orderItem, {
  OrderItemState
} from 'app/entities/order-item/order-item.reducer';
// prettier-ignore
import orderPackage, {
  OrderPackageState
} from 'app/entities/order-package/order-package.reducer';
// prettier-ignore
import orderPackageHistory, {
  OrderPackageHistoryState
} from 'app/entities/order-package-history/order-package-history.reducer';
// prettier-ignore
import orderTransaction, {
  OrderTransactionState
} from 'app/entities/order-transaction/order-transaction.reducer';
// prettier-ignore
import orderComment, {
  OrderCommentState
} from 'app/entities/order-comment/order-comment.reducer';
// prettier-ignore
import shoppingCart, {
  ShoppingCartState
} from 'app/entities/shopping-cart/shopping-cart.reducer';
// prettier-ignore
import shoppingCartItem, {
  ShoppingCartItemState
} from 'app/entities/shopping-cart-item/shopping-cart-item.reducer';
// prettier-ignore
import warehouse, {
  WarehouseState
} from 'app/entities/warehouse/warehouse.reducer';
// prettier-ignore
import delivery, {
  DeliveryState
} from 'app/entities/delivery/delivery.reducer';
// prettier-ignore
import deliveryPackage, {
  DeliveryPackageState
} from 'app/entities/delivery-package/delivery-package.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly country: CountryState;
  readonly region: RegionState;
  readonly city: CityState;
  readonly district: DistrictState;
  readonly ward: WardState;
  readonly userProfile: UserProfileState;
  readonly userShippingAddress: UserShippingAddressState;
  readonly orderCart: OrderCartState;
  readonly orderHistory: OrderHistoryState;
  readonly orderItem: OrderItemState;
  readonly orderPackage: OrderPackageState;
  readonly orderPackageHistory: OrderPackageHistoryState;
  readonly orderTransaction: OrderTransactionState;
  readonly orderComment: OrderCommentState;
  readonly shoppingCart: ShoppingCartState;
  readonly shoppingCartItem: ShoppingCartItemState;
  readonly warehouse: WarehouseState;
  readonly delivery: DeliveryState;
  readonly deliveryPackage: DeliveryPackageState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  country,
  region,
  city,
  district,
  ward,
  userProfile,
  userShippingAddress,
  orderCart,
  orderHistory,
  orderItem,
  orderPackage,
  orderPackageHistory,
  orderTransaction,
  orderComment,
  shoppingCart,
  shoppingCartItem,
  warehouse,
  delivery,
  deliveryPackage,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
