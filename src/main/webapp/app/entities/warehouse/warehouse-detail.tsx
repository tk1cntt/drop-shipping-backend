import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './warehouse.reducer';
import { IWarehouse } from 'app/shared/model/warehouse.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWarehouseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WarehouseDetail extends React.Component<IWarehouseDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { warehouseEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.warehouse.detail.title">Warehouse</Translate> [<b>{warehouseEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="dropshippingApp.warehouse.name">Name</Translate>
              </span>
            </dt>
            <dd>{warehouseEntity.name}</dd>
            <dt>
              <span id="address">
                <Translate contentKey="dropshippingApp.warehouse.address">Address</Translate>
              </span>
            </dt>
            <dd>{warehouseEntity.address}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.warehouse.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={warehouseEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.warehouse.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={warehouseEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/warehouse" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/warehouse/${warehouseEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ warehouse }: IRootState) => ({
  warehouseEntity: warehouse.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WarehouseDetail);
