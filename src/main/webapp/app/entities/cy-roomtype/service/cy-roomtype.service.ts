import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICyRoomtype, getCyRoomtypeIdentifier } from '../cy-roomtype.model';

export type EntityResponseType = HttpResponse<ICyRoomtype>;
export type EntityArrayResponseType = HttpResponse<ICyRoomtype[]>;

@Injectable({ providedIn: 'root' })
export class CyRoomtypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cy-roomtypes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/cy-roomtypes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(cyRoomtype: ICyRoomtype): Observable<EntityResponseType> {
    return this.http.post<ICyRoomtype>(this.resourceUrl, cyRoomtype, { observe: 'response' });
  }

  update(cyRoomtype: ICyRoomtype): Observable<EntityResponseType> {
    return this.http.put<ICyRoomtype>(`${this.resourceUrl}/${getCyRoomtypeIdentifier(cyRoomtype) as number}`, cyRoomtype, {
      observe: 'response',
    });
  }

  partialUpdate(cyRoomtype: ICyRoomtype): Observable<EntityResponseType> {
    return this.http.patch<ICyRoomtype>(`${this.resourceUrl}/${getCyRoomtypeIdentifier(cyRoomtype) as number}`, cyRoomtype, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICyRoomtype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICyRoomtype[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICyRoomtype[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addCyRoomtypeToCollectionIfMissing(
    cyRoomtypeCollection: ICyRoomtype[],
    ...cyRoomtypesToCheck: (ICyRoomtype | null | undefined)[]
  ): ICyRoomtype[] {
    const cyRoomtypes: ICyRoomtype[] = cyRoomtypesToCheck.filter(isPresent);
    if (cyRoomtypes.length > 0) {
      const cyRoomtypeCollectionIdentifiers = cyRoomtypeCollection.map(cyRoomtypeItem => getCyRoomtypeIdentifier(cyRoomtypeItem)!);
      const cyRoomtypesToAdd = cyRoomtypes.filter(cyRoomtypeItem => {
        const cyRoomtypeIdentifier = getCyRoomtypeIdentifier(cyRoomtypeItem);
        if (cyRoomtypeIdentifier == null || cyRoomtypeCollectionIdentifiers.includes(cyRoomtypeIdentifier)) {
          return false;
        }
        cyRoomtypeCollectionIdentifiers.push(cyRoomtypeIdentifier);
        return true;
      });
      return [...cyRoomtypesToAdd, ...cyRoomtypeCollection];
    }
    return cyRoomtypeCollection;
  }
}
