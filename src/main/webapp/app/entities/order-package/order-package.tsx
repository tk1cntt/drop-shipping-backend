import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-package.reducer';
import { IOrderPackage } from 'app/shared/model/order-package.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderPackageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class OrderPackage extends React.Component<IOrderPackageProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { orderPackageList, match } = this.props;
    return (
      <div>
        <h2 id="order-package-heading">
          <Translate contentKey="dropshippingApp.orderPackage.home.title">Order Packages</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.orderPackage.home.createLabel">Create new Order Package</Translate>
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
                  <Translate contentKey="dropshippingApp.orderPackage.ladingCode">Lading Code</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.heightPackage">Height Package</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.widthPackage">Width Package</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.lengthPackage">Length Package</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.netWeight">Net Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.statusName">Status Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.statusStyle">Status Style</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.orderCart">Order Cart</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.warehouse">Warehouse</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.updateBy">Update By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderPackage.delivery">Delivery</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderPackageList.map((orderPackage, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderPackage.id}`} color="link" size="sm">
                      {orderPackage.id}
                    </Button>
                  </td>
                  <td>{orderPackage.ladingCode}</td>
                  <td>{orderPackage.heightPackage}</td>
                  <td>{orderPackage.widthPackage}</td>
                  <td>{orderPackage.lengthPackage}</td>
                  <td>{orderPackage.netWeight}</td>
                  <td>{orderPackage.status}</td>
                  <td>{orderPackage.statusName}</td>
                  <td>{orderPackage.statusStyle}</td>
                  <td>
                    <TextFormat type="date" value={orderPackage.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={orderPackage.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {orderPackage.orderCartId ? <Link to={`order-cart/${orderPackage.orderCartId}`}>{orderPackage.orderCartId}</Link> : ''}
                  </td>
                  <td>
                    {orderPackage.warehouseId ? <Link to={`warehouse/${orderPackage.warehouseId}`}>{orderPackage.warehouseId}</Link> : ''}
                  </td>
                  <td>{orderPackage.createByLogin ? orderPackage.createByLogin : ''}</td>
                  <td>{orderPackage.updateByLogin ? orderPackage.updateByLogin : ''}</td>
                  <td>
                    {orderPackage.deliveryId ? <Link to={`delivery/${orderPackage.deliveryId}`}>{orderPackage.deliveryId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderPackage.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderPackage.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderPackage.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ orderPackage }: IRootState) => ({
  orderPackageList: orderPackage.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderPackage);
