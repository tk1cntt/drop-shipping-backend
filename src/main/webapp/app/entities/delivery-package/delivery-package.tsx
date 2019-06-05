import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './delivery-package.reducer';
import { IDeliveryPackage } from 'app/shared/model/delivery-package.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeliveryPackageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class DeliveryPackage extends React.Component<IDeliveryPackageProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { deliveryPackageList, match } = this.props;
    return (
      <div>
        <h2 id="delivery-package-heading">
          <Translate contentKey="dropshippingApp.deliveryPackage.home.title">Delivery Packages</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.deliveryPackage.home.createLabel">Create new Delivery Package</Translate>
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
                  <Translate contentKey="dropshippingApp.deliveryPackage.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.deliveryPackage.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.deliveryPackage.delivery">Delivery</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.deliveryPackage.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.deliveryPackage.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {deliveryPackageList.map((deliveryPackage, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${deliveryPackage.id}`} color="link" size="sm">
                      {deliveryPackage.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={deliveryPackage.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={deliveryPackage.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {deliveryPackage.deliveryId ? (
                      <Link to={`delivery/${deliveryPackage.deliveryId}`}>{deliveryPackage.deliveryId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{deliveryPackage.createByLogin ? deliveryPackage.createByLogin : ''}</td>
                  <td>{deliveryPackage.updateByLogin ? deliveryPackage.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${deliveryPackage.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${deliveryPackage.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${deliveryPackage.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ deliveryPackage }: IRootState) => ({
  deliveryPackageList: deliveryPackage.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeliveryPackage);
