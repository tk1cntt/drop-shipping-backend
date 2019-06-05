import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './delivery-package.reducer';
import { IDeliveryPackage } from 'app/shared/model/delivery-package.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeliveryPackageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DeliveryPackageDetail extends React.Component<IDeliveryPackageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { deliveryPackageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.deliveryPackage.detail.title">DeliveryPackage</Translate> [
            <b>{deliveryPackageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.deliveryPackage.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={deliveryPackageEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.deliveryPackage.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={deliveryPackageEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.deliveryPackage.delivery">Delivery</Translate>
            </dt>
            <dd>{deliveryPackageEntity.deliveryId ? deliveryPackageEntity.deliveryId : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.deliveryPackage.createBy">Create By</Translate>
            </dt>
            <dd>{deliveryPackageEntity.createByLogin ? deliveryPackageEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.deliveryPackage.updateBy">Update By</Translate>
            </dt>
            <dd>{deliveryPackageEntity.updateByLogin ? deliveryPackageEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/delivery-package" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/delivery-package/${deliveryPackageEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ deliveryPackage }: IRootState) => ({
  deliveryPackageEntity: deliveryPackage.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DeliveryPackageDetail);
