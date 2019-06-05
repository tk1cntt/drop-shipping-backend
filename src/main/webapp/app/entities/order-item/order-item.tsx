import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-item.reducer';
import { IOrderItem } from 'app/shared/model/order-item.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class OrderItem extends React.Component<IOrderItemProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { orderItemList, match } = this.props;
    return (
      <div>
        <h2 id="order-item-heading">
          <Translate contentKey="dropshippingApp.orderItem.home.title">Order Items</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.orderItem.home.createLabel">Create new Order Item</Translate>
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
                  <Translate contentKey="dropshippingApp.orderItem.avatar">Avatar</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.originLink">Origin Link</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.price">Price</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.priceNDT">Price NDT</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.propertiesId">Properties Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.propertiesImage">Properties Image</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.propertiesMD5">Properties MD 5</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.propertiesName">Properties Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.propertiesType">Properties Type</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.quantityOrder">Quantity Order</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.quantityPending">Quantity Pending</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.quantityReceived">Quantity Received</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.totalPrice">Total Price</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.totalPriceNDT">Total Price NDT</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.website">Website</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.orderCart">Order Cart</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.orderItem.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderItemList.map((orderItem, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderItem.id}`} color="link" size="sm">
                      {orderItem.id}
                    </Button>
                  </td>
                  <td>{orderItem.avatar}</td>
                  <td>{orderItem.originLink}</td>
                  <td>{orderItem.name}</td>
                  <td>{orderItem.note}</td>
                  <td>{orderItem.price}</td>
                  <td>{orderItem.priceNDT}</td>
                  <td>{orderItem.propertiesId}</td>
                  <td>{orderItem.propertiesImage}</td>
                  <td>{orderItem.propertiesMD5}</td>
                  <td>{orderItem.propertiesName}</td>
                  <td>{orderItem.propertiesType}</td>
                  <td>{orderItem.quantityOrder}</td>
                  <td>{orderItem.quantityPending}</td>
                  <td>{orderItem.quantityReceived}</td>
                  <td>{orderItem.totalPrice}</td>
                  <td>{orderItem.totalPriceNDT}</td>
                  <td>{orderItem.website}</td>
                  <td>
                    <TextFormat type="date" value={orderItem.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={orderItem.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{orderItem.orderCartId ? <Link to={`order-cart/${orderItem.orderCartId}`}>{orderItem.orderCartId}</Link> : ''}</td>
                  <td>{orderItem.createByLogin ? orderItem.createByLogin : ''}</td>
                  <td>{orderItem.updateByLogin ? orderItem.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderItem.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderItem.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderItem.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ orderItem }: IRootState) => ({
  orderItemList: orderItem.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderItem);
