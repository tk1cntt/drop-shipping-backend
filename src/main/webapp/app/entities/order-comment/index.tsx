import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderComment from './order-comment';
import OrderCommentDetail from './order-comment-detail';
import OrderCommentUpdate from './order-comment-update';
import OrderCommentDeleteDialog from './order-comment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderCommentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderCommentDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderComment} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={OrderCommentDeleteDialog} />
  </>
);

export default Routes;
