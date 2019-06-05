import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './warehouse.reducer';
import { IWarehouse } from 'app/shared/model/warehouse.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWarehouseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Warehouse extends React.Component<IWarehouseProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { warehouseList, match } = this.props;
    return (
      <div>
        <h2 id="warehouse-heading">
          <Translate contentKey="dropshippingApp.warehouse.home.title">Warehouses</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dropshippingApp.warehouse.home.createLabel">Create new Warehouse</Translate>
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
                  <Translate contentKey="dropshippingApp.warehouse.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.warehouse.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.warehouse.createAt">Create At</Translate>
                </th>
                <th>
                  <Translate contentKey="dropshippingApp.warehouse.updateAt">Update At</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {warehouseList.map((warehouse, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${warehouse.id}`} color="link" size="sm">
                      {warehouse.id}
                    </Button>
                  </td>
                  <td>{warehouse.name}</td>
                  <td>{warehouse.address}</td>
                  <td>
                    <TextFormat type="date" value={warehouse.createAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={warehouse.updateAt} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${warehouse.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${warehouse.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${warehouse.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ warehouse }: IRootState) => ({
  warehouseList: warehouse.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Warehouse);
