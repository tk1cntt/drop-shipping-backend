import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shopping-cart.reducer';
import { IShoppingCart } from 'app/shared/model/shopping-cart.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShoppingCartUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShoppingCartUpdateState {
  isNew: boolean;
  createById: string;
  updateById: string;
}

export class ShoppingCartUpdate extends React.Component<IShoppingCartUpdateProps, IShoppingCartUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { shoppingCartEntity } = this.props;
      const entity = {
        ...shoppingCartEntity,
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
    this.props.history.push('/entity/shopping-cart');
  };

  render() {
    const { shoppingCartEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.shoppingCart.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.shoppingCart.home.createOrEditLabel">Create or edit a ShoppingCart</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : shoppingCartEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="shopping-cart-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="shopping-cart-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="aliwangwangLabel" for="shopping-cart-aliwangwang">
                    <Translate contentKey="dropshippingApp.shoppingCart.aliwangwang">Aliwangwang</Translate>
                  </Label>
                  <AvField id="shopping-cart-aliwangwang" type="text" name="aliwangwang" />
                </AvGroup>
                <AvGroup>
                  <Label id="depositAmountLabel" for="shopping-cart-depositAmount">
                    <Translate contentKey="dropshippingApp.shoppingCart.depositAmount">Deposit Amount</Translate>
                  </Label>
                  <AvField id="shopping-cart-depositAmount" type="string" className="form-control" name="depositAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="depositRatioLabel" for="shopping-cart-depositRatio">
                    <Translate contentKey="dropshippingApp.shoppingCart.depositRatio">Deposit Ratio</Translate>
                  </Label>
                  <AvField id="shopping-cart-depositRatio" type="string" className="form-control" name="depositRatio" />
                </AvGroup>
                <AvGroup>
                  <Label id="serviceFeeLabel" for="shopping-cart-serviceFee">
                    <Translate contentKey="dropshippingApp.shoppingCart.serviceFee">Service Fee</Translate>
                  </Label>
                  <AvField id="shopping-cart-serviceFee" type="string" className="form-control" name="serviceFee" />
                </AvGroup>
                <AvGroup>
                  <Label id="serviceFeeDiscountLabel" for="shopping-cart-serviceFeeDiscount">
                    <Translate contentKey="dropshippingApp.shoppingCart.serviceFeeDiscount">Service Fee Discount</Translate>
                  </Label>
                  <AvField id="shopping-cart-serviceFeeDiscount" type="string" className="form-control" name="serviceFeeDiscount" />
                </AvGroup>
                <AvGroup>
                  <Label id="itemCheckingLabel" check>
                    <AvInput id="shopping-cart-itemChecking" type="checkbox" className="form-control" name="itemChecking" />
                    <Translate contentKey="dropshippingApp.shoppingCart.itemChecking">Item Checking</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="itemWoodCratingLabel" check>
                    <AvInput id="shopping-cart-itemWoodCrating" type="checkbox" className="form-control" name="itemWoodCrating" />
                    <Translate contentKey="dropshippingApp.shoppingCart.itemWoodCrating">Item Wood Crating</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="shopIdLabel" for="shopping-cart-shopId">
                    <Translate contentKey="dropshippingApp.shoppingCart.shopId">Shop Id</Translate>
                  </Label>
                  <AvField id="shopping-cart-shopId" type="text" name="shopId" />
                </AvGroup>
                <AvGroup>
                  <Label id="shopLinkLabel" for="shopping-cart-shopLink">
                    <Translate contentKey="dropshippingApp.shoppingCart.shopLink">Shop Link</Translate>
                  </Label>
                  <AvField id="shopping-cart-shopLink" type="text" name="shopLink" />
                </AvGroup>
                <AvGroup>
                  <Label id="shopNameLabel" for="shopping-cart-shopName">
                    <Translate contentKey="dropshippingApp.shoppingCart.shopName">Shop Name</Translate>
                  </Label>
                  <AvField id="shopping-cart-shopName" type="text" name="shopName" />
                </AvGroup>
                <AvGroup>
                  <Label id="shopNoteLabel" for="shopping-cart-shopNote">
                    <Translate contentKey="dropshippingApp.shoppingCart.shopNote">Shop Note</Translate>
                  </Label>
                  <AvField id="shopping-cart-shopNote" type="text" name="shopNote" />
                </AvGroup>
                <AvGroup>
                  <Label id="sourceDataLabel" for="shopping-cart-sourceData">
                    <Translate contentKey="dropshippingApp.shoppingCart.sourceData">Source Data</Translate>
                  </Label>
                  <AvField id="shopping-cart-sourceData" type="text" name="sourceData" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalAmountLabel" for="shopping-cart-totalAmount">
                    <Translate contentKey="dropshippingApp.shoppingCart.totalAmount">Total Amount</Translate>
                  </Label>
                  <AvField id="shopping-cart-totalAmount" type="string" className="form-control" name="totalAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalLinkLabel" for="shopping-cart-totalLink">
                    <Translate contentKey="dropshippingApp.shoppingCart.totalLink">Total Link</Translate>
                  </Label>
                  <AvField id="shopping-cart-totalLink" type="string" className="form-control" name="totalLink" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalQuantityLabel" for="shopping-cart-totalQuantity">
                    <Translate contentKey="dropshippingApp.shoppingCart.totalQuantity">Total Quantity</Translate>
                  </Label>
                  <AvField id="shopping-cart-totalQuantity" type="string" className="form-control" name="totalQuantity" />
                </AvGroup>
                <AvGroup>
                  <Label id="finalAmountLabel" for="shopping-cart-finalAmount">
                    <Translate contentKey="dropshippingApp.shoppingCart.finalAmount">Final Amount</Translate>
                  </Label>
                  <AvField id="shopping-cart-finalAmount" type="string" className="form-control" name="finalAmount" />
                </AvGroup>
                <AvGroup>
                  <Label id="websiteLabel" for="shopping-cart-website">
                    <Translate contentKey="dropshippingApp.shoppingCart.website">Website</Translate>
                  </Label>
                  <AvField id="shopping-cart-website" type="text" name="website" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="shopping-cart-createAt">
                    <Translate contentKey="dropshippingApp.shoppingCart.createAt">Create At</Translate>
                  </Label>
                  <AvField id="shopping-cart-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="shopping-cart-updateAt">
                    <Translate contentKey="dropshippingApp.shoppingCart.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="shopping-cart-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="shopping-cart-createBy">
                    <Translate contentKey="dropshippingApp.shoppingCart.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="shopping-cart-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="shopping-cart-updateBy">
                    <Translate contentKey="dropshippingApp.shoppingCart.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="shopping-cart-updateBy" type="select" className="form-control" name="updateById">
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
                <Button tag={Link} id="cancel-save" to="/entity/shopping-cart" replace color="info">
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
  users: storeState.userManagement.users,
  shoppingCartEntity: storeState.shoppingCart.entity,
  loading: storeState.shoppingCart.loading,
  updating: storeState.shoppingCart.updating,
  updateSuccess: storeState.shoppingCart.updateSuccess
});

const mapDispatchToProps = {
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
)(ShoppingCartUpdate);
