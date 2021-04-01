import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDDept, getDDeptIdentifier } from '../d-dept.model';

export type EntityResponseType = HttpResponse<IDDept>;
export type EntityArrayResponseType = HttpResponse<IDDept[]>;

@Injectable({ providedIn: 'root' })
export class DDeptService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-depts');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-depts');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dDept: IDDept): Observable<EntityResponseType> {
    return this.http.post<IDDept>(this.resourceUrl, dDept, { observe: 'response' });
  }

  update(dDept: IDDept): Observable<EntityResponseType> {
    return this.http.put<IDDept>(`${this.resourceUrl}/${getDDeptIdentifier(dDept) as number}`, dDept, { observe: 'response' });
  }

  partialUpdate(dDept: IDDept): Observable<EntityResponseType> {
    return this.http.patch<IDDept>(`${this.resourceUrl}/${getDDeptIdentifier(dDept) as number}`, dDept, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDDept>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDDept[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDDept[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDDeptToCollectionIfMissing(dDeptCollection: IDDept[], ...dDeptsToCheck: (IDDept | null | undefined)[]): IDDept[] {
    const dDepts: IDDept[] = dDeptsToCheck.filter(isPresent);
    if (dDepts.length > 0) {
      const dDeptCollectionIdentifiers = dDeptCollection.map(dDeptItem => getDDeptIdentifier(dDeptItem)!);
      const dDeptsToAdd = dDepts.filter(dDeptItem => {
        const dDeptIdentifier = getDDeptIdentifier(dDeptItem);
        if (dDeptIdentifier == null || dDeptCollectionIdentifiers.includes(dDeptIdentifier)) {
          return false;
        }
        dDeptCollectionIdentifiers.push(dDeptIdentifier);
        return true;
      });
      return [...dDeptsToAdd, ...dDeptCollection];
    }
    return dDeptCollection;
  }
}
