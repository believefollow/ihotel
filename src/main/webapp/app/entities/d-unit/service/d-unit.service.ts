import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDUnit, getDUnitIdentifier } from '../d-unit.model';

export type EntityResponseType = HttpResponse<IDUnit>;
export type EntityArrayResponseType = HttpResponse<IDUnit[]>;

@Injectable({ providedIn: 'root' })
export class DUnitService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-units');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-units');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dUnit: IDUnit): Observable<EntityResponseType> {
    return this.http.post<IDUnit>(this.resourceUrl, dUnit, { observe: 'response' });
  }

  update(dUnit: IDUnit): Observable<EntityResponseType> {
    return this.http.put<IDUnit>(`${this.resourceUrl}/${getDUnitIdentifier(dUnit) as number}`, dUnit, { observe: 'response' });
  }

  partialUpdate(dUnit: IDUnit): Observable<EntityResponseType> {
    return this.http.patch<IDUnit>(`${this.resourceUrl}/${getDUnitIdentifier(dUnit) as number}`, dUnit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDUnit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDUnit[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDUnitToCollectionIfMissing(dUnitCollection: IDUnit[], ...dUnitsToCheck: (IDUnit | null | undefined)[]): IDUnit[] {
    const dUnits: IDUnit[] = dUnitsToCheck.filter(isPresent);
    if (dUnits.length > 0) {
      const dUnitCollectionIdentifiers = dUnitCollection.map(dUnitItem => getDUnitIdentifier(dUnitItem)!);
      const dUnitsToAdd = dUnits.filter(dUnitItem => {
        const dUnitIdentifier = getDUnitIdentifier(dUnitItem);
        if (dUnitIdentifier == null || dUnitCollectionIdentifiers.includes(dUnitIdentifier)) {
          return false;
        }
        dUnitCollectionIdentifiers.push(dUnitIdentifier);
        return true;
      });
      return [...dUnitsToAdd, ...dUnitCollection];
    }
    return dUnitCollection;
  }
}
