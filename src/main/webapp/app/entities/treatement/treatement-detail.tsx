import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './treatement.reducer';

export const TreatementDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const treatementEntity = useAppSelector(state => state.treatement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="treatementDetailsHeading">
          <Translate contentKey="backendApp.treatement.detail.title">Treatement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="backendApp.treatement.id">Id</Translate>
            </span>
          </dt>
          <dd>{treatementEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="backendApp.treatement.name">Name</Translate>
            </span>
          </dt>
          <dd>{treatementEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="backendApp.treatement.description">Description</Translate>
            </span>
          </dt>
          <dd>{treatementEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/treatement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/treatement/${treatementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TreatementDetail;
