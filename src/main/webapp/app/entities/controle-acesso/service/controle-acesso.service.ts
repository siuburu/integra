import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IControleAcesso, NewControleAcesso } from '../controle-acesso.model';

export type PartialUpdateControleAcesso = Partial<IControleAcesso> & Pick<IControleAcesso, 'id'>;

type RestOf<T extends IControleAcesso | NewControleAcesso> = Omit<T, 'dataAcesso'> & {
  dataAcesso?: string | null;
};

export type RestControleAcesso = RestOf<IControleAcesso>;

export type NewRestControleAcesso = RestOf<NewControleAcesso>;

export type PartialUpdateRestControleAcesso = RestOf<PartialUpdateControleAcesso>;

export type EntityResponseType = HttpResponse<IControleAcesso>;
export type EntityArrayResponseType = HttpResponse<IControleAcesso[]>;

@Injectable({ providedIn: 'root' })
export class ControleAcessoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/controle-acessos');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(controleAcesso: NewControleAcesso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleAcesso);
    return this.http
      .post<RestControleAcesso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(controleAcesso: IControleAcesso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleAcesso);
    return this.http
      .put<RestControleAcesso>(`${this.resourceUrl}/${this.getControleAcessoIdentifier(controleAcesso)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(controleAcesso: PartialUpdateControleAcesso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controleAcesso);
    return this.http
      .patch<RestControleAcesso>(`${this.resourceUrl}/${this.getControleAcessoIdentifier(controleAcesso)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestControleAcesso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestControleAcesso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getControleAcessoIdentifier(controleAcesso: Pick<IControleAcesso, 'id'>): number {
    return controleAcesso.id;
  }

  compareControleAcesso(o1: Pick<IControleAcesso, 'id'> | null, o2: Pick<IControleAcesso, 'id'> | null): boolean {
    return o1 && o2 ? this.getControleAcessoIdentifier(o1) === this.getControleAcessoIdentifier(o2) : o1 === o2;
  }

  addControleAcessoToCollectionIfMissing<Type extends Pick<IControleAcesso, 'id'>>(
    controleAcessoCollection: Type[],
    ...controleAcessosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const controleAcessos: Type[] = controleAcessosToCheck.filter(isPresent);
    if (controleAcessos.length > 0) {
      const controleAcessoCollectionIdentifiers = controleAcessoCollection.map(
        controleAcessoItem => this.getControleAcessoIdentifier(controleAcessoItem)!,
      );
      const controleAcessosToAdd = controleAcessos.filter(controleAcessoItem => {
        const controleAcessoIdentifier = this.getControleAcessoIdentifier(controleAcessoItem);
        if (controleAcessoCollectionIdentifiers.includes(controleAcessoIdentifier)) {
          return false;
        }
        controleAcessoCollectionIdentifiers.push(controleAcessoIdentifier);
        return true;
      });
      return [...controleAcessosToAdd, ...controleAcessoCollection];
    }
    return controleAcessoCollection;
  }

  protected convertDateFromClient<T extends IControleAcesso | NewControleAcesso | PartialUpdateControleAcesso>(
    controleAcesso: T,
  ): RestOf<T> {
    return {
      ...controleAcesso,
      dataAcesso: controleAcesso.dataAcesso?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restControleAcesso: RestControleAcesso): IControleAcesso {
    return {
      ...restControleAcesso,
      dataAcesso: restControleAcesso.dataAcesso ? dayjs(restControleAcesso.dataAcesso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestControleAcesso>): HttpResponse<IControleAcesso> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestControleAcesso[]>): HttpResponse<IControleAcesso[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
