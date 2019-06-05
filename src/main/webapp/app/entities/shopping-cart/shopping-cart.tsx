import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './shopping-cart.reducer';
import { IShoppingCart } from 'app/shared/model/shopping-cart.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShoppingCartProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ShoppingCart extends React.Component<IShoppingCartProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { shoppingCartList, match } = this.props;
    return (
      <div>
        <h2 id="shopping-cart-heading">
          <Translate contentKey="dropshippingApp.shoppingCart.home.title">Shopping Carts</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.shoppingCart.home.createLabel">Create new Shopping Cart</Translate>
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
                  <Translate contentKey="dropshippingApp.shoppingCart.aliwangwang">Aliwangwang</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.depositAmount">Deposit Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.depositRatio">Deposit Ratio</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.serviceFee">Service Fee</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.serviceFeeDiscount">Service Fee Discount</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.itemChecking">Item Checking</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.itemWoodCrating">Item Wood Crating</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.shopId">Shop Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.shopLink">Shop Link</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.shopName">Shop Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.shopNote">Shop Note</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.sourceData">Source Data</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.totalAmount">Total Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.totalLink">Total Link</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.totalQuantity">Total Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.finalAmount">Final Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.website">Website</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCart.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shoppingCartList.map((shoppingCart, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${shoppingCart.id}`} color="link" size="sm">
                      {shoppingCart.id}
                    </Button>
                  </td>
                  <td>{shoppingCart.aliwangwang}</td>
                  <td>{shoppingCart.depositAmount}</td>
                  <td>{shoppingCart.depositRatio}</td>
                  <td>{shoppingCart.serviceFee}</td>
                  <td>{shoppingCart.serviceFeeDiscount}</td>
                  <td>{shoppingCart.itemChecking ? 'true' : 'false'}</td>
                  <td>{shoppingCart.itemWoodCrating ? 'true' : 'false'}</td>
                  <td>{shoppingCart.shopId}</td>
                  <td>{shoppingCart.shopLink}</td>
                  <td>{shoppingCart.shopName}</td>
                  <td>{shoppingCart.shopNote}</td>
                  <td>{shoppingCart.sourceData}</td>
                  <td>{shoppingCart.totalAmount}</td>
                  <td>{shoppingCart.totalLink}</td>
                  <td>{shoppingCart.totalQuantity}</td>
                  <td>{shoppingCart.finalAmount}</td>
                  <td>{shoppingCart.website}</td>
                  <td>
                    <TextFormat type="date" value={shoppingCart.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shoppingCart.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{shoppingCart.createByLogin ? shoppingCart.createByLogin : ''}</td>
                  <td>{shoppingCart.updateByLogin ? shoppingCart.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${shoppingCart.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shoppingCart.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shoppingCart.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ shoppingCart }: IRootState) => ({
  shoppingCartList: shoppingCart.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShoppingCart);
