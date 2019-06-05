import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWarehouse } from 'app/shared/model/warehouse.model';
import { getEntities as getWarehouses } from 'app/entities/warehouse/warehouse.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './delivery.reducer';
import { IDelivery } from 'app/shared/model/delivery.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDeliveryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDeliveryUpdateState {
  isNew: boolean;
  warehouseId: string;
  createById: string;
  updateById: string;
}

export class DeliveryUpdate extends React.Component<IDeliveryUpdateProps, IDeliveryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      warehouseId: '0',
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

    this.props.getWarehouses();
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { deliveryEntity } = this.props;
      const entity = {
        ...deliveryEntity,
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
    this.props.history.push('/entity/delivery');
  };

  render() {
    const { deliveryEntity, warehouses, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.delivery.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.delivery.home.createOrEditLabel">Create or edit a Delivery</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : deliveryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="delivery-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="delivery-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="deliveryMethodLabel" for="delivery-deliveryMethod">
                    <Translate contentKey="dropshippingApp.delivery.deliveryMethod">Delivery Method</Translate>
                  </Label>
                  <AvField id="delivery-deliveryMethod" type="text" name="deliveryMethod" />
                </AvGroup>
                <AvGroup>
                  <Label id="deliveryMethodNameLabel" for="delivery-deliveryMethodName">
                    <Translate contentKey="dropshippingApp.delivery.deliveryMethodName">Delivery Method Name</Translate>
                  </Label>
                  <AvField id="delivery-deliveryMethodName" type="text" name="deliveryMethodName" />
                </AvGroup>
                <AvGroup>
                  <Label id="exportTimeLabel" for="delivery-exportTime">
                    <Translate contentKey="dropshippingApp.delivery.exportTime">Export Time</Translate>
                  </Label>
                  <AvField id="delivery-exportTime" type="date" className="form-control" name="exportTime" />
                </AvGroup>
                <AvGroup>
                  <Label id="packedTimeLabel" for="delivery-packedTime">
                    <Translate contentKey="dropshippingApp.delivery.packedTime">Packed Time</Translate>
                  </Label>
                  <AvField id="delivery-packedTime" type="date" className="form-control" name="packedTime" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="delivery-status">
                    <Translate contentKey="dropshippingApp.delivery.status">Status</Translate>
                  </Label>
                  <AvField id="delivery-status" type="text" name="status" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusNameLabel" for="delivery-statusName">
                    <Translate contentKey="dropshippingApp.delivery.statusName">Status Name</Translate>
                  </Label>
                  <AvField id="delivery-statusName" type="text" name="statusName" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusStyleLabel" for="delivery-statusStyle">
                    <Translate contentKey="dropshippingApp.delivery.statusStyle">Status Style</Translate>
                  </Label>
                  <AvField id="delivery-statusStyle" type="text" name="statusStyle" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalWeightLabel" for="delivery-totalWeight">
                    <Translate contentKey="dropshippingApp.delivery.totalWeight">Total Weight</Translate>
                  </Label>
                  <AvField id="delivery-totalWeight" type="string" className="form-control" name="totalWeight" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="delivery-createAt">
                    <Translate contentKey="dropshippingApp.delivery.createAt">Create At</Translate>
                  </Label>
                  <AvField id="delivery-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="delivery-updateAt">
                    <Translate contentKey="dropshippingApp.delivery.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="delivery-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="delivery-warehouse">
                    <Translate contentKey="dropshippingApp.delivery.warehouse">Warehouse</Translate>
                  </Label>
                  <AvInput id="delivery-warehouse" type="select" className="form-control" name="warehouseId">
                    <option value="" key="0" />
                    {warehouses
                      ? warehouses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="delivery-createBy">
                    <Translate contentKey="dropshippingApp.delivery.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="delivery-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="delivery-updateBy">
                    <Translate contentKey="dropshippingApp.delivery.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="delivery-updateBy" type="select" className="form-control" name="updateById">
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
                <Button tag={Link} id="cancel-save" to="/entity/delivery" replace color="info">
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
  warehouses: storeState.warehouse.entities,
  users: storeState.userManagement.users,
  deliveryEntity: storeState.delivery.entity,
  loading: storeState.delivery.loading,
  updating: storeState.delivery.updating,
  updateSuccess: storeState.delivery.updateSuccess
});

const mapDispatchToProps = {
  getWarehouses,
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
)(DeliveryUpdate);
