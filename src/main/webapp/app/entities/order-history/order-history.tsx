import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-history.reducer';
import { IOrderHistory } from 'app/shared/model/order-history.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderHistoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class OrderHistory extends React.Component<IOrderHistoryProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { orderHistoryList, match } = this.props;
    return (
      <div>
        <h2 id="order-history-heading">
          <Translate contentKey="dropshippingApp.orderHistory.home.title">Order Histories</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.orderHistory.home.createLabel">Create new Order History</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderHistory.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderHistory.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderHistory.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderHistory.orderCart">Order Cart</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderHistory.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderHistory.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderHistoryList.map((orderHistory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderHistory.id}`} color="link" size="sm">
                      {orderHistory.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`dropshippingApp.OrderStatus.${orderHistory.status}`} />
                  </td>
                  <td>{orderHistory.description}</td>
                  <td>
                    <TextFormat type="date" value={orderHistory.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {orderHistory.orderCartId ? <Link to={`order-cart/${orderHistory.orderCartId}`}>{orderHistory.orderCartId}</Link> : ''}
                  </td>
                  <td>{orderHistory.createByLogin ? orderHistory.createByLogin : ''}</td>
                  <td>{orderHistory.updateByLogin ? orderHistory.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderHistory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderHistory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderHistory.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ orderHistory }: IRootState) => ({
  orderHistoryList: orderHistory.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderHistory);
