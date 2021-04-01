import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDxSedinfo, getDxSedinfoIdentifier } from '../dx-sedinfo.model';

export type EntityResponseType = HttpResponse<IDxSedinfo>;
export type EntityArrayResponseType = HttpResponse<IDxSedinfo[]>;

@Injectable({ providedIn: 'root' })
export class DxSedinfoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/dx-sedinfos');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/dx-sedinfos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dxSedinfo: IDxSedinfo): Observable<EntityResponseType> {
    return this.http.post<IDxSedinfo>(this.resourceUrl, dxSedinfo, { observe: 'response' });
  }

  update(dxSedinfo: IDxSedinfo): Observable<EntityResponseType> {
    return this.http.put<IDxSedinfo>(`${this.resourceUrl}/${getDxSedinfoIdentifier(dxSedinfo) as number}`, dxSedinfo, {
      observe: 'response',
    });
  }

  partialUpdate(dxSedinfo: IDxSedinfo): Observable<EntityResponseType> {
    return this.http.patch<IDxSedinfo>(`${this.resourceUrl}/${getDxSedinfoIdentifier(dxSedinfo) as number}`, dxSedinfo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDxSedinfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDxSedinfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDxSedinfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDxSedinfoToCollectionIfMissing(
    dxSedinfoCollection: IDxSedinfo[],
    ...dxSedinfosToCheck: (IDxSedinfo | null | undefined)[]
  ): IDxSedinfo[] {
    const dxSedinfos: IDxSedinfo[] = dxSedinfosToCheck.filter(isPresent);
    if (dxSedinfos.length > 0) {
      const dxSedinfoCollectionIdentifiers = dxSedinfoCollection.map(dxSedinfoItem => getDxSedinfoIdentifier(dxSedinfoItem)!);
      const dxSedinfosToAdd = dxSedinfos.filter(dxSedinfoItem => {
        const dxSedinfoIdentifier = getDxSedinfoIdentifier(dxSedinfoItem);
        if (dxSedinfoIdentifier == null || dxSedinfoCollectionIdentifiers.includes(dxSedinfoIdentifier)) {
          return false;
        }
        dxSedinfoCollectionIdentifiers.push(dxSedinfoIdentifier);
        return true;
      });
      return [...dxSedinfosToAdd, ...dxSedinfoCollection];
    }
    return dxSedinfoCollection;
  }
}
