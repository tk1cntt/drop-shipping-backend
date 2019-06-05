import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Delivery from './delivery';
import DeliveryDetail from './delivery-detail';
import DeliveryUpdate from './delivery-update';
import DeliveryDeleteDialog from './delivery-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeliveryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeliveryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeliveryDetail} />
      <ErrorBoundaryRoute path={match.url} component={Delivery} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DeliveryDeleteDialog} />
  </>
);

export default Routes;
