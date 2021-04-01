import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFwEmpn, getFwEmpnIdentifier } from '../fw-empn.model';

export type EntityResponseType = HttpResponse<IFwEmpn>;
export type EntityArrayResponseType = HttpResponse<IFwEmpn[]>;

@Injectable({ providedIn: 'root' })
export class FwEmpnService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/fw-empns');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/fw-empns');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fwEmpn: IFwEmpn): Observable<EntityResponseType> {
    return this.http.post<IFwEmpn>(this.resourceUrl, fwEmpn, { observe: 'response' });
  }

  update(fwEmpn: IFwEmpn): Observable<EntityResponseType> {
    return this.http.put<IFwEmpn>(`${this.resourceUrl}/${getFwEmpnIdentifier(fwEmpn) as number}`, fwEmpn, { observe: 'response' });
  }

  partialUpdate(fwEmpn: IFwEmpn): Observable<EntityResponseType> {
    return this.http.patch<IFwEmpn>(`${this.resourceUrl}/${getFwEmpnIdentifier(fwEmpn) as number}`, fwEmpn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFwEmpn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFwEmpn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFwEmpn[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addFwEmpnToCollectionIfMissing(fwEmpnCollection: IFwEmpn[], ...fwEmpnsToCheck: (IFwEmpn | null | undefined)[]): IFwEmpn[] {
    const fwEmpns: IFwEmpn[] = fwEmpnsToCheck.filter(isPresent);
    if (fwEmpns.length > 0) {
      const fwEmpnCollectionIdentifiers = fwEmpnCollection.map(fwEmpnItem => getFwEmpnIdentifier(fwEmpnItem)!);
      const fwEmpnsToAdd = fwEmpns.filter(fwEmpnItem => {
        const fwEmpnIdentifier = getFwEmpnIdentifier(fwEmpnItem);
        if (fwEmpnIdentifier == null || fwEmpnCollectionIdentifiers.includes(fwEmpnIdentifier)) {
          return false;
        }
        fwEmpnCollectionIdentifiers.push(fwEmpnIdentifier);
        return true;
      });
      return [...fwEmpnsToAdd, ...fwEmpnCollection];
    }
    return fwEmpnCollection;
  }
}
