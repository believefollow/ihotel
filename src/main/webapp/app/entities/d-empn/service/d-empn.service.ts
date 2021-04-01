import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDEmpn, getDEmpnIdentifier } from '../d-empn.model';

export type EntityResponseType = HttpResponse<IDEmpn>;
export type EntityArrayResponseType = HttpResponse<IDEmpn[]>;

@Injectable({ providedIn: 'root' })
export class DEmpnService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-empns');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-empns');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dEmpn: IDEmpn): Observable<EntityResponseType> {
    return this.http.post<IDEmpn>(this.resourceUrl, dEmpn, { observe: 'response' });
  }

  update(dEmpn: IDEmpn): Observable<EntityResponseType> {
    return this.http.put<IDEmpn>(`${this.resourceUrl}/${getDEmpnIdentifier(dEmpn) as number}`, dEmpn, { observe: 'response' });
  }

  partialUpdate(dEmpn: IDEmpn): Observable<EntityResponseType> {
    return this.http.patch<IDEmpn>(`${this.resourceUrl}/${getDEmpnIdentifier(dEmpn) as number}`, dEmpn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDEmpn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDEmpn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDEmpn[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDEmpnToCollectionIfMissing(dEmpnCollection: IDEmpn[], ...dEmpnsToCheck: (IDEmpn | null | undefined)[]): IDEmpn[] {
    const dEmpns: IDEmpn[] = dEmpnsToCheck.filter(isPresent);
    if (dEmpns.length > 0) {
      const dEmpnCollectionIdentifiers = dEmpnCollection.map(dEmpnItem => getDEmpnIdentifier(dEmpnItem)!);
      const dEmpnsToAdd = dEmpns.filter(dEmpnItem => {
        const dEmpnIdentifier = getDEmpnIdentifier(dEmpnItem);
        if (dEmpnIdentifier == null || dEmpnCollectionIdentifiers.includes(dEmpnIdentifier)) {
          return false;
        }
        dEmpnCollectionIdentifiers.push(dEmpnIdentifier);
        return true;
      });
      return [...dEmpnsToAdd, ...dEmpnCollection];
    }
    return dEmpnCollection;
  }
}
