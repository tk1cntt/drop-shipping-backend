import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import City from './city';
import CityDetail from './city-detail';
import CityUpdate from './city-update';
import CityDeleteDialog from './city-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CityDetail} />
      <ErrorBoundaryRoute path={match.url} component={City} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CityDeleteDialog} />
  </>
);

export default Routes;
