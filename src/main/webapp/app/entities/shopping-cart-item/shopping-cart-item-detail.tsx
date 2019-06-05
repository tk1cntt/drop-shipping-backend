import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shopping-cart-item.reducer';
import { IShoppingCartItem } from 'app/shared/model/shopping-cart-item.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShoppingCartItemDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShoppingCartItemDetail extends React.Component<IShoppingCartItemDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shoppingCartItemEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.shoppingCartItem.detail.title">ShoppingCartItem</Translate> [
            <b>{shoppingCartItemEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="itemId">
                <Translate contentKey="dropshippingApp.shoppingCartItem.itemId">Item Id</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.itemId}</dd>
            <dt>
              <span id="itemImage">
                <Translate contentKey="dropshippingApp.shoppingCartItem.itemImage">Item Image</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.itemImage}</dd>
            <dt>
              <span id="itemLink">
                <Translate contentKey="dropshippingApp.shoppingCartItem.itemLink">Item Link</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.itemLink}</dd>
            <dt>
              <span id="itemPrice">
                <Translate contentKey="dropshippingApp.shoppingCartItem.itemPrice">Item Price</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.itemPrice}</dd>
            <dt>
              <span id="itemPriceNDT">
                <Translate contentKey="dropshippingApp.shoppingCartItem.itemPriceNDT">Item Price NDT</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.itemPriceNDT}</dd>
            <dt>
              <span id="itemNote">
                <Translate contentKey="dropshippingApp.shoppingCartItem.itemNote">Item Note</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.itemNote}</dd>
            <dt>
              <span id="propertiesId">
                <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesId">Properties Id</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.propertiesId}</dd>
            <dt>
              <span id="propertiesImage">
                <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesImage">Properties Image</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.propertiesImage}</dd>
            <dt>
              <span id="propertiesMD5">
                <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesMD5">Properties MD 5</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.propertiesMD5}</dd>
            <dt>
              <span id="propertiesName">
                <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesName">Properties Name</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.propertiesName}</dd>
            <dt>
              <span id="propertiesType">
                <Translate contentKey="dropshippingApp.shoppingCartItem.propertiesType">Properties Type</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.propertiesType}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="dropshippingApp.shoppingCartItem.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.quantity}</dd>
            <dt>
              <span id="requireMin">
                <Translate contentKey="dropshippingApp.shoppingCartItem.requireMin">Require Min</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.requireMin}</dd>
            <dt>
              <span id="totalAmount">
                <Translate contentKey="dropshippingApp.shoppingCartItem.totalAmount">Total Amount</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.totalAmount}</dd>
            <dt>
              <span id="totalAmountNDT">
                <Translate contentKey="dropshippingApp.shoppingCartItem.totalAmountNDT">Total Amount NDT</Translate>
              </span>
            </dt>
            <dd>{shoppingCartItemEntity.totalAmountNDT}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.shoppingCartItem.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={shoppingCartItemEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.shoppingCartItem.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={shoppingCartItemEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.shoppingCartItem.shoppingCart">Shopping Cart</Translate>
            </dt>
            <dd>{shoppingCartItemEntity.shoppingCartId ? shoppingCartItemEntity.shoppingCartId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.shoppingCartItem.createBy">Create By</Translate>
            </dt>
            <dd>{shoppingCartItemEntity.createByLogin ? shoppingCartItemEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.shoppingCartItem.updateBy">Update By</Translate>
            </dt>
            <dd>{shoppingCartItemEntity.updateByLogin ? shoppingCartItemEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shopping-cart-item" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/shopping-cart-item/${shoppingCartItemEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ shoppingCartItem }: IRootState) => ({
  shoppingCartItemEntity: shoppingCartItem.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShoppingCartItemDetail);
