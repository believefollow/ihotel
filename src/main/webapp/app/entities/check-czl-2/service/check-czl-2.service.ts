import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICheckCzl2, getCheckCzl2Identifier } from '../check-czl-2.model';

export type EntityResponseType = HttpResponse<ICheckCzl2>;
export type EntityArrayResponseType = HttpResponse<ICheckCzl2[]>;

@Injectable({ providedIn: 'root' })
export class CheckCzl2Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/check-czl-2-s');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/check-czl-2-s');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(checkCzl2: ICheckCzl2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkCzl2);
    return this.http
      .post<ICheckCzl2>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(checkCzl2: ICheckCzl2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkCzl2);
    return this.http
      .put<ICheckCzl2>(`${this.resourceUrl}/${getCheckCzl2Identifier(checkCzl2) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(checkCzl2: ICheckCzl2): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkCzl2);
    return this.http
      .patch<ICheckCzl2>(`${this.resourceUrl}/${getCheckCzl2Identifier(checkCzl2) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICheckCzl2>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckCzl2[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckCzl2[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCheckCzl2ToCollectionIfMissing(
    checkCzl2Collection: ICheckCzl2[],
    ...checkCzl2sToCheck: (ICheckCzl2 | null | undefined)[]
  ): ICheckCzl2[] {
    const checkCzl2s: ICheckCzl2[] = checkCzl2sToCheck.filter(isPresent);
    if (checkCzl2s.length > 0) {
      const checkCzl2CollectionIdentifiers = checkCzl2Collection.map(checkCzl2Item => getCheckCzl2Identifier(checkCzl2Item)!);
      const checkCzl2sToAdd = checkCzl2s.filter(checkCzl2Item => {
        const checkCzl2Identifier = getCheckCzl2Identifier(checkCzl2Item);
        if (checkCzl2Identifier == null || checkCzl2CollectionIdentifiers.includes(checkCzl2Identifier)) {
          return false;
        }
        checkCzl2CollectionIdentifiers.push(checkCzl2Identifier);
        return true;
      });
      return [...checkCzl2sToAdd, ...checkCzl2Collection];
    }
    return checkCzl2Collection;
  }

  protected convertDateFromClient(checkCzl2: ICheckCzl2): ICheckCzl2 {
    return Object.assign({}, checkCzl2, {
      hoteltime: checkCzl2.hoteltime?.isValid() ? checkCzl2.hoteltime.toJSON() : undefined,
      entertime: checkCzl2.entertime?.isValid() ? checkCzl2.entertime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.entertime = res.body.entertime ? dayjs(res.body.entertime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((checkCzl2: ICheckCzl2) => {
        checkCzl2.hoteltime = checkCzl2.hoteltime ? dayjs(checkCzl2.hoteltime) : undefined;
        checkCzl2.entertime = checkCzl2.entertime ? dayjs(checkCzl2.entertime) : undefined;
      });
    }
    return res;
  }
}
