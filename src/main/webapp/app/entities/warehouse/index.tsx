import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Warehouse from './warehouse';
import WarehouseDetail from './warehouse-detail';
import WarehouseUpdate from './warehouse-update';
import WarehouseDeleteDialog from './warehouse-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WarehouseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WarehouseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WarehouseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Warehouse} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WarehouseDeleteDialog} />
  </>
);

export default Routes;
