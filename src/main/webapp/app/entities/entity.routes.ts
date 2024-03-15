import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'sistema',
    data: { pageTitle: 'integraApp.sistema.home.title' },
    loadChildren: () => import('./sistema/sistema.routes'),
  },
  {
    path: 'controle-acesso',
    data: { pageTitle: 'integraApp.controleAcesso.home.title' },
    loadChildren: () => import('./controle-acesso/controle-acesso.routes'),
  },
  {
    path: 'area',
    data: { pageTitle: 'integraApp.area.home.title' },
    loadChildren: () => import('./area/area.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
