import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrderPackage } from 'app/shared/model/order-package.model';
import { getEntities as getOrderPackages } from 'app/entities/order-package/order-package.reducer';
import { IWarehouse } from 'app/shared/model/warehouse.model';
import { getEntities as getWarehouses } from 'app/entities/warehouse/warehouse.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order-package-history.reducer';
import { IOrderPackageHistory } from 'app/shared/model/order-package-history.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderPackageHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrderPackageHistoryUpdateState {
  isNew: boolean;
  orderPackageId: string;
  warehouseId: string;
  createById: string;
  updateById: string;
}

export class OrderPackageHistoryUpdate extends React.Component<IOrderPackageHistoryUpdateProps, IOrderPackageHistoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      orderPackageId: '0',
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

    this.props.getOrderPackages();
    this.props.getWarehouses();
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { orderPackageHistoryEntity } = this.props;
      const entity = {
        ...orderPackageHistoryEntity,
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
    this.props.history.push('/entity/order-package-history');
  };

  render() {
    const { orderPackageHistoryEntity, orderPackages, warehouses, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.orderPackageHistory.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.orderPackageHistory.home.createOrEditLabel">
                Create or edit a OrderPackageHistory
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orderPackageHistoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="order-package-history-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="order-package-history-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="statusLabel" for="order-package-history-status">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.status">Status</Translate>
                  </Label>
                  <AvField id="order-package-history-status" type="text" name="status" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusNameLabel" for="order-package-history-statusName">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.statusName">Status Name</Translate>
                  </Label>
                  <AvField id="order-package-history-statusName" type="text" name="statusName" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusStyleLabel" for="order-package-history-statusStyle">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.statusStyle">Status Style</Translate>
                  </Label>
                  <AvField id="order-package-history-statusStyle" type="text" name="statusStyle" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="order-package-history-createAt">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.createAt">Create At</Translate>
                  </Label>
                  <AvField id="order-package-history-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="order-package-history-updateAt">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="order-package-history-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="order-package-history-orderPackage">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.orderPackage">Order Package</Translate>
                  </Label>
                  <AvInput id="order-package-history-orderPackage" type="select" className="form-control" name="orderPackageId">
                    <option value="" key="0" />
                    {orderPackages
                      ? orderPackages.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="order-package-history-warehouse">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.warehouse">Warehouse</Translate>
                  </Label>
                  <AvInput id="order-package-history-warehouse" type="select" className="form-control" name="warehouseId">
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
                  <Label for="order-package-history-createBy">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="order-package-history-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="order-package-history-updateBy">
                    <Translate contentKey="dropshippingApp.orderPackageHistory.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="order-package-history-updateBy" type="select" className="form-control" name="updateById">
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
                <Button tag={Link} id="cancel-save" to="/entity/order-package-history" replace color="info">
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
  orderPackages: storeState.orderPackage.entities,
  warehouses: storeState.warehouse.entities,
  users: storeState.userManagement.users,
  orderPackageHistoryEntity: storeState.orderPackageHistory.entity,
  loading: storeState.orderPackageHistory.loading,
  updating: storeState.orderPackageHistory.updating,
  updateSuccess: storeState.orderPackageHistory.updateSuccess
});

const mapDispatchToProps = {
  getOrderPackages,
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
)(OrderPackageHistoryUpdate);
