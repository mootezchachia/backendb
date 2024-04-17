import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Items from './items';
import ItemsDetail from './items-detail';
import ItemsUpdate from './items-update';
import ItemsDeleteDialog from './items-delete-dialog';

const ItemsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Items />} />
    <Route path="new" element={<ItemsUpdate />} />
    <Route path=":id">
      <Route index element={<ItemsDetail />} />
      <Route path="edit" element={<ItemsUpdate />} />
      <Route path="delete" element={<ItemsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ItemsRoutes;
