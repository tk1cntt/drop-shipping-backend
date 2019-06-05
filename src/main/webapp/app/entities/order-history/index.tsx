import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderHistory from './order-history';
import OrderHistoryDetail from './order-history-detail';
import OrderHistoryUpdate from './order-history-update';
import OrderHistoryDeleteDialog from './order-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderHistory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrderHistoryDeleteDialog} />
  </>
);

export default Routes;
