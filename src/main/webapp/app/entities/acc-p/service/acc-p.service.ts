import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAccP, getAccPIdentifier } from '../acc-p.model';

export type EntityResponseType = HttpResponse<IAccP>;
export type EntityArrayResponseType = HttpResponse<IAccP[]>;

@Injectable({ providedIn: 'root' })
export class AccPService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/acc-ps');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/acc-ps');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(accP: IAccP): Observable<EntityResponseType> {
    return this.http.post<IAccP>(this.resourceUrl, accP, { observe: 'response' });
  }

  update(accP: IAccP): Observable<EntityResponseType> {
    return this.http.put<IAccP>(`${this.resourceUrl}/${getAccPIdentifier(accP) as number}`, accP, { observe: 'response' });
  }

  partialUpdate(accP: IAccP): Observable<EntityResponseType> {
    return this.http.patch<IAccP>(`${this.resourceUrl}/${getAccPIdentifier(accP) as number}`, accP, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccP>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccP[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccP[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addAccPToCollectionIfMissing(accPCollection: IAccP[], ...accPSToCheck: (IAccP | null | undefined)[]): IAccP[] {
    const accPS: IAccP[] = accPSToCheck.filter(isPresent);
    if (accPS.length > 0) {
      const accPCollectionIdentifiers = accPCollection.map(accPItem => getAccPIdentifier(accPItem)!);
      const accPSToAdd = accPS.filter(accPItem => {
        const accPIdentifier = getAccPIdentifier(accPItem);
        if (accPIdentifier == null || accPCollectionIdentifiers.includes(accPIdentifier)) {
          return false;
        }
        accPCollectionIdentifiers.push(accPIdentifier);
        return true;
      });
      return [...accPSToAdd, ...accPCollection];
    }
    return accPCollection;
  }
}
