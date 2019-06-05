import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Country from './country';
import Region from './region';
import City from './city';
import District from './district';
import Ward from './ward';
import UserProfile from './user-profile';
import UserShippingAddress from './user-shipping-address';
import OrderCart from './order-cart';
import OrderHistory from './order-history';
import OrderItem from './order-item';
import OrderPackage from './order-package';
import OrderPackageHistory from './order-package-history';
import OrderTransaction from './order-transaction';
import OrderComment from './order-comment';
import ShoppingCart from './shopping-cart';
import ShoppingCartItem from './shopping-cart-item';
import Warehouse from './warehouse';
import Delivery from './delivery';
import DeliveryPackage from './delivery-package';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/country`} component={Country} />
      <ErrorBoundaryRoute path={`${match.url}/region`} component={Region} />
      <ErrorBoundaryRoute path={`${match.url}/city`} component={City} />
      <ErrorBoundaryRoute path={`${match.url}/district`} component={District} />
      <ErrorBoundaryRoute path={`${match.url}/ward`} component={Ward} />
      <ErrorBoundaryRoute path={`${match.url}/user-profile`} component={UserProfile} />
      <ErrorBoundaryRoute path={`${match.url}/user-shipping-address`} component={UserShippingAddress} />
      <ErrorBoundaryRoute path={`${match.url}/order-cart`} component={OrderCart} />
      <ErrorBoundaryRoute path={`${match.url}/order-history`} component={OrderHistory} />
      <ErrorBoundaryRoute path={`${match.url}/order-item`} component={OrderItem} />
      <ErrorBoundaryRoute path={`${match.url}/order-package`} component={OrderPackage} />
      <ErrorBoundaryRoute path={`${match.url}/order-package-history`} component={OrderPackageHistory} />
      <ErrorBoundaryRoute path={`${match.url}/order-transaction`} component={OrderTransaction} />
      <ErrorBoundaryRoute path={`${match.url}/order-comment`} component={OrderComment} />
      <ErrorBoundaryRoute path={`${match.url}/shopping-cart`} component={ShoppingCart} />
      <ErrorBoundaryRoute path={`${match.url}/shopping-cart-item`} component={ShoppingCartItem} />
      <ErrorBoundaryRoute path={`${match.url}/warehouse`} component={Warehouse} />
      <ErrorBoundaryRoute path={`${match.url}/delivery`} component={Delivery} />
      <ErrorBoundaryRoute path={`${match.url}/delivery-package`} component={DeliveryPackage} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
