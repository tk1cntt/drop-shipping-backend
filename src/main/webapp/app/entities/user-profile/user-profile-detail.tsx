import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-profile.reducer';
import { IUserProfile } from 'app/shared/model/user-profile.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserProfileDetail extends React.Component<IUserProfileDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userProfileEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.userProfile.detail.title">UserProfile</Translate> [<b>{userProfileEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fullName">
                <Translate contentKey="dropshippingApp.userProfile.fullName">Full Name</Translate>
              </span>
            </dt>
            <dd>{userProfileEntity.fullName}</dd>
            <dt>
              <span id="gender">
                <Translate contentKey="dropshippingApp.userProfile.gender">Gender</Translate>
              </span>
            </dt>
            <dd>{userProfileEntity.gender}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="dropshippingApp.userProfile.email">Email</Translate>
              </span>
            </dt>
            <dd>{userProfileEntity.email}</dd>
            <dt>
              <span id="mobile">
                <Translate contentKey="dropshippingApp.userProfile.mobile">Mobile</Translate>
              </span>
            </dt>
            <dd>{userProfileEntity.mobile}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.userProfile.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={userProfileEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.userProfile.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={userProfileEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.userProfile.createBy">Create By</Translate>
            </dt>
            <dd>{userProfileEntity.createByLogin ? userProfileEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.userProfile.updateBy">Update By</Translate>
            </dt>
            <dd>{userProfileEntity.updateByLogin ? userProfileEntity.updateByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.userProfile.city">City</Translate>
            </dt>
            <dd>{userProfileEntity.cityName ? userProfileEntity.cityName : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.userProfile.district">District</Translate>
            </dt>
            <dd>{userProfileEntity.districtName ? userProfileEntity.districtName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-profile" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-profile/${userProfileEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userProfile }: IRootState) => ({
  userProfileEntity: userProfile.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserProfileDetail);
