import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './delivery.reducer';
import { IDelivery } from 'app/shared/model/delivery.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeliveryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Delivery extends React.Component<IDeliveryProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { deliveryList, match } = this.props;
    return (
      <div>
        <h2 id="delivery-heading">
          <Translate contentKey="dropshippingApp.delivery.home.title">Deliveries</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.delivery.home.createLabel">Create new Delivery</Translate>
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
                  <Translate contentKey="dropshippingApp.delivery.deliveryMethod">Delivery Method</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.deliveryMethodName">Delivery Method Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.exportTime">Export Time</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.packedTime">Packed Time</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.statusName">Status Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.statusStyle">Status Style</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.totalWeight">Total Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.updateAt">Update At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.warehouse">Warehouse</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.createBy">Create By</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.delivery.updateBy">Update By</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {deliveryList.map((delivery, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${delivery.id}`} color="link" size="sm">
                      {delivery.id}
                    </Button>
                  </td>
                  <td>{delivery.deliveryMethod}</td>
                  <td>{delivery.deliveryMethodName}</td>
                  <td>
                    <TextFormat type="date" value={delivery.exportTime} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={delivery.packedTime} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{delivery.status}</td>
                  <td>{delivery.statusName}</td>
                  <td>{delivery.statusStyle}</td>
                  <td>{delivery.totalWeight}</td>
                  <td>
                    <TextFormat type="date" value={delivery.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={delivery.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{delivery.warehouseId ? <Link to={`warehouse/${delivery.warehouseId}`}>{delivery.warehouseId}</Link> : ''}</td>
                  <td>{delivery.createByLogin ? delivery.createByLogin : ''}</td>
                  <td>{delivery.updateByLogin ? delivery.updateByLogin : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${delivery.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${delivery.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${delivery.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ delivery }: IRootState) => ({
  deliveryList: delivery.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Delivery);
