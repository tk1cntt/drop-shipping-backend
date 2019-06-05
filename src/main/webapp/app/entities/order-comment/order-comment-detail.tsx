import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-comment.reducer';
import { IOrderComment } from 'app/shared/model/order-comment.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderCommentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrderCommentDetail extends React.Component<IOrderCommentDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orderCommentEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.orderComment.detail.title">OrderComment</Translate> [<b>{orderCommentEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="message">
                <Translate contentKey="dropshippingApp.orderComment.message">Message</Translate>
              </span>
            </dt>
            <dd>{orderCommentEntity.message}</dd>
            <dt>
              <span id="sender">
                <Translate contentKey="dropshippingApp.orderComment.sender">Sender</Translate>
              </span>
            </dt>
            <dd>{orderCommentEntity.sender}</dd>
            <dt>
              <span id="type">
                <Translate contentKey="dropshippingApp.orderComment.type">Type</Translate>
              </span>
            </dt>
            <dd>{orderCommentEntity.type}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.orderComment.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderCommentEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderComment.orderCart">Order Cart</Translate>
            </dt>
            <dd>{orderCommentEntity.orderCartId ? orderCommentEntity.orderCartId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/order-comment" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/order-comment/${orderCommentEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ orderComment }: IRootState) => ({
  orderCommentEntity: orderComment.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderCommentDetail);
