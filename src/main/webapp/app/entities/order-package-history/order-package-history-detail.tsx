import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-package-history.reducer';
import { IOrderPackageHistory } from 'app/shared/model/order-package-history.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderPackageHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrderPackageHistoryDetail extends React.Component<IOrderPackageHistoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orderPackageHistoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.orderPackageHistory.detail.title">OrderPackageHistory</Translate> [
            <b>{orderPackageHistoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="dropshippingApp.orderPackageHistory.status">Status</Translate>
              </span>
            </dt>
            <dd>{orderPackageHistoryEntity.status}</dd>
            <dt>
              <span id="statusName">
                <Translate contentKey="dropshippingApp.orderPackageHistory.statusName">Status Name</Translate>
              </span>
            </dt>
            <dd>{orderPackageHistoryEntity.statusName}</dd>
            <dt>
              <span id="statusStyle">
                <Translate contentKey="dropshippingApp.orderPackageHistory.statusStyle">Status Style</Translate>
              </span>
            </dt>
            <dd>{orderPackageHistoryEntity.statusStyle}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.orderPackageHistory.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderPackageHistoryEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.orderPackageHistory.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderPackageHistoryEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackageHistory.orderPackage">Order Package</Translate>
            </dt>
            <dd>{orderPackageHistoryEntity.orderPackageId ? orderPackageHistoryEntity.orderPackageId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackageHistory.warehouse">Warehouse</Translate>
            </dt>
            <dd>{orderPackageHistoryEntity.warehouseId ? orderPackageHistoryEntity.warehouseId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackageHistory.createBy">Create By</Translate>
            </dt>
            <dd>{orderPackageHistoryEntity.createByLogin ? orderPackageHistoryEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderPackageHistory.updateBy">Update By</Translate>
            </dt>
            <dd>{orderPackageHistoryEntity.updateByLogin ? orderPackageHistoryEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/order-package-history" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/order-package-history/${orderPackageHistoryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orderPackageHistory }: IRootState) => ({
  orderPackageHistoryEntity: orderPackageHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderPackageHistoryDetail);
