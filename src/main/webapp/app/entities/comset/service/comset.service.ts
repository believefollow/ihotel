import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IComset, getComsetIdentifier } from '../comset.model';

export type EntityResponseType = HttpResponse<IComset>;
export type EntityArrayResponseType = HttpResponse<IComset[]>;

@Injectable({ providedIn: 'root' })
export class ComsetService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/comsets');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/comsets');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(comset: IComset): Observable<EntityResponseType> {
    return this.http.post<IComset>(this.resourceUrl, comset, { observe: 'response' });
  }

  update(comset: IComset): Observable<EntityResponseType> {
    return this.http.put<IComset>(`${this.resourceUrl}/${getComsetIdentifier(comset) as number}`, comset, { observe: 'response' });
  }

  partialUpdate(comset: IComset): Observable<EntityResponseType> {
    return this.http.patch<IComset>(`${this.resourceUrl}/${getComsetIdentifier(comset) as number}`, comset, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComset>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComset[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComset[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addComsetToCollectionIfMissing(comsetCollection: IComset[], ...comsetsToCheck: (IComset | null | undefined)[]): IComset[] {
    const comsets: IComset[] = comsetsToCheck.filter(isPresent);
    if (comsets.length > 0) {
      const comsetCollectionIdentifiers = comsetCollection.map(comsetItem => getComsetIdentifier(comsetItem)!);
      const comsetsToAdd = comsets.filter(comsetItem => {
        const comsetIdentifier = getComsetIdentifier(comsetItem);
        if (comsetIdentifier == null || comsetCollectionIdentifiers.includes(comsetIdentifier)) {
          return false;
        }
        comsetCollectionIdentifiers.push(comsetIdentifier);
        return true;
      });
      return [...comsetsToAdd, ...comsetCollection];
    }
    return comsetCollection;
  }
}
