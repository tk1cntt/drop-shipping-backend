import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderCart from './order-cart';
import OrderCartDetail from './order-cart-detail';
import OrderCartUpdate from './order-cart-update';
import OrderCartDeleteDialog from './order-cart-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderCartUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderCartUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderCartDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderCart} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrderCartDeleteDialog} />
  </>
);

export default Routes;
