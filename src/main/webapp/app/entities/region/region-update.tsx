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
import { getEntity, updateEntity, createEntity, reset } from './region.reducer';
import { IRegion } from 'app/shared/model/region.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRegionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRegionUpdateState {
  isNew: boolean;
  idsuser: any[];
}

export class RegionUpdate extends React.Component<IRegionUpdateProps, IRegionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsuser: [],
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
      const { regionEntity } = this.props;
      const entity = {
        ...regionEntity,
        ...values,
        users: mapIdList(values.users)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/region');
  };

  render() {
    const { regionEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.region.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.region.home.createOrEditLabel">Create or edit a Region</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : regionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="region-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="region-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="region-name">
                    <Translate contentKey="dropshippingApp.region.name">Name</Translate>
                  </Label>
                  <AvField id="region-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="enabledLabel" check>
                    <AvInput id="region-enabled" type="checkbox" className="form-control" name="enabled" />
                    <Translate contentKey="dropshippingApp.region.enabled">Enabled</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="region-createAt">
                    <Translate contentKey="dropshippingApp.region.createAt">Create At</Translate>
                  </Label>
                  <AvField id="region-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="region-updateAt">
                    <Translate contentKey="dropshippingApp.region.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="region-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="region-user">
                    <Translate contentKey="dropshippingApp.region.user">User</Translate>
                  </Label>
                  <AvInput
                    id="region-user"
                    type="select"
                    multiple
                    className="form-control"
                    name="users"
                    value={regionEntity.users && regionEntity.users.map(e => e.id)}
                  >
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
                <Button tag={Link} id="cancel-save" to="/entity/region" replace color="info">
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
  regionEntity: storeState.region.entity,
  loading: storeState.region.loading,
  updating: storeState.region.updating,
  updateSuccess: storeState.region.updateSuccess
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
)(RegionUpdate);
