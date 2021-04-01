import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAccPp, getAccPpIdentifier } from '../acc-pp.model';

export type EntityResponseType = HttpResponse<IAccPp>;
export type EntityArrayResponseType = HttpResponse<IAccPp[]>;

@Injectable({ providedIn: 'root' })
export class AccPpService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/acc-pps');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/acc-pps');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(accPp: IAccPp): Observable<EntityResponseType> {
    return this.http.post<IAccPp>(this.resourceUrl, accPp, { observe: 'response' });
  }

  update(accPp: IAccPp): Observable<EntityResponseType> {
    return this.http.put<IAccPp>(`${this.resourceUrl}/${getAccPpIdentifier(accPp) as number}`, accPp, { observe: 'response' });
  }

  partialUpdate(accPp: IAccPp): Observable<EntityResponseType> {
    return this.http.patch<IAccPp>(`${this.resourceUrl}/${getAccPpIdentifier(accPp) as number}`, accPp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccPp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccPp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccPp[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addAccPpToCollectionIfMissing(accPpCollection: IAccPp[], ...accPpsToCheck: (IAccPp | null | undefined)[]): IAccPp[] {
    const accPps: IAccPp[] = accPpsToCheck.filter(isPresent);
    if (accPps.length > 0) {
      const accPpCollectionIdentifiers = accPpCollection.map(accPpItem => getAccPpIdentifier(accPpItem)!);
      const accPpsToAdd = accPps.filter(accPpItem => {
        const accPpIdentifier = getAccPpIdentifier(accPpItem);
        if (accPpIdentifier == null || accPpCollectionIdentifiers.includes(accPpIdentifier)) {
          return false;
        }
        accPpCollectionIdentifiers.push(accPpIdentifier);
        return true;
      });
      return [...accPpsToAdd, ...accPpCollection];
    }
    return accPpCollection;
  }
}
