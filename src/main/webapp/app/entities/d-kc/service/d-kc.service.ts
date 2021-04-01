import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDKc, getDKcIdentifier } from '../d-kc.model';

export type EntityResponseType = HttpResponse<IDKc>;
export type EntityArrayResponseType = HttpResponse<IDKc[]>;

@Injectable({ providedIn: 'root' })
export class DKcService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-kcs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-kcs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dKc: IDKc): Observable<EntityResponseType> {
    return this.http.post<IDKc>(this.resourceUrl, dKc, { observe: 'response' });
  }

  update(dKc: IDKc): Observable<EntityResponseType> {
    return this.http.put<IDKc>(`${this.resourceUrl}/${getDKcIdentifier(dKc) as number}`, dKc, { observe: 'response' });
  }

  partialUpdate(dKc: IDKc): Observable<EntityResponseType> {
    return this.http.patch<IDKc>(`${this.resourceUrl}/${getDKcIdentifier(dKc) as number}`, dKc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDKc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDKc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDKc[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDKcToCollectionIfMissing(dKcCollection: IDKc[], ...dKcsToCheck: (IDKc | null | undefined)[]): IDKc[] {
    const dKcs: IDKc[] = dKcsToCheck.filter(isPresent);
    if (dKcs.length > 0) {
      const dKcCollectionIdentifiers = dKcCollection.map(dKcItem => getDKcIdentifier(dKcItem)!);
      const dKcsToAdd = dKcs.filter(dKcItem => {
        const dKcIdentifier = getDKcIdentifier(dKcItem);
        if (dKcIdentifier == null || dKcCollectionIdentifiers.includes(dKcIdentifier)) {
          return false;
        }
        dKcCollectionIdentifiers.push(dKcIdentifier);
        return true;
      });
      return [...dKcsToAdd, ...dKcCollection];
    }
    return dKcCollection;
  }
}
