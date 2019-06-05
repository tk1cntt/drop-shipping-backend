import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-shipping-address.reducer';
import { IUserShippingAddress } from 'app/shared/model/user-shipping-address.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserShippingAddressProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class UserShippingAddress extends React.Component<IUserShippingAddressProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { userShippingAddressList, match } = this.props;
    return (
      <div>
        <h2 id="user-shipping-address-heading">
          <Translate contentKey="dropshippingApp.userShippingAddress.home.title">User Shipping Addresses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.userShippingAddress.home.createLabel">Create new User Shipping Address</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.mobile">Mobile</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.note">Note</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.isShippingAddress">Is Shipping Address</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.userProfile">User Profile</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.updateBy">Update By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.city">City</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.userShippingAddress.district">District</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userShippingAddressList.map((userShippingAddress, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${userShippingAddress.id}`} color="link" size="sm">
                      {userShippingAddress.id}
                    </Button>
                  </td>
                  <td>{userShippingAddress.name}</td>
                  <td>{userShippingAddress.address}</td>
                  <td>{userShippingAddress.mobile}</td>
                  <td>{userShippingAddress.note}</td>
                  <td>{userShippingAddress.isShippingAddress ? 'true' : 'false'}</td>
                  <td>
                    <TextFormat type="date" value={userShippingAddress.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={userShippingAddress.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {userShippingAddress.userProfileId ? (
                      <Link to={`user-profile/${userShippingAddress.userProfileId}`}>{userShippingAddress.userProfileId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{userShippingAddress.createByLogin ? userShippingAddress.createByLogin : ''}</td>
                  <td>{userShippingAddress.updateByLogin ? userShippingAddress.updateByLogin : ''}</td>
                  <td>
                    {userShippingAddress.cityId ? <Link to={`city/${userShippingAddress.cityId}`}>{userShippingAddress.cityId}</Link> : ''}
                  </td>
                  <td>
                    {userShippingAddress.districtId ? (
                      <Link to={`district/${userShippingAddress.districtId}`}>{userShippingAddress.districtId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userShippingAddress.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userShippingAddress.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userShippingAddress.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ userShippingAddress }: IRootState) => ({
  userShippingAddressList: userShippingAddress.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserShippingAddress);
