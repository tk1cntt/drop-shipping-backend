import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-transaction.reducer';
import { IOrderTransaction } from 'app/shared/model/order-transaction.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrderTransactionDetail extends React.Component<IOrderTransactionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orderTransactionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.orderTransaction.detail.title">OrderTransaction</Translate> [
            <b>{orderTransactionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="amount">
                <Translate contentKey="dropshippingApp.orderTransaction.amount">Amount</Translate>
              </span>
            </dt>
            <dd>{orderTransactionEntity.amount}</dd>
            <dt>
              <span id="note">
                <Translate contentKey="dropshippingApp.orderTransaction.note">Note</Translate>
              </span>
            </dt>
            <dd>{orderTransactionEntity.note}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="dropshippingApp.orderTransaction.status">Status</Translate>
              </span>
            </dt>
            <dd>{orderTransactionEntity.status}</dd>
            <dt>
              <span id="orderDate">
                <Translate contentKey="dropshippingApp.orderTransaction.orderDate">Order Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderTransactionEntity.orderDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.orderTransaction.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderTransactionEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.orderTransaction.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderTransactionEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderTransaction.orderCart">Order Cart</Translate>
            </dt>
            <dd>{orderTransactionEntity.orderCartId ? orderTransactionEntity.orderCartId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderTransaction.approver">Approver</Translate>
            </dt>
            <dd>{orderTransactionEntity.approverLogin ? orderTransactionEntity.approverLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderTransaction.createBy">Create By</Translate>
            </dt>
            <dd>{orderTransactionEntity.createByLogin ? orderTransactionEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderTransaction.updateBy">Update By</Translate>
            </dt>
            <dd>{orderTransactionEntity.updateByLogin ? orderTransactionEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/order-transaction" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/order-transaction/${orderTransactionEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orderTransaction }: IRootState) => ({
  orderTransactionEntity: orderTransaction.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderTransactionDetail);
