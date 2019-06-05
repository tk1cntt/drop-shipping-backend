import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrderCart } from 'app/shared/model/order-cart.model';
import { getEntities as getOrderCarts } from 'app/entities/order-cart/order-cart.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order-item.reducer';
import { IOrderItem } from 'app/shared/model/order-item.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrderItemUpdateState {
  isNew: boolean;
  orderCartId: string;
  createById: string;
  updateById: string;
}

export class OrderItemUpdate extends React.Component<IOrderItemUpdateProps, IOrderItemUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orderCartId: '0',
      createById: '0',
      updateById: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getOrderCarts();
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { orderItemEntity } = this.props;
      const entity = {
        ...orderItemEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/order-item');
  };

  render() {
    const { orderItemEntity, orderCarts, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.orderItem.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.orderItem.home.createOrEditLabel">Create or edit a OrderItem</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orderItemEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="order-item-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="order-item-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="avatarLabel" for="order-item-avatar">
                    <Translate contentKey="dropshippingApp.orderItem.avatar">Avatar</Translate>
                  </Label>
                  <AvField id="order-item-avatar" type="text" name="avatar" />
                </AvGroup>
                <AvGroup>
                  <Label id="originLinkLabel" for="order-item-originLink">
                    <Translate contentKey="dropshippingApp.orderItem.originLink">Origin Link</Translate>
                  </Label>
                  <AvField id="order-item-originLink" type="text" name="originLink" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="order-item-name">
                    <Translate contentKey="dropshippingApp.orderItem.name">Name</Translate>
                  </Label>
                  <AvField id="order-item-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="order-item-note">
                    <Translate contentKey="dropshippingApp.orderItem.note">Note</Translate>
                  </Label>
                  <AvField id="order-item-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="order-item-price">
                    <Translate contentKey="dropshippingApp.orderItem.price">Price</Translate>
                  </Label>
                  <AvField id="order-item-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceNDTLabel" for="order-item-priceNDT">
                    <Translate contentKey="dropshippingApp.orderItem.priceNDT">Price NDT</Translate>
                  </Label>
                  <AvField id="order-item-priceNDT" type="string" className="form-control" name="priceNDT" />
                </AvGroup>
                <AvGroup>
                  <Label id="propertiesIdLabel" for="order-item-propertiesId">
                    <Translate contentKey="dropshippingApp.orderItem.propertiesId">Properties Id</Translate>
                  </Label>
                  <AvField id="order-item-propertiesId" type="text" name="propertiesId" />
                </AvGroup>
                <AvGroup>
                  <Label id="propertiesImageLabel" for="order-item-propertiesImage">
                    <Translate contentKey="dropshippingApp.orderItem.propertiesImage">Properties Image</Translate>
                  </Label>
                  <AvField id="order-item-propertiesImage" type="text" name="propertiesImage" />
                </AvGroup>
                <AvGroup>
                  <Label id="propertiesMD5Label" for="order-item-propertiesMD5">
                    <Translate contentKey="dropshippingApp.orderItem.propertiesMD5">Properties MD 5</Translate>
                  </Label>
                  <AvField id="order-item-propertiesMD5" type="text" name="propertiesMD5" />
                </AvGroup>
                <AvGroup>
                  <Label id="propertiesNameLabel" for="order-item-propertiesName">
                    <Translate contentKey="dropshippingApp.orderItem.propertiesName">Properties Name</Translate>
                  </Label>
                  <AvField id="order-item-propertiesName" type="text" name="propertiesName" />
                </AvGroup>
                <AvGroup>
                  <Label id="propertiesTypeLabel" for="order-item-propertiesType">
                    <Translate contentKey="dropshippingApp.orderItem.propertiesType">Properties Type</Translate>
                  </Label>
                  <AvField id="order-item-propertiesType" type="text" name="propertiesType" />
                </AvGroup>
                <AvGroup>
                  <Label id="quantityOrderLabel" for="order-item-quantityOrder">
                    <Translate contentKey="dropshippingApp.orderItem.quantityOrder">Quantity Order</Translate>
                  </Label>
                  <AvField id="order-item-quantityOrder" type="string" className="form-control" name="quantityOrder" />
                </AvGroup>
                <AvGroup>
                  <Label id="quantityPendingLabel" for="order-item-quantityPending">
                    <Translate contentKey="dropshippingApp.orderItem.quantityPending">Quantity Pending</Translate>
                  </Label>
                  <AvField id="order-item-quantityPending" type="string" className="form-control" name="quantityPending" />
                </AvGroup>
                <AvGroup>
                  <Label id="quantityReceivedLabel" for="order-item-quantityReceived">
                    <Translate contentKey="dropshippingApp.orderItem.quantityReceived">Quantity Received</Translate>
                  </Label>
                  <AvField id="order-item-quantityReceived" type="string" className="form-control" name="quantityReceived" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalPriceLabel" for="order-item-totalPrice">
                    <Translate contentKey="dropshippingApp.orderItem.totalPrice">Total Price</Translate>
                  </Label>
                  <AvField id="order-item-totalPrice" type="string" className="form-control" name="totalPrice" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalPriceNDTLabel" for="order-item-totalPriceNDT">
                    <Translate contentKey="dropshippingApp.orderItem.totalPriceNDT">Total Price NDT</Translate>
                  </Label>
                  <AvField id="order-item-totalPriceNDT" type="string" className="form-control" name="totalPriceNDT" />
                </AvGroup>
                <AvGroup>
                  <Label id="websiteLabel" for="order-item-website">
                    <Translate contentKey="dropshippingApp.orderItem.website">Website</Translate>
                  </Label>
                  <AvField id="order-item-website" type="text" name="website" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="order-item-createAt">
                    <Translate contentKey="dropshippingApp.orderItem.createAt">Create At</Translate>
                  </Label>
                  <AvField id="order-item-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="order-item-updateAt">
                    <Translate contentKey="dropshippingApp.orderItem.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="order-item-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="order-item-orderCart">
                    <Translate contentKey="dropshippingApp.orderItem.orderCart">Order Cart</Translate>
                  </Label>
                  <AvInput id="order-item-orderCart" type="select" className="form-control" name="orderCartId">
                    <option value="" key="0" />
                    {orderCarts
                      ? orderCarts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="order-item-createBy">
                    <Translate contentKey="dropshippingApp.orderItem.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="order-item-createBy" type="select" className="form-control" name="createById">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="order-item-updateBy">
                    <Translate contentKey="dropshippingApp.orderItem.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="order-item-updateBy" type="select" className="form-control" name="updateById">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/order-item" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  orderCarts: storeState.orderCart.entities,
  users: storeState.userManagement.users,
  orderItemEntity: storeState.orderItem.entity,
  loading: storeState.orderItem.loading,
  updating: storeState.orderItem.updating,
  updateSuccess: storeState.orderItem.updateSuccess
});

const mapDispatchToProps = {
  getOrderCarts,
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderItemUpdate);
