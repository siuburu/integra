import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SistemaComponent } from './list/sistema.component';
import { SistemaDetailComponent } from './detail/sistema-detail.component';
import { SistemaUpdateComponent } from './update/sistema-update.component';
import SistemaResolve from './route/sistema-routing-resolve.service';

const sistemaRoute: Routes = [
  {
    path: '',
    component: SistemaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SistemaDetailComponent,
    resolve: {
      sistema: SistemaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SistemaUpdateComponent,
    resolve: {
      sistema: SistemaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SistemaUpdateComponent,
    resolve: {
      sistema: SistemaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sistemaRoute;
