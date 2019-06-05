import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserProfile } from 'app/shared/model/user-profile.model';
import { getEntities as getUserProfiles } from 'app/entities/user-profile/user-profile.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICity } from 'app/shared/model/city.model';
import { getEntities as getCities } from 'app/entities/city/city.reducer';
import { IDistrict } from 'app/shared/model/district.model';
import { getEntities as getDistricts } from 'app/entities/district/district.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-shipping-address.reducer';
import { IUserShippingAddress } from 'app/shared/model/user-shipping-address.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserShippingAddressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserShippingAddressUpdateState {
  isNew: boolean;
  userProfileId: string;
  createById: string;
  updateById: string;
  cityId: string;
  districtId: string;
}

export class UserShippingAddressUpdate extends React.Component<IUserShippingAddressUpdateProps, IUserShippingAddressUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userProfileId: '0',
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

    this.props.getUserProfiles();
    this.props.getUsers();
    this.props.getCities();
    this.props.getDistricts();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { userShippingAddressEntity } = this.props;
      const entity = {
        ...userShippingAddressEntity,
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
    this.props.history.push('/entity/user-shipping-address');
  };

  render() {
    const { userShippingAddressEntity, userProfiles, users, cities, districts, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="dropshippingApp.userShippingAddress.home.createOrEditLabel">
              <Translate contentKey="dropshippingApp.userShippingAddress.home.createOrEditLabel">
                Create or edit a UserShippingAddress
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userShippingAddressEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="user-shipping-address-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="user-shipping-address-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="user-shipping-address-name">
                    <Translate contentKey="dropshippingApp.userShippingAddress.name">Name</Translate>
                  </Label>
                  <AvField id="user-shipping-address-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="user-shipping-address-address">
                    <Translate contentKey="dropshippingApp.userShippingAddress.address">Address</Translate>
                  </Label>
                  <AvField id="user-shipping-address-address" type="text" name="address" />
                </AvGroup>
                <AvGroup>
                  <Label id="mobileLabel" for="user-shipping-address-mobile">
                    <Translate contentKey="dropshippingApp.userShippingAddress.mobile">Mobile</Translate>
                  </Label>
                  <AvField id="user-shipping-address-mobile" type="text" name="mobile" />
                </AvGroup>
                <AvGroup>
                  <Label id="noteLabel" for="user-shipping-address-note">
                    <Translate contentKey="dropshippingApp.userShippingAddress.note">Note</Translate>
                  </Label>
                  <AvField id="user-shipping-address-note" type="text" name="note" />
                </AvGroup>
                <AvGroup>
                  <Label id="isShippingAddressLabel" check>
                    <AvInput
                      id="user-shipping-address-isShippingAddress"
                      type="checkbox"
                      className="form-control"
                      name="isShippingAddress"
                    />
                    <Translate contentKey="dropshippingApp.userShippingAddress.isShippingAddress">Is Shipping Address</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="createAtLabel" for="user-shipping-address-createAt">
                    <Translate contentKey="dropshippingApp.userShippingAddress.createAt">Create At</Translate>
                  </Label>
                  <AvField id="user-shipping-address-createAt" type="date" className="form-control" name="createAt" />
                </AvGroup>
                <AvGroup>
                  <Label id="updateAtLabel" for="user-shipping-address-updateAt">
                    <Translate contentKey="dropshippingApp.userShippingAddress.updateAt">Update At</Translate>
                  </Label>
                  <AvField id="user-shipping-address-updateAt" type="date" className="form-control" name="updateAt" />
                </AvGroup>
                <AvGroup>
                  <Label for="user-shipping-address-userProfile">
                    <Translate contentKey="dropshippingApp.userShippingAddress.userProfile">User Profile</Translate>
                  </Label>
                  <AvInput id="user-shipping-address-userProfile" type="select" className="form-control" name="userProfileId">
                    <option value="" key="0" />
                    {userProfiles
                      ? userProfiles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="user-shipping-address-createBy">
                    <Translate contentKey="dropshippingApp.userShippingAddress.createBy">Create By</Translate>
                  </Label>
                  <AvInput id="user-shipping-address-createBy" type="select" className="form-control" name="createById">
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
                  <Label for="user-shipping-address-updateBy">
                    <Translate contentKey="dropshippingApp.userShippingAddress.updateBy">Update By</Translate>
                  </Label>
                  <AvInput id="user-shipping-address-updateBy" type="select" className="form-control" name="updateById">
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
                  <Label for="user-shipping-address-city">
                    <Translate contentKey="dropshippingApp.userShippingAddress.city">City</Translate>
                  </Label>
                  <AvInput id="user-shipping-address-city" type="select" className="form-control" name="cityId">
                    <option value="" key="0" />
                    {cities
                      ? cities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="user-shipping-address-district">
                    <Translate contentKey="dropshippingApp.userShippingAddress.district">District</Translate>
                  </Label>
                  <AvInput id="user-shipping-address-district" type="select" className="form-control" name="districtId">
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
                <Button tag={Link} id="cancel-save" to="/entity/user-shipping-address" replace color="info">
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
  userProfiles: storeState.userProfile.entities,
  users: storeState.userManagement.users,
  cities: storeState.city.entities,
  districts: storeState.district.entities,
  userShippingAddressEntity: storeState.userShippingAddress.entity,
  loading: storeState.userShippingAddress.loading,
  updating: storeState.userShippingAddress.updating,
  updateSuccess: storeState.userShippingAddress.updateSuccess
});

const mapDispatchToProps = {
  getUserProfiles,
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
)(UserShippingAddressUpdate);
