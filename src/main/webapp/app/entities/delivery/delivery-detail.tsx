import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './delivery.reducer';
import { IDelivery } from 'app/shared/model/delivery.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeliveryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DeliveryDetail extends React.Component<IDeliveryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { deliveryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.delivery.detail.title">Delivery</Translate> [<b>{deliveryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="deliveryMethod">
                <Translate contentKey="dropshippingApp.delivery.deliveryMethod">Delivery Method</Translate>
              </span>
            </dt>
            <dd>{deliveryEntity.deliveryMethod}</dd>
            <dt>
              <span id="deliveryMethodName">
                <Translate contentKey="dropshippingApp.delivery.deliveryMethodName">Delivery Method Name</Translate>
              </span>
            </dt>
            <dd>{deliveryEntity.deliveryMethodName}</dd>
            <dt>
              <span id="exportTime">
                <Translate contentKey="dropshippingApp.delivery.exportTime">Export Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={deliveryEntity.exportTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="packedTime">
                <Translate contentKey="dropshippingApp.delivery.packedTime">Packed Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={deliveryEntity.packedTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="status">
                <Translate contentKey="dropshippingApp.delivery.status">Status</Translate>
              </span>
            </dt>
            <dd>{deliveryEntity.status}</dd>
            <dt>
              <span id="statusName">
                <Translate contentKey="dropshippingApp.delivery.statusName">Status Name</Translate>
              </span>
            </dt>
            <dd>{deliveryEntity.statusName}</dd>
            <dt>
              <span id="statusStyle">
                <Translate contentKey="dropshippingApp.delivery.statusStyle">Status Style</Translate>
              </span>
            </dt>
            <dd>{deliveryEntity.statusStyle}</dd>
            <dt>
              <span id="totalWeight">
                <Translate contentKey="dropshippingApp.delivery.totalWeight">Total Weight</Translate>
              </span>
            </dt>
            <dd>{deliveryEntity.totalWeight}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.delivery.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={deliveryEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.delivery.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={deliveryEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.delivery.warehouse">Warehouse</Translate>
            </dt>
            <dd>{deliveryEntity.warehouseId ? deliveryEntity.warehouseId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.delivery.createBy">Create By</Translate>
            </dt>
            <dd>{deliveryEntity.createByLogin ? deliveryEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.delivery.updateBy">Update By</Translate>
            </dt>
            <dd>{deliveryEntity.updateByLogin ? deliveryEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/delivery" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/delivery/${deliveryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ delivery }: IRootState) => ({
  deliveryEntity: delivery.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeliveryDetail);
