import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICheckinBak, getCheckinBakIdentifier } from '../checkin-bak.model';

export type EntityResponseType = HttpResponse<ICheckinBak>;
export type EntityArrayResponseType = HttpResponse<ICheckinBak[]>;

@Injectable({ providedIn: 'root' })
export class CheckinBakService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/checkin-baks');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/checkin-baks');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(checkinBak: ICheckinBak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinBak);
    return this.http
      .post<ICheckinBak>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(checkinBak: ICheckinBak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinBak);
    return this.http
      .put<ICheckinBak>(`${this.resourceUrl}/${getCheckinBakIdentifier(checkinBak) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(checkinBak: ICheckinBak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinBak);
    return this.http
      .patch<ICheckinBak>(`${this.resourceUrl}/${getCheckinBakIdentifier(checkinBak) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICheckinBak>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckinBak[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckinBak[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCheckinBakToCollectionIfMissing(
    checkinBakCollection: ICheckinBak[],
    ...checkinBaksToCheck: (ICheckinBak | null | undefined)[]
  ): ICheckinBak[] {
    const checkinBaks: ICheckinBak[] = checkinBaksToCheck.filter(isPresent);
    if (checkinBaks.length > 0) {
      const checkinBakCollectionIdentifiers = checkinBakCollection.map(checkinBakItem => getCheckinBakIdentifier(checkinBakItem)!);
      const checkinBaksToAdd = checkinBaks.filter(checkinBakItem => {
        const checkinBakIdentifier = getCheckinBakIdentifier(checkinBakItem);
        if (checkinBakIdentifier == null || checkinBakCollectionIdentifiers.includes(checkinBakIdentifier)) {
          return false;
        }
        checkinBakCollectionIdentifiers.push(checkinBakIdentifier);
        return true;
      });
      return [...checkinBaksToAdd, ...checkinBakCollection];
    }
    return checkinBakCollection;
  }

  protected convertDateFromClient(checkinBak: ICheckinBak): ICheckinBak {
    return Object.assign({}, checkinBak, {
      hoteltime: checkinBak.hoteltime?.isValid() ? checkinBak.hoteltime.toJSON() : undefined,
      indatetime: checkinBak.indatetime?.isValid() ? checkinBak.indatetime.toJSON() : undefined,
      gotime: checkinBak.gotime?.isValid() ? checkinBak.gotime.toJSON() : undefined,
      hoteldate: checkinBak.hoteldate?.isValid() ? checkinBak.hoteldate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.indatetime = res.body.indatetime ? dayjs(res.body.indatetime) : undefined;
      res.body.gotime = res.body.gotime ? dayjs(res.body.gotime) : undefined;
      res.body.hoteldate = res.body.hoteldate ? dayjs(res.body.hoteldate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((checkinBak: ICheckinBak) => {
        checkinBak.hoteltime = checkinBak.hoteltime ? dayjs(checkinBak.hoteltime) : undefined;
        checkinBak.indatetime = checkinBak.indatetime ? dayjs(checkinBak.indatetime) : undefined;
        checkinBak.gotime = checkinBak.gotime ? dayjs(checkinBak.gotime) : undefined;
        checkinBak.hoteldate = checkinBak.hoteldate ? dayjs(checkinBak.hoteldate) : undefined;
      });
    }
    return res;
  }
}
