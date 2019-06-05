import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './district.reducer';
import { IDistrict } from 'app/shared/model/district.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDistrictDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DistrictDetail extends React.Component<IDistrictDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { districtEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.district.detail.title">District</Translate> [<b>{districtEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="dropshippingApp.district.name">Name</Translate>
              </span>
            </dt>
            <dd>{districtEntity.name}</dd>
            <dt>
              <span id="enabled">
                <Translate contentKey="dropshippingApp.district.enabled">Enabled</Translate>
              </span>
            </dt>
            <dd>{districtEntity.enabled ? 'true' : 'false'}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.district.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={districtEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.district.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={districtEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.district.city">City</Translate>
            </dt>
            <dd>{districtEntity.cityId ? districtEntity.cityId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/district" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/district/${districtEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ district }: IRootState) => ({
  districtEntity: district.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DistrictDetail);
