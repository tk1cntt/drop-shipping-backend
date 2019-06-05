import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShoppingCartItem from './shopping-cart-item';
import ShoppingCartItemDetail from './shopping-cart-item-detail';
import ShoppingCartItemUpdate from './shopping-cart-item-update';
import ShoppingCartItemDeleteDialog from './shopping-cart-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShoppingCartItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShoppingCartItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShoppingCartItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShoppingCartItem} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShoppingCartItemDeleteDialog} />
  </>
);

export default Routes;
