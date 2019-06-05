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
import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { IDistrict } from 'app/shared/model/district.model';
import { getEntities as getDistricts } from 'app/entities/district/district.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-profile.reducer';
import { IUserProfile } from 'app/shared/model/user-profile.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserProfileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserProfileUpdateState {
  isNew: boolean;
  createById: string;
  updateById: string;
  cityId: string;
  districtId: string;
}

export class UserProfileUpdate extends React.Component<IUserProfileUpdateProps, IUserProfileUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createById: '0',
      updateById: '0',
      cityId: '0',
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

    this.props.getUsers();
    this.props.getCities();
    this.props.getDistricts();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { userProfileEntity } = this.props;
      const entity = {
        ...userProfileEntity,
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
    this.props.history.push('/entity/user-profile');
  };

  render() {
    const { userProfileEntity, users, cities, districts, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.userProfile.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.userProfile.home.createOrEditLabel">Create or edit a UserProfile</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userProfileEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="user-profile-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="user-profile-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fullNameLabel" for="user-profile-fullName">
                    <Translate contentKey="dropshippingApp.userProfile.fullName">Full Name</Translate>
                  </Label>
                  <AvField id="user-profile-fullName" type="text" name="fullName" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel" for="user-profile-gender">
                    <Translate contentKey="dropshippingApp.userProfile.gender">Gender</Translate>
                  </Label>
                  <AvInput
                    id="user-profile-gender"
                    type="select"
                    className="form-control"
                    name="gender"
                    value={(!isNew && userProfileEntity.gender) || 'MALE'}
                  >
                    <option value="MALE">
                      <Translate contentKey="dropshippingApp.Gender.MALE" />
                    </option>
                    <option value="FEMALE">
                      <Translate contentKey="dropshippingApp.Gender.FEMALE" />
                    </option>
                    <option value="OTHER">
                      <Translate contentKey="dropshippingApp.Gender.OTHER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="user-profile-email">
                    <Translate contentKey="dropshippingApp.userProfile.email">Email</Translate>
                  </Label>
                  <AvField id="user-profile-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="mobileLabel" for="user-profile-mobile">
                    <Translate contentKey="dropshippingApp.userProfile.mobile">Mobile</Translate>
                  </Label>
                  <AvField id="user-profile-mobile" type="text" name="mobile" />
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="user-profile-createAt">
                    <Translate contentKey="dropshippingApp.userProfile.createAt">Create At</Translate>
                  </Label>
                  <AvField id="user-profile-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="user-profile-updateAt">
                    <Translate contentKey="dropshippingApp.userProfile.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="user-profile-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="user-profile-createBy">
                    <Translate contentKey="dropshippingApp.userProfile.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="user-profile-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="user-profile-updateBy">
                    <Translate contentKey="dropshippingApp.userProfile.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="user-profile-updateBy" type="select" className="form-control" name="updateById">
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
                  <Label for="user-profile-city">
                    <Translate contentKey="dropshippingApp.userProfile.city">City</Translate>
                  </Label>
                  <AvInput id="user-profile-city" type="select" className="form-control" name="cityId">
                    <option value="" key="0" />
                    {cities
                      ? cities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="user-profile-district">
                    <Translate contentKey="dropshippingApp.userProfile.district">District</Translate>
                  </Label>
                  <AvInput id="user-profile-district" type="select" className="form-control" name="districtId">
                    <option value="" key="0" />
                    {districts
                      ? districts.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-profile" replace color="info">
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
  cities: storeState.city.entities,
  districts: storeState.district.entities,
  userProfileEntity: storeState.userProfile.entity,
  loading: storeState.userProfile.loading,
  updating: storeState.userProfile.updating,
  updateSuccess: storeState.userProfile.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getCities,
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
)(UserProfileUpdate);
