import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderTransaction from './order-transaction';
import OrderTransactionDetail from './order-transaction-detail';
import OrderTransactionUpdate from './order-transaction-update';
import OrderTransactionDeleteDialog from './order-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderTransaction} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrderTransactionDeleteDialog} />
  </>
);

export default Routes;
