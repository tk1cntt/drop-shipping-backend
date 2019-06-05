import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDelivery } from 'app/shared/model/delivery.model';
import { getEntities as getDeliveries } from 'app/entities/delivery/delivery.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './delivery-package.reducer';
import { IDeliveryPackage } from 'app/shared/model/delivery-package.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDeliveryPackageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDeliveryPackageUpdateState {
  isNew: boolean;
  deliveryId: string;
  createById: string;
  updateById: string;
}

export class DeliveryPackageUpdate extends React.Component<IDeliveryPackageUpdateProps, IDeliveryPackageUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      deliveryId: '0',
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

    this.props.getDeliveries();
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { deliveryPackageEntity } = this.props;
      const entity = {
        ...deliveryPackageEntity,
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
    this.props.history.push('/entity/delivery-package');
  };

  render() {
    const { deliveryPackageEntity, deliveries, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.deliveryPackage.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.deliveryPackage.home.createOrEditLabel">Create or edit a DeliveryPackage</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : deliveryPackageEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="delivery-package-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="delivery-package-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="createAtLabel" for="delivery-package-createAt">
                    <Translate contentKey="dropshippingApp.deliveryPackage.createAt">Create At</Translate>
                  </Label>
                  <AvField id="delivery-package-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="delivery-package-updateAt">
                    <Translate contentKey="dropshippingApp.deliveryPackage.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="delivery-package-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="delivery-package-delivery">
                    <Translate contentKey="dropshippingApp.deliveryPackage.delivery">Delivery</Translate>
                  </Label>
                  <AvInput id="delivery-package-delivery" type="select" className="form-control" name="deliveryId">
                    <option value="" key="0" />
                    {deliveries
                      ? deliveries.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="delivery-package-createBy">
                    <Translate contentKey="dropshippingApp.deliveryPackage.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="delivery-package-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="delivery-package-updateBy">
                    <Translate contentKey="dropshippingApp.deliveryPackage.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="delivery-package-updateBy" type="select" className="form-control" name="updateById">
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
                <Button tag={Link} id="cancel-save" to="/entity/delivery-package" replace color="info">
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
  deliveries: storeState.delivery.entities,
  users: storeState.userManagement.users,
  deliveryPackageEntity: storeState.deliveryPackage.entity,
  loading: storeState.deliveryPackage.loading,
  updating: storeState.deliveryPackage.updating,
  updateSuccess: storeState.deliveryPackage.updateSuccess
});

const mapDispatchToProps = {
  getDeliveries,
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
)(DeliveryPackageUpdate);
