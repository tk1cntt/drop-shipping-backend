import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './city.reducer';
import { ICity } from 'app/shared/model/city.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CityDetail extends React.Component<ICityDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { cityEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.city.detail.title">City</Translate> [<b>{cityEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="dropshippingApp.city.name">Name</Translate>
              </span>
            </dt>
            <dd>{cityEntity.name}</dd>
            <dt>
              <span id="index">
                <Translate contentKey="dropshippingApp.city.index">Index</Translate>
              </span>
            </dt>
            <dd>{cityEntity.index}</dd>
            <dt>
              <span id="enabled">
                <Translate contentKey="dropshippingApp.city.enabled">Enabled</Translate>
              </span>
            </dt>
            <dd>{cityEntity.enabled ? 'true' : 'false'}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.city.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={cityEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.city.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={cityEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.city.country">Country</Translate>
            </dt>
            <dd>{cityEntity.countryId ? cityEntity.countryId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/city" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/city/${cityEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ city }: IRootState) => ({
  cityEntity: city.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CityDetail);
