import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import District from './district';
import DistrictDetail from './district-detail';
import DistrictUpdate from './district-update';
import DistrictDeleteDialog from './district-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DistrictUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DistrictUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DistrictDetail} />
      <ErrorBoundaryRoute path={match.url} component={District} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DistrictDeleteDialog} />
  </>
);

export default Routes;
