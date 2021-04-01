import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICzlCz, getCzlCzIdentifier } from '../czl-cz.model';

export type EntityResponseType = HttpResponse<ICzlCz>;
export type EntityArrayResponseType = HttpResponse<ICzlCz[]>;

@Injectable({ providedIn: 'root' })
export class CzlCzService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/czl-czs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/czl-czs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(czlCz: ICzlCz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czlCz);
    return this.http
      .post<ICzlCz>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(czlCz: ICzlCz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czlCz);
    return this.http
      .put<ICzlCz>(`${this.resourceUrl}/${getCzlCzIdentifier(czlCz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(czlCz: ICzlCz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(czlCz);
    return this.http
      .patch<ICzlCz>(`${this.resourceUrl}/${getCzlCzIdentifier(czlCz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICzlCz>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICzlCz[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICzlCz[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCzlCzToCollectionIfMissing(czlCzCollection: ICzlCz[], ...czlCzsToCheck: (ICzlCz | null | undefined)[]): ICzlCz[] {
    const czlCzs: ICzlCz[] = czlCzsToCheck.filter(isPresent);
    if (czlCzs.length > 0) {
      const czlCzCollectionIdentifiers = czlCzCollection.map(czlCzItem => getCzlCzIdentifier(czlCzItem)!);
      const czlCzsToAdd = czlCzs.filter(czlCzItem => {
        const czlCzIdentifier = getCzlCzIdentifier(czlCzItem);
        if (czlCzIdentifier == null || czlCzCollectionIdentifiers.includes(czlCzIdentifier)) {
          return false;
        }
        czlCzCollectionIdentifiers.push(czlCzIdentifier);
        return true;
      });
      return [...czlCzsToAdd, ...czlCzCollection];
    }
    return czlCzCollection;
  }

  protected convertDateFromClient(czlCz: ICzlCz): ICzlCz {
    return Object.assign({}, czlCz, {
      tjrq: czlCz.tjrq?.isValid() ? czlCz.tjrq.toJSON() : undefined,
      hoteltime: czlCz.hoteltime?.isValid() ? czlCz.hoteltime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.tjrq = res.body.tjrq ? dayjs(res.body.tjrq) : undefined;
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((czlCz: ICzlCz) => {
        czlCz.tjrq = czlCz.tjrq ? dayjs(czlCz.tjrq) : undefined;
        czlCz.hoteltime = czlCz.hoteltime ? dayjs(czlCz.hoteltime) : undefined;
      });
    }
    return res;
  }
}
