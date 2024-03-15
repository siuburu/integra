import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IControleAcesso } from '../controle-acesso.model';
import { ControleAcessoService } from '../service/controle-acesso.service';

export const controleAcessoResolve = (route: ActivatedRouteSnapshot): Observable<null | IControleAcesso> => {
  const id = route.params['id'];
  if (id) {
    return inject(ControleAcessoService)
      .find(id)
      .pipe(
        mergeMap((controleAcesso: HttpResponse<IControleAcesso>) => {
          if (controleAcesso.body) {
            return of(controleAcesso.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default controleAcessoResolve;
