import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAcc, getAccIdentifier } from '../acc.model';

export type EntityResponseType = HttpResponse<IAcc>;
export type EntityArrayResponseType = HttpResponse<IAcc[]>;

@Injectable({ providedIn: 'root' })
export class AccService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/accs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/accs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(acc: IAcc): Observable<EntityResponseType> {
    return this.http.post<IAcc>(this.resourceUrl, acc, { observe: 'response' });
  }

  update(acc: IAcc): Observable<EntityResponseType> {
    return this.http.put<IAcc>(`${this.resourceUrl}/${getAccIdentifier(acc) as number}`, acc, { observe: 'response' });
  }

  partialUpdate(acc: IAcc): Observable<EntityResponseType> {
    return this.http.patch<IAcc>(`${this.resourceUrl}/${getAccIdentifier(acc) as number}`, acc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAcc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAcc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAcc[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addAccToCollectionIfMissing(accCollection: IAcc[], ...accsToCheck: (IAcc | null | undefined)[]): IAcc[] {
    const accs: IAcc[] = accsToCheck.filter(isPresent);
    if (accs.length > 0) {
      const accCollectionIdentifiers = accCollection.map(accItem => getAccIdentifier(accItem)!);
      const accsToAdd = accs.filter(accItem => {
        const accIdentifier = getAccIdentifier(accItem);
        if (accIdentifier == null || accCollectionIdentifiers.includes(accIdentifier)) {
          return false;
        }
        accCollectionIdentifiers.push(accIdentifier);
        return true;
      });
      return [...accsToAdd, ...accCollection];
    }
    return accCollection;
  }
}
