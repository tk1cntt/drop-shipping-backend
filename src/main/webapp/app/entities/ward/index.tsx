import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ward from './ward';
import WardDetail from './ward-detail';
import WardUpdate from './ward-update';
import WardDeleteDialog from './ward-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WardDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ward} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WardDeleteDialog} />
  </>
);

export default Routes;
