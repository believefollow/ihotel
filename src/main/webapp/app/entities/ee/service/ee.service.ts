import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IEe, getEeIdentifier } from '../ee.model';

export type EntityResponseType = HttpResponse<IEe>;
export type EntityArrayResponseType = HttpResponse<IEe[]>;

@Injectable({ providedIn: 'root' })
export class EeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ees');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/ees');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ee: IEe): Observable<EntityResponseType> {
    return this.http.post<IEe>(this.resourceUrl, ee, { observe: 'response' });
  }

  update(ee: IEe): Observable<EntityResponseType> {
    return this.http.put<IEe>(`${this.resourceUrl}/${getEeIdentifier(ee) as number}`, ee, { observe: 'response' });
  }

  partialUpdate(ee: IEe): Observable<EntityResponseType> {
    return this.http.patch<IEe>(`${this.resourceUrl}/${getEeIdentifier(ee) as number}`, ee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEe[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addEeToCollectionIfMissing(eeCollection: IEe[], ...eesToCheck: (IEe | null | undefined)[]): IEe[] {
    const ees: IEe[] = eesToCheck.filter(isPresent);
    if (ees.length > 0) {
      const eeCollectionIdentifiers = eeCollection.map(eeItem => getEeIdentifier(eeItem)!);
      const eesToAdd = ees.filter(eeItem => {
        const eeIdentifier = getEeIdentifier(eeItem);
        if (eeIdentifier == null || eeCollectionIdentifiers.includes(eeIdentifier)) {
          return false;
        }
        eeCollectionIdentifiers.push(eeIdentifier);
        return true;
      });
      return [...eesToAdd, ...eeCollection];
    }
    return eeCollection;
  }
}
