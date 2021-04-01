import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFeetype, getFeetypeIdentifier } from '../feetype.model';

export type EntityResponseType = HttpResponse<IFeetype>;
export type EntityArrayResponseType = HttpResponse<IFeetype[]>;

@Injectable({ providedIn: 'root' })
export class FeetypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/feetypes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/feetypes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(feetype: IFeetype): Observable<EntityResponseType> {
    return this.http.post<IFeetype>(this.resourceUrl, feetype, { observe: 'response' });
  }

  update(feetype: IFeetype): Observable<EntityResponseType> {
    return this.http.put<IFeetype>(`${this.resourceUrl}/${getFeetypeIdentifier(feetype) as number}`, feetype, { observe: 'response' });
  }

  partialUpdate(feetype: IFeetype): Observable<EntityResponseType> {
    return this.http.patch<IFeetype>(`${this.resourceUrl}/${getFeetypeIdentifier(feetype) as number}`, feetype, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeetype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeetype[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeetype[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addFeetypeToCollectionIfMissing(feetypeCollection: IFeetype[], ...feetypesToCheck: (IFeetype | null | undefined)[]): IFeetype[] {
    const feetypes: IFeetype[] = feetypesToCheck.filter(isPresent);
    if (feetypes.length > 0) {
      const feetypeCollectionIdentifiers = feetypeCollection.map(feetypeItem => getFeetypeIdentifier(feetypeItem)!);
      const feetypesToAdd = feetypes.filter(feetypeItem => {
        const feetypeIdentifier = getFeetypeIdentifier(feetypeItem);
        if (feetypeIdentifier == null || feetypeCollectionIdentifiers.includes(feetypeIdentifier)) {
          return false;
        }
        feetypeCollectionIdentifiers.push(feetypeIdentifier);
        return true;
      });
      return [...feetypesToAdd, ...feetypeCollection];
    }
    return feetypeCollection;
  }
}
