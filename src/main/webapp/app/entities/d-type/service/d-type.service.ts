import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDType, getDTypeIdentifier } from '../d-type.model';

export type EntityResponseType = HttpResponse<IDType>;
export type EntityArrayResponseType = HttpResponse<IDType[]>;

@Injectable({ providedIn: 'root' })
export class DTypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-types');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-types');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dType: IDType): Observable<EntityResponseType> {
    return this.http.post<IDType>(this.resourceUrl, dType, { observe: 'response' });
  }

  update(dType: IDType): Observable<EntityResponseType> {
    return this.http.put<IDType>(`${this.resourceUrl}/${getDTypeIdentifier(dType) as number}`, dType, { observe: 'response' });
  }

  partialUpdate(dType: IDType): Observable<EntityResponseType> {
    return this.http.patch<IDType>(`${this.resourceUrl}/${getDTypeIdentifier(dType) as number}`, dType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDTypeToCollectionIfMissing(dTypeCollection: IDType[], ...dTypesToCheck: (IDType | null | undefined)[]): IDType[] {
    const dTypes: IDType[] = dTypesToCheck.filter(isPresent);
    if (dTypes.length > 0) {
      const dTypeCollectionIdentifiers = dTypeCollection.map(dTypeItem => getDTypeIdentifier(dTypeItem)!);
      const dTypesToAdd = dTypes.filter(dTypeItem => {
        const dTypeIdentifier = getDTypeIdentifier(dTypeItem);
        if (dTypeIdentifier == null || dTypeCollectionIdentifiers.includes(dTypeIdentifier)) {
          return false;
        }
        dTypeCollectionIdentifiers.push(dTypeIdentifier);
        return true;
      });
      return [...dTypesToAdd, ...dTypeCollection];
    }
    return dTypeCollection;
  }
}
