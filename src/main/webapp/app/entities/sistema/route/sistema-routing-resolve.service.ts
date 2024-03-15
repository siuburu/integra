import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISistema } from '../sistema.model';
import { SistemaService } from '../service/sistema.service';

export const sistemaResolve = (route: ActivatedRouteSnapshot): Observable<null | ISistema> => {
  const id = route.params['id'];
  if (id) {
    return inject(SistemaService)
      .find(id)
      .pipe(
        mergeMap((sistema: HttpResponse<ISistema>) => {
          if (sistema.body) {
            return of(sistema.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sistemaResolve;
