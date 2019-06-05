import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Region from './region';
import RegionDetail from './region-detail';
import RegionUpdate from './region-update';
import RegionDeleteDialog from './region-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RegionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RegionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Region} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RegionDeleteDialog} />
  </>
);

export default Routes;
