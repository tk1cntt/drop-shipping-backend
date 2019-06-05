import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDistrict } from 'app/shared/model/district.model';
import { getEntities as getDistricts } from 'app/entities/district/district.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ward.reducer';
import { IWard } from 'app/shared/model/ward.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWardUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IWardUpdateState {
  isNew: boolean;
  districtId: string;
}

export class WardUpdate extends React.Component<IWardUpdateProps, IWardUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      districtId: '0',
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

    this.props.getDistricts();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { wardEntity } = this.props;
      const entity = {
        ...wardEntity,
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
    this.props.history.push('/entity/ward');
  };

  render() {
    const { wardEntity, districts, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.ward.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.ward.home.createOrEditLabel">Create or edit a Ward</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : wardEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="ward-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="ward-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="ward-name">
                    <Translate contentKey="dropshippingApp.ward.name">Name</Translate>
                  </Label>
                  <AvField id="ward-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="enabledLabel" check>
                    <AvInput id="ward-enabled" type="checkbox" className="form-control" name="enabled" />
                    <Translate contentKey="dropshippingApp.ward.enabled">Enabled</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="ward-createAt">
                    <Translate contentKey="dropshippingApp.ward.createAt">Create At</Translate>
                  </Label>
                  <AvField id="ward-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="ward-updateAt">
                    <Translate contentKey="dropshippingApp.ward.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="ward-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="ward-district">
                    <Translate contentKey="dropshippingApp.ward.district">District</Translate>
                  </Label>
                  <AvInput id="ward-district" type="select" className="form-control" name="districtId">
                    <option value="" key="0" />
                    {districts
                      ? districts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/ward" replace color="info">
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
  districts: storeState.district.entities,
  wardEntity: storeState.ward.entity,
  loading: storeState.ward.loading,
  updating: storeState.ward.updating,
  updateSuccess: storeState.ward.updateSuccess
});

const mapDispatchToProps = {
  getDistricts,
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
)(WardUpdate);
