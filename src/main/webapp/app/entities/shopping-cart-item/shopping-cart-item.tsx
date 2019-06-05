import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './shopping-cart-item.reducer';
import { IShoppingCartItem } from 'app/shared/model/shopping-cart-item.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShoppingCartItemProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ShoppingCartItem extends React.Component<IShoppingCartItemProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { shoppingCartItemList, match } = this.props;
    return (
      <div>
        <h2 id="shopping-cart-item-heading">
          <Translate contentKey="dropshippingApp.shoppingCartItem.home.title">Shopping Cart Items</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.shoppingCartItem.home.createLabel">Create new Shopping Cart Item</Translate>
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
                  <Translate contentKey="dropshippingApp.shoppingCartItem.itemId">Item Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.itemImage">Item Image</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.itemLink">Item Link</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.itemPrice">Item Price</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.itemPriceNDT">Item Price NDT</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.itemNote">Item Note</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesId">Properties Id</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesImage">Properties Image</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesMD5">Properties MD 5</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesName">Properties Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesType">Properties Type</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.requireMin">Require Min</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.totalAmount">Total Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.totalAmountNDT">Total Amount NDT</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.shoppingCart">Shopping Cart</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.shoppingCartItem.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shoppingCartItemList.map((shoppingCartItem, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${shoppingCartItem.id}`} color="link" size="sm">
                      {shoppingCartItem.id}
                    </Button>
                  </td>
                  <td>{shoppingCartItem.itemId}</td>
                  <td>{shoppingCartItem.itemImage}</td>
                  <td>{shoppingCartItem.itemLink}</td>
                  <td>{shoppingCartItem.itemPrice}</td>
                  <td>{shoppingCartItem.itemPriceNDT}</td>
                  <td>{shoppingCartItem.itemNote}</td>
                  <td>{shoppingCartItem.propertiesId}</td>
                  <td>{shoppingCartItem.propertiesImage}</td>
                  <td>{shoppingCartItem.propertiesMD5}</td>
                  <td>{shoppingCartItem.propertiesName}</td>
                  <td>{shoppingCartItem.propertiesType}</td>
                  <td>{shoppingCartItem.quantity}</td>
                  <td>{shoppingCartItem.requireMin}</td>
                  <td>{shoppingCartItem.totalAmount}</td>
                  <td>{shoppingCartItem.totalAmountNDT}</td>
                  <td>
                    <TextFormat type="date" value={shoppingCartItem.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shoppingCartItem.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {shoppingCartItem.shoppingCartId ? (
                      <Link to={`shopping-cart/${shoppingCartItem.shoppingCartId}`}>{shoppingCartItem.shoppingCartId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{shoppingCartItem.createByLogin ? shoppingCartItem.createByLogin : ''}</td>
                  <td>{shoppingCartItem.updateByLogin ? shoppingCartItem.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${shoppingCartItem.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shoppingCartItem.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shoppingCartItem.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ shoppingCartItem }: IRootState) => ({
  shoppingCartItemList: shoppingCartItem.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShoppingCartItem);
