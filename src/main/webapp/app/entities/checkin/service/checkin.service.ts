import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICheckin, getCheckinIdentifier } from '../checkin.model';

export type EntityResponseType = HttpResponse<ICheckin>;
export type EntityArrayResponseType = HttpResponse<ICheckin[]>;

@Injectable({ providedIn: 'root' })
export class CheckinService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/checkins');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/checkins');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(checkin: ICheckin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkin);
    return this.http
      .post<ICheckin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(checkin: ICheckin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkin);
    return this.http
      .put<ICheckin>(`${this.resourceUrl}/${getCheckinIdentifier(checkin) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(checkin: ICheckin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkin);
    return this.http
      .patch<ICheckin>(`${this.resourceUrl}/${getCheckinIdentifier(checkin) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICheckin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckin[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCheckinToCollectionIfMissing(checkinCollection: ICheckin[], ...checkinsToCheck: (ICheckin | null | undefined)[]): ICheckin[] {
    const checkins: ICheckin[] = checkinsToCheck.filter(isPresent);
    if (checkins.length > 0) {
      const checkinCollectionIdentifiers = checkinCollection.map(checkinItem => getCheckinIdentifier(checkinItem)!);
      const checkinsToAdd = checkins.filter(checkinItem => {
        const checkinIdentifier = getCheckinIdentifier(checkinItem);
        if (checkinIdentifier == null || checkinCollectionIdentifiers.includes(checkinIdentifier)) {
          return false;
        }
        checkinCollectionIdentifiers.push(checkinIdentifier);
        return true;
      });
      return [...checkinsToAdd, ...checkinCollection];
    }
    return checkinCollection;
  }

  protected convertDateFromClient(checkin: ICheckin): ICheckin {
    return Object.assign({}, checkin, {
      hoteltime: checkin.hoteltime?.isValid() ? checkin.hoteltime.toJSON() : undefined,
      indatetime: checkin.indatetime?.isValid() ? checkin.indatetime.toJSON() : undefined,
      gotime: checkin.gotime?.isValid() ? checkin.gotime.toJSON() : undefined,
      jxtime: checkin.jxtime?.isValid() ? checkin.jxtime.toJSON() : undefined,
      fkrq: checkin.fkrq?.isValid() ? checkin.fkrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.indatetime = res.body.indatetime ? dayjs(res.body.indatetime) : undefined;
      res.body.gotime = res.body.gotime ? dayjs(res.body.gotime) : undefined;
      res.body.jxtime = res.body.jxtime ? dayjs(res.body.jxtime) : undefined;
      res.body.fkrq = res.body.fkrq ? dayjs(res.body.fkrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((checkin: ICheckin) => {
        checkin.hoteltime = checkin.hoteltime ? dayjs(checkin.hoteltime) : undefined;
        checkin.indatetime = checkin.indatetime ? dayjs(checkin.indatetime) : undefined;
        checkin.gotime = checkin.gotime ? dayjs(checkin.gotime) : undefined;
        checkin.jxtime = checkin.jxtime ? dayjs(checkin.jxtime) : undefined;
        checkin.fkrq = checkin.fkrq ? dayjs(checkin.fkrq) : undefined;
      });
    }
    return res;
  }
}
