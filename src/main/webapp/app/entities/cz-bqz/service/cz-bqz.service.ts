import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICzBqz, getCzBqzIdentifier } from '../cz-bqz.model';

export type EntityResponseType = HttpResponse<ICzBqz>;
export type EntityArrayResponseType = HttpResponse<ICzBqz[]>;

@Injectable({ providedIn: 'root' })
export class CzBqzService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cz-bqzs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/cz-bqzs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(czBqz: ICzBqz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czBqz);
    return this.http
      .post<ICzBqz>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(czBqz: ICzBqz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czBqz);
    return this.http
      .put<ICzBqz>(`${this.resourceUrl}/${getCzBqzIdentifier(czBqz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(czBqz: ICzBqz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czBqz);
    return this.http
      .patch<ICzBqz>(`${this.resourceUrl}/${getCzBqzIdentifier(czBqz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICzBqz>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICzBqz[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICzBqz[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCzBqzToCollectionIfMissing(czBqzCollection: ICzBqz[], ...czBqzsToCheck: (ICzBqz | null | undefined)[]): ICzBqz[] {
    const czBqzs: ICzBqz[] = czBqzsToCheck.filter(isPresent);
    if (czBqzs.length > 0) {
      const czBqzCollectionIdentifiers = czBqzCollection.map(czBqzItem => getCzBqzIdentifier(czBqzItem)!);
      const czBqzsToAdd = czBqzs.filter(czBqzItem => {
        const czBqzIdentifier = getCzBqzIdentifier(czBqzItem);
        if (czBqzIdentifier == null || czBqzCollectionIdentifiers.includes(czBqzIdentifier)) {
          return false;
        }
        czBqzCollectionIdentifiers.push(czBqzIdentifier);
        return true;
      });
      return [...czBqzsToAdd, ...czBqzCollection];
    }
    return czBqzCollection;
  }

  protected convertDateFromClient(czBqz: ICzBqz): ICzBqz {
    return Object.assign({}, czBqz, {
      rq: czBqz.rq?.isValid() ? czBqz.rq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.rq = res.body.rq ? dayjs(res.body.rq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((czBqz: ICzBqz) => {
        czBqz.rq = czBqz.rq ? dayjs(czBqz.rq) : undefined;
      });
    }
    return res;
  }
}
