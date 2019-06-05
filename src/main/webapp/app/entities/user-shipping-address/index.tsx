import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserShippingAddress from './user-shipping-address';
import UserShippingAddressDetail from './user-shipping-address-detail';
import UserShippingAddressUpdate from './user-shipping-address-update';
import UserShippingAddressDeleteDialog from './user-shipping-address-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserShippingAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserShippingAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserShippingAddressDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserShippingAddress} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserShippingAddressDeleteDialog} />
  </>
);

export default Routes;
