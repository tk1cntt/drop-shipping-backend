import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-history.reducer';
import { IOrderHistory } from 'app/shared/model/order-history.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrderHistoryDetail extends React.Component<IOrderHistoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orderHistoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.orderHistory.detail.title">OrderHistory</Translate> [<b>{orderHistoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="status">
                <Translate contentKey="dropshippingApp.orderHistory.status">Status</Translate>
              </span>
            </dt>
            <dd>{orderHistoryEntity.status}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="dropshippingApp.orderHistory.description">Description</Translate>
              </span>
            </dt>
            <dd>{orderHistoryEntity.description}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.orderHistory.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderHistoryEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderHistory.orderCart">Order Cart</Translate>
            </dt>
            <dd>{orderHistoryEntity.orderCartId ? orderHistoryEntity.orderCartId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderHistory.createBy">Create By</Translate>
            </dt>
            <dd>{orderHistoryEntity.createByLogin ? orderHistoryEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderHistory.updateBy">Update By</Translate>
            </dt>
            <dd>{orderHistoryEntity.updateByLogin ? orderHistoryEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/order-history" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/order-history/${orderHistoryEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orderHistory }: IRootState) => ({
  orderHistoryEntity: orderHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderHistoryDetail);
