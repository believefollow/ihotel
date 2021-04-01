import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICzCzl3, getCzCzl3Identifier } from '../cz-czl-3.model';

export type EntityResponseType = HttpResponse<ICzCzl3>;
export type EntityArrayResponseType = HttpResponse<ICzCzl3[]>;

@Injectable({ providedIn: 'root' })
export class CzCzl3Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cz-czl-3-s');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/cz-czl-3-s');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(czCzl3: ICzCzl3): Observable<EntityResponseType> {
    return this.http.post<ICzCzl3>(this.resourceUrl, czCzl3, { observe: 'response' });
  }

  update(czCzl3: ICzCzl3): Observable<EntityResponseType> {
    return this.http.put<ICzCzl3>(`${this.resourceUrl}/${getCzCzl3Identifier(czCzl3) as number}`, czCzl3, { observe: 'response' });
  }

  partialUpdate(czCzl3: ICzCzl3): Observable<EntityResponseType> {
    return this.http.patch<ICzCzl3>(`${this.resourceUrl}/${getCzCzl3Identifier(czCzl3) as number}`, czCzl3, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICzCzl3>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICzCzl3[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICzCzl3[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addCzCzl3ToCollectionIfMissing(czCzl3Collection: ICzCzl3[], ...czCzl3sToCheck: (ICzCzl3 | null | undefined)[]): ICzCzl3[] {
    const czCzl3s: ICzCzl3[] = czCzl3sToCheck.filter(isPresent);
    if (czCzl3s.length > 0) {
      const czCzl3CollectionIdentifiers = czCzl3Collection.map(czCzl3Item => getCzCzl3Identifier(czCzl3Item)!);
      const czCzl3sToAdd = czCzl3s.filter(czCzl3Item => {
        const czCzl3Identifier = getCzCzl3Identifier(czCzl3Item);
        if (czCzl3Identifier == null || czCzl3CollectionIdentifiers.includes(czCzl3Identifier)) {
          return false;
        }
        czCzl3CollectionIdentifiers.push(czCzl3Identifier);
        return true;
      });
      return [...czCzl3sToAdd, ...czCzl3Collection];
    }
    return czCzl3Collection;
  }
}
