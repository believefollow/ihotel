import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICheckCzl, getCheckCzlIdentifier } from '../check-czl.model';

export type EntityResponseType = HttpResponse<ICheckCzl>;
export type EntityArrayResponseType = HttpResponse<ICheckCzl[]>;

@Injectable({ providedIn: 'root' })
export class CheckCzlService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/check-czls');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/check-czls');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(checkCzl: ICheckCzl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkCzl);
    return this.http
      .post<ICheckCzl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(checkCzl: ICheckCzl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkCzl);
    return this.http
      .put<ICheckCzl>(`${this.resourceUrl}/${getCheckCzlIdentifier(checkCzl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(checkCzl: ICheckCzl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkCzl);
    return this.http
      .patch<ICheckCzl>(`${this.resourceUrl}/${getCheckCzlIdentifier(checkCzl) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICheckCzl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckCzl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckCzl[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCheckCzlToCollectionIfMissing(checkCzlCollection: ICheckCzl[], ...checkCzlsToCheck: (ICheckCzl | null | undefined)[]): ICheckCzl[] {
    const checkCzls: ICheckCzl[] = checkCzlsToCheck.filter(isPresent);
    if (checkCzls.length > 0) {
      const checkCzlCollectionIdentifiers = checkCzlCollection.map(checkCzlItem => getCheckCzlIdentifier(checkCzlItem)!);
      const checkCzlsToAdd = checkCzls.filter(checkCzlItem => {
        const checkCzlIdentifier = getCheckCzlIdentifier(checkCzlItem);
        if (checkCzlIdentifier == null || checkCzlCollectionIdentifiers.includes(checkCzlIdentifier)) {
          return false;
        }
        checkCzlCollectionIdentifiers.push(checkCzlIdentifier);
        return true;
      });
      return [...checkCzlsToAdd, ...checkCzlCollection];
    }
    return checkCzlCollection;
  }

  protected convertDateFromClient(checkCzl: ICheckCzl): ICheckCzl {
    return Object.assign({}, checkCzl, {
      hoteltime: checkCzl.hoteltime?.isValid() ? checkCzl.hoteltime.toJSON() : undefined,
      entertime: checkCzl.entertime?.isValid() ? checkCzl.entertime.toJSON() : undefined,
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
      res.body.forEach((checkCzl: ICheckCzl) => {
        checkCzl.hoteltime = checkCzl.hoteltime ? dayjs(checkCzl.hoteltime) : undefined;
        checkCzl.entertime = checkCzl.entertime ? dayjs(checkCzl.entertime) : undefined;
      });
    }
    return res;
  }
}
