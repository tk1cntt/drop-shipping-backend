import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderPackageHistory from './order-package-history';
import OrderPackageHistoryDetail from './order-package-history-detail';
import OrderPackageHistoryUpdate from './order-package-history-update';
import OrderPackageHistoryDeleteDialog from './order-package-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderPackageHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderPackageHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderPackageHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderPackageHistory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrderPackageHistoryDeleteDialog} />
  </>
);

export default Routes;
