import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-package.reducer';
import { IOrderPackage } from 'app/shared/model/order-package.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderPackageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrderPackageDetail extends React.Component<IOrderPackageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orderPackageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.orderPackage.detail.title">OrderPackage</Translate> [<b>{orderPackageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ladingCode">
                <Translate contentKey="dropshippingApp.orderPackage.ladingCode">Lading Code</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.ladingCode}</dd>
            <dt>
              <span id="heightPackage">
                <Translate contentKey="dropshippingApp.orderPackage.heightPackage">Height Package</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.heightPackage}</dd>
            <dt>
              <span id="widthPackage">
                <Translate contentKey="dropshippingApp.orderPackage.widthPackage">Width Package</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.widthPackage}</dd>
            <dt>
              <span id="lengthPackage">
                <Translate contentKey="dropshippingApp.orderPackage.lengthPackage">Length Package</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.lengthPackage}</dd>
            <dt>
              <span id="netWeight">
                <Translate contentKey="dropshippingApp.orderPackage.netWeight">Net Weight</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.netWeight}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="dropshippingApp.orderPackage.status">Status</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.status}</dd>
            <dt>
              <span id="statusName">
                <Translate contentKey="dropshippingApp.orderPackage.statusName">Status Name</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.statusName}</dd>
            <dt>
              <span id="statusStyle">
                <Translate contentKey="dropshippingApp.orderPackage.statusStyle">Status Style</Translate>
              </span>
            </dt>
            <dd>{orderPackageEntity.statusStyle}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.orderPackage.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderPackageEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.orderPackage.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderPackageEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackage.orderCart">Order Cart</Translate>
            </dt>
            <dd>{orderPackageEntity.orderCartId ? orderPackageEntity.orderCartId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackage.warehouse">Warehouse</Translate>
            </dt>
            <dd>{orderPackageEntity.warehouseId ? orderPackageEntity.warehouseId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackage.createBy">Create By</Translate>
            </dt>
            <dd>{orderPackageEntity.createByLogin ? orderPackageEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackage.updateBy">Update By</Translate>
            </dt>
            <dd>{orderPackageEntity.updateByLogin ? orderPackageEntity.updateByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackage.delivery">Delivery</Translate>
            </dt>
            <dd>{orderPackageEntity.deliveryId ? orderPackageEntity.deliveryId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/order-package" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/order-package/${orderPackageEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orderPackage }: IRootState) => ({
  orderPackageEntity: orderPackage.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderPackageDetail);
