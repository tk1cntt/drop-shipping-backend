import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-transaction.reducer';
import { IOrderTransaction } from 'app/shared/model/order-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderTransactionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class OrderTransaction extends React.Component<IOrderTransactionProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { orderTransactionList, match } = this.props;
    return (
      <div>
        <h2 id="order-transaction-heading">
          <Translate contentKey="dropshippingApp.orderTransaction.home.title">Order Transactions</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.orderTransaction.home.createLabel">Create new Order Transaction</Translate>
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
                  <Translate contentKey="dropshippingApp.orderTransaction.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.orderDate">Order Date</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.orderCart">Order Cart</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.approver">Approver</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderTransaction.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderTransactionList.map((orderTransaction, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderTransaction.id}`} color="link" size="sm">
                      {orderTransaction.id}
                    </Button>
                  </td>
                  <td>{orderTransaction.amount}</td>
                  <td>{orderTransaction.note}</td>
                  <td>
                    <Translate contentKey={`dropshippingApp.TransactionType.${orderTransaction.status}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={orderTransaction.orderDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={orderTransaction.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={orderTransaction.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {orderTransaction.orderCartId ? (
                      <Link to={`order-cart/${orderTransaction.orderCartId}`}>{orderTransaction.orderCartId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{orderTransaction.approverLogin ? orderTransaction.approverLogin : ''}</td>
                  <td>{orderTransaction.createByLogin ? orderTransaction.createByLogin : ''}</td>
                  <td>{orderTransaction.updateByLogin ? orderTransaction.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderTransaction.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderTransaction.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderTransaction.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ orderTransaction }: IRootState) => ({
  orderTransactionList: orderTransaction.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderTransaction);
