import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFtXzs, getFtXzsIdentifier } from '../ft-xzs.model';

export type EntityResponseType = HttpResponse<IFtXzs>;
export type EntityArrayResponseType = HttpResponse<IFtXzs[]>;

@Injectable({ providedIn: 'root' })
export class FtXzsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ft-xzs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/ft-xzs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ftXzs: IFtXzs): Observable<EntityResponseType> {
    return this.http.post<IFtXzs>(this.resourceUrl, ftXzs, { observe: 'response' });
  }

  update(ftXzs: IFtXzs): Observable<EntityResponseType> {
    return this.http.put<IFtXzs>(`${this.resourceUrl}/${getFtXzsIdentifier(ftXzs) as number}`, ftXzs, { observe: 'response' });
  }

  partialUpdate(ftXzs: IFtXzs): Observable<EntityResponseType> {
    return this.http.patch<IFtXzs>(`${this.resourceUrl}/${getFtXzsIdentifier(ftXzs) as number}`, ftXzs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFtXzs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFtXzs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFtXzs[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addFtXzsToCollectionIfMissing(ftXzsCollection: IFtXzs[], ...ftXzsToCheck: (IFtXzs | null | undefined)[]): IFtXzs[] {
    const ftXzs: IFtXzs[] = ftXzsToCheck.filter(isPresent);
    if (ftXzs.length > 0) {
      const ftXzsCollectionIdentifiers = ftXzsCollection.map(ftXzsItem => getFtXzsIdentifier(ftXzsItem)!);
      const ftXzsToAdd = ftXzs.filter(ftXzsItem => {
        const ftXzsIdentifier = getFtXzsIdentifier(ftXzsItem);
        if (ftXzsIdentifier == null || ftXzsCollectionIdentifiers.includes(ftXzsIdentifier)) {
          return false;
        }
        ftXzsCollectionIdentifiers.push(ftXzsIdentifier);
        return true;
      });
      return [...ftXzsToAdd, ...ftXzsCollection];
    }
    return ftXzsCollection;
  }
}
