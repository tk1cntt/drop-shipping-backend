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
import { getEntity, updateEntity, createEntity, reset } from './order-transaction.reducer';
import { IOrderTransaction } from 'app/shared/model/order-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderTransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrderTransactionUpdateState {
  isNew: boolean;
  orderCartId: string;
  approverId: string;
  createById: string;
  updateById: string;
}

export class OrderTransactionUpdate extends React.Component<IOrderTransactionUpdateProps, IOrderTransactionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orderCartId: '0',
      approverId: '0',
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
      const { orderTransactionEntity } = this.props;
      const entity = {
        ...orderTransactionEntity,
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
    this.props.history.push('/entity/order-transaction');
  };

  render() {
    const { orderTransactionEntity, orderCarts, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.orderTransaction.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.orderTransaction.home.createOrEditLabel">Create or edit a OrderTransaction</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orderTransactionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="order-transaction-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="order-transaction-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="amountLabel" for="order-transaction-amount">
                    <Translate contentKey="dropshippingApp.orderTransaction.amount">Amount</Translate>
                  </Label>
                  <AvField id="order-transaction-amount" type="string" className="form-control" name="amount" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="order-transaction-note">
                    <Translate contentKey="dropshippingApp.orderTransaction.note">Note</Translate>
                  </Label>
                  <AvField id="order-transaction-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="order-transaction-status">
                    <Translate contentKey="dropshippingApp.orderTransaction.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="order-transaction-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && orderTransactionEntity.status) || 'DEPOSIT'}
                  >
                    <option value="DEPOSIT">
                      <Translate contentKey="dropshippingApp.TransactionType.DEPOSIT" />
                    </option>
                    <option value="ORDER_PAYMENT">
                      <Translate contentKey="dropshippingApp.TransactionType.ORDER_PAYMENT" />
                    </option>
                    <option value="REFUND">
                      <Translate contentKey="dropshippingApp.TransactionType.REFUND" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="orderDateLabel" for="order-transaction-orderDate">
                    <Translate contentKey="dropshippingApp.orderTransaction.orderDate">Order Date</Translate>
                  </Label>
                  <AvField id="order-transaction-orderDate" type="date" className="form-control" name="orderDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="order-transaction-createAt">
                    <Translate contentKey="dropshippingApp.orderTransaction.createAt">Create At</Translate>
                  </Label>
                  <AvField id="order-transaction-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="order-transaction-updateAt">
                    <Translate contentKey="dropshippingApp.orderTransaction.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="order-transaction-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="order-transaction-orderCart">
                    <Translate contentKey="dropshippingApp.orderTransaction.orderCart">Order Cart</Translate>
                  </Label>
                  <AvInput id="order-transaction-orderCart" type="select" className="form-control" name="orderCartId">
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
                  <Label for="order-transaction-approver">
                    <Translate contentKey="dropshippingApp.orderTransaction.approver">Approver</Translate>
                  </Label>
                  <AvInput id="order-transaction-approver" type="select" className="form-control" name="approverId">
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
                  <Label for="order-transaction-createBy">
                    <Translate contentKey="dropshippingApp.orderTransaction.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="order-transaction-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="order-transaction-updateBy">
                    <Translate contentKey="dropshippingApp.orderTransaction.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="order-transaction-updateBy" type="select" className="form-control" name="updateById">
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
                <Button tag={Link} id="cancel-save" to="/entity/order-transaction" replace color="info">
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
  orderTransactionEntity: storeState.orderTransaction.entity,
  loading: storeState.orderTransaction.loading,
  updating: storeState.orderTransaction.updating,
  updateSuccess: storeState.orderTransaction.updateSuccess
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
)(OrderTransactionUpdate);
