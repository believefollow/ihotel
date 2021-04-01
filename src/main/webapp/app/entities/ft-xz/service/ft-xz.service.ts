import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFtXz, getFtXzIdentifier } from '../ft-xz.model';

export type EntityResponseType = HttpResponse<IFtXz>;
export type EntityArrayResponseType = HttpResponse<IFtXz[]>;

@Injectable({ providedIn: 'root' })
export class FtXzService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ft-xzs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/ft-xzs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ftXz: IFtXz): Observable<EntityResponseType> {
    return this.http.post<IFtXz>(this.resourceUrl, ftXz, { observe: 'response' });
  }

  update(ftXz: IFtXz): Observable<EntityResponseType> {
    return this.http.put<IFtXz>(`${this.resourceUrl}/${getFtXzIdentifier(ftXz) as number}`, ftXz, { observe: 'response' });
  }

  partialUpdate(ftXz: IFtXz): Observable<EntityResponseType> {
    return this.http.patch<IFtXz>(`${this.resourceUrl}/${getFtXzIdentifier(ftXz) as number}`, ftXz, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFtXz>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFtXz[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFtXz[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addFtXzToCollectionIfMissing(ftXzCollection: IFtXz[], ...ftXzsToCheck: (IFtXz | null | undefined)[]): IFtXz[] {
    const ftXzs: IFtXz[] = ftXzsToCheck.filter(isPresent);
    if (ftXzs.length > 0) {
      const ftXzCollectionIdentifiers = ftXzCollection.map(ftXzItem => getFtXzIdentifier(ftXzItem)!);
      const ftXzsToAdd = ftXzs.filter(ftXzItem => {
        const ftXzIdentifier = getFtXzIdentifier(ftXzItem);
        if (ftXzIdentifier == null || ftXzCollectionIdentifiers.includes(ftXzIdentifier)) {
          return false;
        }
        ftXzCollectionIdentifiers.push(ftXzIdentifier);
        return true;
      });
      return [...ftXzsToAdd, ...ftXzCollection];
    }
    return ftXzCollection;
  }
}
