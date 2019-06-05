import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderPackage from './order-package';
import OrderPackageDetail from './order-package-detail';
import OrderPackageUpdate from './order-package-update';
import OrderPackageDeleteDialog from './order-package-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderPackageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderPackageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderPackageDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderPackage} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrderPackageDeleteDialog} />
  </>
);

export default Routes;
