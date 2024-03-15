import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ControleAcessoComponent } from './list/controle-acesso.component';
import { ControleAcessoDetailComponent } from './detail/controle-acesso-detail.component';
import { ControleAcessoUpdateComponent } from './update/controle-acesso-update.component';
import ControleAcessoResolve from './route/controle-acesso-routing-resolve.service';

const controleAcessoRoute: Routes = [
  {
    path: '',
    component: ControleAcessoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControleAcessoDetailComponent,
    resolve: {
      controleAcesso: ControleAcessoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControleAcessoUpdateComponent,
    resolve: {
      controleAcesso: ControleAcessoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControleAcessoUpdateComponent,
    resolve: {
      controleAcesso: ControleAcessoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default controleAcessoRoute;
