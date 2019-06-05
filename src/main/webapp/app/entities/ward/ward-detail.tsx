import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ward.reducer';
import { IWard } from 'app/shared/model/ward.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWardDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class WardDetail extends React.Component<IWardDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { wardEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.ward.detail.title">Ward</Translate> [<b>{wardEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="dropshippingApp.ward.name">Name</Translate>
              </span>
            </dt>
            <dd>{wardEntity.name}</dd>
            <dt>
              <span id="enabled">
                <Translate contentKey="dropshippingApp.ward.enabled">Enabled</Translate>
              </span>
            </dt>
            <dd>{wardEntity.enabled ? 'true' : 'false'}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.ward.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={wardEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.ward.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={wardEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.ward.district">District</Translate>
            </dt>
            <dd>{wardEntity.districtId ? wardEntity.districtId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/ward" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/ward/${wardEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ward }: IRootState) => ({
  wardEntity: ward.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(WardDetail);
