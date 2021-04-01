import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICyCptype, getCyCptypeIdentifier } from '../cy-cptype.model';

export type EntityResponseType = HttpResponse<ICyCptype>;
export type EntityArrayResponseType = HttpResponse<ICyCptype[]>;

@Injectable({ providedIn: 'root' })
export class CyCptypeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cy-cptypes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/cy-cptypes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(cyCptype: ICyCptype): Observable<EntityResponseType> {
    return this.http.post<ICyCptype>(this.resourceUrl, cyCptype, { observe: 'response' });
  }

  update(cyCptype: ICyCptype): Observable<EntityResponseType> {
    return this.http.put<ICyCptype>(`${this.resourceUrl}/${getCyCptypeIdentifier(cyCptype) as number}`, cyCptype, { observe: 'response' });
  }

  partialUpdate(cyCptype: ICyCptype): Observable<EntityResponseType> {
    return this.http.patch<ICyCptype>(`${this.resourceUrl}/${getCyCptypeIdentifier(cyCptype) as number}`, cyCptype, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICyCptype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICyCptype[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICyCptype[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addCyCptypeToCollectionIfMissing(cyCptypeCollection: ICyCptype[], ...cyCptypesToCheck: (ICyCptype | null | undefined)[]): ICyCptype[] {
    const cyCptypes: ICyCptype[] = cyCptypesToCheck.filter(isPresent);
    if (cyCptypes.length > 0) {
      const cyCptypeCollectionIdentifiers = cyCptypeCollection.map(cyCptypeItem => getCyCptypeIdentifier(cyCptypeItem)!);
      const cyCptypesToAdd = cyCptypes.filter(cyCptypeItem => {
        const cyCptypeIdentifier = getCyCptypeIdentifier(cyCptypeItem);
        if (cyCptypeIdentifier == null || cyCptypeCollectionIdentifiers.includes(cyCptypeIdentifier)) {
          return false;
        }
        cyCptypeCollectionIdentifiers.push(cyCptypeIdentifier);
        return true;
      });
      return [...cyCptypesToAdd, ...cyCptypeCollection];
    }
    return cyCptypeCollection;
  }
}
