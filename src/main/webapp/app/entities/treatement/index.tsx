import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Treatement from './treatement';
import TreatementDetail from './treatement-detail';
import TreatementUpdate from './treatement-update';
import TreatementDeleteDialog from './treatement-delete-dialog';

const TreatementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Treatement />} />
    <Route path="new" element={<TreatementUpdate />} />
    <Route path=":id">
      <Route index element={<TreatementDetail />} />
      <Route path="edit" element={<TreatementUpdate />} />
      <Route path="delete" element={<TreatementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TreatementRoutes;
