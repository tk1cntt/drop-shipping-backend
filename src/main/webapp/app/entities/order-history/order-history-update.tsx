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
import { getEntity, updateEntity, createEntity, reset } from './order-history.reducer';
import { IOrderHistory } from 'app/shared/model/order-history.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrderHistoryUpdateState {
  isNew: boolean;
  orderCartId: string;
  createById: string;
  updateById: string;
}

export class OrderHistoryUpdate extends React.Component<IOrderHistoryUpdateProps, IOrderHistoryUpdateState> {
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
      const { orderHistoryEntity } = this.props;
      const entity = {
        ...orderHistoryEntity,
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
    this.props.history.push('/entity/order-history');
  };

  render() {
    const { orderHistoryEntity, orderCarts, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.orderHistory.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.orderHistory.home.createOrEditLabel">Create or edit a OrderHistory</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orderHistoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="order-history-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="order-history-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel" for="order-history-status">
                    <Translate contentKey="dropshippingApp.orderHistory.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="order-history-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && orderHistoryEntity.status) || 'DEPOSITED'}
                  >
                    <option value="DEPOSITED">
                      <Translate contentKey="dropshippingApp.OrderStatus.DEPOSITED" />
                    </option>
                    <option value="ARE_BUYING">
                      <Translate contentKey="dropshippingApp.OrderStatus.ARE_BUYING" />
                    </option>
                    <option value="PURCHASED">
                      <Translate contentKey="dropshippingApp.OrderStatus.PURCHASED" />
                    </option>
                    <option value="SELLER_DELIVERY">
                      <Translate contentKey="dropshippingApp.OrderStatus.SELLER_DELIVERY" />
                    </option>
                    <option value="WAREHOUSE_CHINA">
                      <Translate contentKey="dropshippingApp.OrderStatus.WAREHOUSE_CHINA" />
                    </option>
                    <option value="DELIVERING_CHINA_VIETNAM">
                      <Translate contentKey="dropshippingApp.OrderStatus.DELIVERING_CHINA_VIETNAM" />
                    </option>
                    <option value="WAREHOUSE_VIETNAM">
                      <Translate contentKey="dropshippingApp.OrderStatus.WAREHOUSE_VIETNAM" />
                    </option>
                    <option value="DELIVERY_REQUIREMENTS">
                      <Translate contentKey="dropshippingApp.OrderStatus.DELIVERY_REQUIREMENTS" />
                    </option>
                    <option value="DELIVERING_VIETNAM">
                      <Translate contentKey="dropshippingApp.OrderStatus.DELIVERING_VIETNAM" />
                    </option>
                    <option value="DELIVERED">
                      <Translate contentKey="dropshippingApp.OrderStatus.DELIVERED" />
                    </option>
                    <option value="CANCELLED">
                      <Translate contentKey="dropshippingApp.OrderStatus.CANCELLED" />
                    </option>
                    <option value="LOST">
                      <Translate contentKey="dropshippingApp.OrderStatus.LOST" />
                    </option>
                    <option value="RETURN">
                      <Translate contentKey="dropshippingApp.OrderStatus.RETURN" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="order-history-description">
                    <Translate contentKey="dropshippingApp.orderHistory.description">Description</Translate>
                  </Label>
                  <AvField id="order-history-description" type="text" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="order-history-createAt">
                    <Translate contentKey="dropshippingApp.orderHistory.createAt">Create At</Translate>
                  </Label>
                  <AvField id="order-history-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="order-history-orderCart">
                    <Translate contentKey="dropshippingApp.orderHistory.orderCart">Order Cart</Translate>
                  </Label>
                  <AvInput id="order-history-orderCart" type="select" className="form-control" name="orderCartId">
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
                  <Label for="order-history-createBy">
                    <Translate contentKey="dropshippingApp.orderHistory.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="order-history-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="order-history-updateBy">
                    <Translate contentKey="dropshippingApp.orderHistory.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="order-history-updateBy" type="select" className="form-control" name="updateById">
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
                <Button tag={Link} id="cancel-save" to="/entity/order-history" replace color="info">
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
  orderHistoryEntity: storeState.orderHistory.entity,
  loading: storeState.orderHistory.loading,
  updating: storeState.orderHistory.updating,
  updateSuccess: storeState.orderHistory.updateSuccess
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
)(OrderHistoryUpdate);
