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
import { getEntity, updateEntity, createEntity, reset } from './order-comment.reducer';
import { IOrderComment } from 'app/shared/model/order-comment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderCommentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrderCommentUpdateState {
  isNew: boolean;
  orderCartId: string;
}

export class OrderCommentUpdate extends React.Component<IOrderCommentUpdateProps, IOrderCommentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orderCartId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { orderCommentEntity } = this.props;
      const entity = {
        ...orderCommentEntity,
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
    this.props.history.push('/entity/order-comment');
  };

  render() {
    const { orderCommentEntity, orderCarts, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.orderComment.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.orderComment.home.createOrEditLabel">Create or edit a OrderComment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orderCommentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="order-comment-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="order-comment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="messageLabel" for="order-comment-message">
                    <Translate contentKey="dropshippingApp.orderComment.message">Message</Translate>
                  </Label>
                  <AvField id="order-comment-message" type="text" name="message" />
                </AvGroup>
                <AvGroup>
                  <Label id="senderLabel" for="order-comment-sender">
                    <Translate contentKey="dropshippingApp.orderComment.sender">Sender</Translate>
                  </Label>
                  <AvField id="order-comment-sender" type="text" name="sender" />
                </AvGroup>
                <AvGroup>
                  <Label id="typeLabel" for="order-comment-type">
                    <Translate contentKey="dropshippingApp.orderComment.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="order-comment-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && orderCommentEntity.type) || 'CUSTOMER_LOG'}
                  >
                    <option value="CUSTOMER_LOG">
                      <Translate contentKey="dropshippingApp.CommentType.CUSTOMER_LOG" />
                    </option>
                    <option value="CUSTOMER_CHAT">
                      <Translate contentKey="dropshippingApp.CommentType.CUSTOMER_CHAT" />
                    </option>
                    <option value="STAFF_LOG">
                      <Translate contentKey="dropshippingApp.CommentType.STAFF_LOG" />
                    </option>
                    <option value="STAFF_CHAT">
                      <Translate contentKey="dropshippingApp.CommentType.STAFF_CHAT" />
                    </option>
                    <option value="SYSTEM_LOG">
                      <Translate contentKey="dropshippingApp.CommentType.SYSTEM_LOG" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="order-comment-createAt">
                    <Translate contentKey="dropshippingApp.orderComment.createAt">Create At</Translate>
                  </Label>
                  <AvField id="order-comment-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="order-comment-orderCart">
                    <Translate contentKey="dropshippingApp.orderComment.orderCart">Order Cart</Translate>
                  </Label>
                  <AvInput id="order-comment-orderCart" type="select" className="form-control" name="orderCartId">
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
                <Button tag={Link} id="cancel-save" to="/entity/order-comment" replace color="info">
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
  orderCommentEntity: storeState.orderComment.entity,
  loading: storeState.orderComment.loading,
  updating: storeState.orderComment.updating,
  updateSuccess: storeState.orderComment.updateSuccess
});

const mapDispatchToProps = {
  getOrderCarts,
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
)(OrderCommentUpdate);
