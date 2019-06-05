import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-package-history.reducer';
import { IOrderPackageHistory } from 'app/shared/model/order-package-history.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderPackageHistoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class OrderPackageHistory extends React.Component<IOrderPackageHistoryProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { orderPackageHistoryList, match } = this.props;
    return (
      <div>
        <h2 id="order-package-history-heading">
          <Translate contentKey="dropshippingApp.orderPackageHistory.home.title">Order Package Histories</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.orderPackageHistory.home.createLabel">Create new Order Package History</Translate>
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
                  <Translate contentKey="dropshippingApp.orderPackageHistory.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.statusName">Status Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.statusStyle">Status Style</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.orderPackage">Order Package</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.warehouse">Warehouse</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackageHistory.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderPackageHistoryList.map((orderPackageHistory, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderPackageHistory.id}`} color="link" size="sm">
                      {orderPackageHistory.id}
                    </Button>
                  </td>
                  <td>{orderPackageHistory.status}</td>
                  <td>{orderPackageHistory.statusName}</td>
                  <td>{orderPackageHistory.statusStyle}</td>
                  <td>
                    <TextFormat type="date" value={orderPackageHistory.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={orderPackageHistory.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {orderPackageHistory.orderPackageId ? (
                      <Link to={`order-package/${orderPackageHistory.orderPackageId}`}>{orderPackageHistory.orderPackageId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {orderPackageHistory.warehouseId ? (
                      <Link to={`warehouse/${orderPackageHistory.warehouseId}`}>{orderPackageHistory.warehouseId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{orderPackageHistory.createByLogin ? orderPackageHistory.createByLogin : ''}</td>
                  <td>{orderPackageHistory.updateByLogin ? orderPackageHistory.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderPackageHistory.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderPackageHistory.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderPackageHistory.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ orderPackageHistory }: IRootState) => ({
  orderPackageHistoryList: orderPackageHistory.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderPackageHistory);
