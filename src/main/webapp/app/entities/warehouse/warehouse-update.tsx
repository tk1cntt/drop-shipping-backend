import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './warehouse.reducer';
import { IWarehouse } from 'app/shared/model/warehouse.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWarehouseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWarehouseUpdateState {
  isNew: boolean;
}

export class WarehouseUpdate extends React.Component<IWarehouseUpdateProps, IWarehouseUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { warehouseEntity } = this.props;
      const entity = {
        ...warehouseEntity,
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
    this.props.history.push('/entity/warehouse');
  };

  render() {
    const { warehouseEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.warehouse.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.warehouse.home.createOrEditLabel">Create or edit a Warehouse</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : warehouseEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="warehouse-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="warehouse-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="warehouse-name">
                    <Translate contentKey="dropshippingApp.warehouse.name">Name</Translate>
                  </Label>
                  <AvField id="warehouse-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="warehouse-address">
                    <Translate contentKey="dropshippingApp.warehouse.address">Address</Translate>
                  </Label>
                  <AvField id="warehouse-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="warehouse-createAt">
                    <Translate contentKey="dropshippingApp.warehouse.createAt">Create At</Translate>
                  </Label>
                  <AvField id="warehouse-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="warehouse-updateAt">
                    <Translate contentKey="dropshippingApp.warehouse.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="warehouse-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/warehouse" replace color="info">
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
  warehouseEntity: storeState.warehouse.entity,
  loading: storeState.warehouse.loading,
  updating: storeState.warehouse.updating,
  updateSuccess: storeState.warehouse.updateSuccess
});

const mapDispatchToProps = {
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
)(WarehouseUpdate);
