import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICode1, getCode1Identifier } from '../code-1.model';

export type EntityResponseType = HttpResponse<ICode1>;
export type EntityArrayResponseType = HttpResponse<ICode1[]>;

@Injectable({ providedIn: 'root' })
export class Code1Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/code-1-s');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/code-1-s');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(code1: ICode1): Observable<EntityResponseType> {
    return this.http.post<ICode1>(this.resourceUrl, code1, { observe: 'response' });
  }

  update(code1: ICode1): Observable<EntityResponseType> {
    return this.http.put<ICode1>(`${this.resourceUrl}/${getCode1Identifier(code1) as number}`, code1, { observe: 'response' });
  }

  partialUpdate(code1: ICode1): Observable<EntityResponseType> {
    return this.http.patch<ICode1>(`${this.resourceUrl}/${getCode1Identifier(code1) as number}`, code1, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICode1>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICode1[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICode1[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addCode1ToCollectionIfMissing(code1Collection: ICode1[], ...code1sToCheck: (ICode1 | null | undefined)[]): ICode1[] {
    const code1s: ICode1[] = code1sToCheck.filter(isPresent);
    if (code1s.length > 0) {
      const code1CollectionIdentifiers = code1Collection.map(code1Item => getCode1Identifier(code1Item)!);
      const code1sToAdd = code1s.filter(code1Item => {
        const code1Identifier = getCode1Identifier(code1Item);
        if (code1Identifier == null || code1CollectionIdentifiers.includes(code1Identifier)) {
          return false;
        }
        code1CollectionIdentifiers.push(code1Identifier);
        return true;
      });
      return [...code1sToAdd, ...code1Collection];
    }
    return code1Collection;
  }
}
