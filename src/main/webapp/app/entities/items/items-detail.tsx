import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './items.reducer';

export const ItemsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemsEntity = useAppSelector(state => state.items.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemsDetailsHeading">
          <Translate contentKey="backendApp.items.detail.title">Items</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="backendApp.items.id">Id</Translate>
            </span>
          </dt>
          <dd>{itemsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="backendApp.items.name">Name</Translate>
            </span>
          </dt>
          <dd>{itemsEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="backendApp.items.description">Description</Translate>
            </span>
          </dt>
          <dd>{itemsEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/items" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/items/${itemsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemsDetail;
