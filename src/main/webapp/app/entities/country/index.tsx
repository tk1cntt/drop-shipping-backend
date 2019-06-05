import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Country from './country';
import CountryDetail from './country-detail';
import CountryUpdate from './country-update';
import CountryDeleteDialog from './country-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CountryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CountryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CountryDetail} />
      <ErrorBoundaryRoute path={match.url} component={Country} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CountryDeleteDialog} />
  </>
);

export default Routes;
