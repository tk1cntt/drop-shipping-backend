import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DeliveryPackage from './delivery-package';
import DeliveryPackageDetail from './delivery-package-detail';
import DeliveryPackageUpdate from './delivery-package-update';
import DeliveryPackageDeleteDialog from './delivery-package-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeliveryPackageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeliveryPackageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeliveryPackageDetail} />
      <ErrorBoundaryRoute path={match.url} component={DeliveryPackage} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DeliveryPackageDeleteDialog} />
  </>
);

export default Routes;
