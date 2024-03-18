import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISistema, NewSistema } from '../sistema.model';

export type PartialUpdateSistema = Partial<ISistema> & Pick<ISistema, 'id'>;

export type EntityResponseType = HttpResponse<ISistema>;
export type EntityArrayResponseType = HttpResponse<ISistema[]>;

@Injectable({ providedIn: 'root' })
export class SistemaService {
  //protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sistemas/all');
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sistemas');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(sistema: NewSistema): Observable<EntityResponseType> {
    return this.http.post<ISistema>(this.resourceUrl, sistema, { observe: 'response' });
  }

  update(sistema: ISistema): Observable<EntityResponseType> {
    return this.http.put<ISistema>(`${this.resourceUrl}/${this.getSistemaIdentifier(sistema)}`, sistema, { observe: 'response' });
  }

  partialUpdate(sistema: PartialUpdateSistema): Observable<EntityResponseType> {
    return this.http.patch<ISistema>(`${this.resourceUrl}/${this.getSistemaIdentifier(sistema)}`, sistema, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISistema>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISistema[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSistemaIdentifier(sistema: Pick<ISistema, 'id'>): number {
    return sistema.id;
  }

  compareSistema(o1: Pick<ISistema, 'id'> | null, o2: Pick<ISistema, 'id'> | null): boolean {
    return o1 && o2 ? this.getSistemaIdentifier(o1) === this.getSistemaIdentifier(o2) : o1 === o2;
  }

  addSistemaToCollectionIfMissing<Type extends Pick<ISistema, 'id'>>(
    sistemaCollection: Type[],
    ...sistemasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sistemas: Type[] = sistemasToCheck.filter(isPresent);
    if (sistemas.length > 0) {
      const sistemaCollectionIdentifiers = sistemaCollection.map(sistemaItem => this.getSistemaIdentifier(sistemaItem)!);
      const sistemasToAdd = sistemas.filter(sistemaItem => {
        const sistemaIdentifier = this.getSistemaIdentifier(sistemaItem);
        if (sistemaCollectionIdentifiers.includes(sistemaIdentifier)) {
          return false;
        }
        sistemaCollectionIdentifiers.push(sistemaIdentifier);
        return true;
      });
      return [...sistemasToAdd, ...sistemaCollection];
    }
    return sistemaCollection;
  }
}
