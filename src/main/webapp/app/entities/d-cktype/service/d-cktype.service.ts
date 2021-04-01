import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDCktype, getDCktypeIdentifier } from '../d-cktype.model';

export type EntityResponseType = HttpResponse<IDCktype>;
export type EntityArrayResponseType = HttpResponse<IDCktype[]>;

@Injectable({ providedIn: 'root' })
export class DCktypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-cktypes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-cktypes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dCktype: IDCktype): Observable<EntityResponseType> {
    return this.http.post<IDCktype>(this.resourceUrl, dCktype, { observe: 'response' });
  }

  update(dCktype: IDCktype): Observable<EntityResponseType> {
    return this.http.put<IDCktype>(`${this.resourceUrl}/${getDCktypeIdentifier(dCktype) as number}`, dCktype, { observe: 'response' });
  }

  partialUpdate(dCktype: IDCktype): Observable<EntityResponseType> {
    return this.http.patch<IDCktype>(`${this.resourceUrl}/${getDCktypeIdentifier(dCktype) as number}`, dCktype, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDCktype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDCktype[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDCktype[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDCktypeToCollectionIfMissing(dCktypeCollection: IDCktype[], ...dCktypesToCheck: (IDCktype | null | undefined)[]): IDCktype[] {
    const dCktypes: IDCktype[] = dCktypesToCheck.filter(isPresent);
    if (dCktypes.length > 0) {
      const dCktypeCollectionIdentifiers = dCktypeCollection.map(dCktypeItem => getDCktypeIdentifier(dCktypeItem)!);
      const dCktypesToAdd = dCktypes.filter(dCktypeItem => {
        const dCktypeIdentifier = getDCktypeIdentifier(dCktypeItem);
        if (dCktypeIdentifier == null || dCktypeCollectionIdentifiers.includes(dCktypeIdentifier)) {
          return false;
        }
        dCktypeCollectionIdentifiers.push(dCktypeIdentifier);
        return true;
      });
      return [...dCktypesToAdd, ...dCktypeCollection];
    }
    return dCktypeCollection;
  }
}
