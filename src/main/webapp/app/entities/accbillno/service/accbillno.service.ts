import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAccbillno, getAccbillnoIdentifier } from '../accbillno.model';

export type EntityResponseType = HttpResponse<IAccbillno>;
export type EntityArrayResponseType = HttpResponse<IAccbillno[]>;

@Injectable({ providedIn: 'root' })
export class AccbillnoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/accbillnos');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/accbillnos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(accbillno: IAccbillno): Observable<EntityResponseType> {
    return this.http.post<IAccbillno>(this.resourceUrl, accbillno, { observe: 'response' });
  }

  update(accbillno: IAccbillno): Observable<EntityResponseType> {
    return this.http.put<IAccbillno>(`${this.resourceUrl}/${getAccbillnoIdentifier(accbillno) as number}`, accbillno, {
      observe: 'response',
    });
  }

  partialUpdate(accbillno: IAccbillno): Observable<EntityResponseType> {
    return this.http.patch<IAccbillno>(`${this.resourceUrl}/${getAccbillnoIdentifier(accbillno) as number}`, accbillno, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccbillno>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccbillno[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccbillno[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addAccbillnoToCollectionIfMissing(
    accbillnoCollection: IAccbillno[],
    ...accbillnosToCheck: (IAccbillno | null | undefined)[]
  ): IAccbillno[] {
    const accbillnos: IAccbillno[] = accbillnosToCheck.filter(isPresent);
    if (accbillnos.length > 0) {
      const accbillnoCollectionIdentifiers = accbillnoCollection.map(accbillnoItem => getAccbillnoIdentifier(accbillnoItem)!);
      const accbillnosToAdd = accbillnos.filter(accbillnoItem => {
        const accbillnoIdentifier = getAccbillnoIdentifier(accbillnoItem);
        if (accbillnoIdentifier == null || accbillnoCollectionIdentifiers.includes(accbillnoIdentifier)) {
          return false;
        }
        accbillnoCollectionIdentifiers.push(accbillnoIdentifier);
        return true;
      });
      return [...accbillnosToAdd, ...accbillnoCollection];
    }
    return accbillnoCollection;
  }
}
