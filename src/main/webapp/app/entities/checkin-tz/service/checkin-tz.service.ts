import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICheckinTz, getCheckinTzIdentifier } from '../checkin-tz.model';

export type EntityResponseType = HttpResponse<ICheckinTz>;
export type EntityArrayResponseType = HttpResponse<ICheckinTz[]>;

@Injectable({ providedIn: 'root' })
export class CheckinTzService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/checkin-tzs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/checkin-tzs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(checkinTz: ICheckinTz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinTz);
    return this.http
      .post<ICheckinTz>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(checkinTz: ICheckinTz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinTz);
    return this.http
      .put<ICheckinTz>(`${this.resourceUrl}/${getCheckinTzIdentifier(checkinTz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(checkinTz: ICheckinTz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinTz);
    return this.http
      .patch<ICheckinTz>(`${this.resourceUrl}/${getCheckinTzIdentifier(checkinTz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICheckinTz>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckinTz[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckinTz[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCheckinTzToCollectionIfMissing(
    checkinTzCollection: ICheckinTz[],
    ...checkinTzsToCheck: (ICheckinTz | null | undefined)[]
  ): ICheckinTz[] {
    const checkinTzs: ICheckinTz[] = checkinTzsToCheck.filter(isPresent);
    if (checkinTzs.length > 0) {
      const checkinTzCollectionIdentifiers = checkinTzCollection.map(checkinTzItem => getCheckinTzIdentifier(checkinTzItem)!);
      const checkinTzsToAdd = checkinTzs.filter(checkinTzItem => {
        const checkinTzIdentifier = getCheckinTzIdentifier(checkinTzItem);
        if (checkinTzIdentifier == null || checkinTzCollectionIdentifiers.includes(checkinTzIdentifier)) {
          return false;
        }
        checkinTzCollectionIdentifiers.push(checkinTzIdentifier);
        return true;
      });
      return [...checkinTzsToAdd, ...checkinTzCollection];
    }
    return checkinTzCollection;
  }

  protected convertDateFromClient(checkinTz: ICheckinTz): ICheckinTz {
    return Object.assign({}, checkinTz, {
      hoteltime: checkinTz.hoteltime?.isValid() ? checkinTz.hoteltime.toJSON() : undefined,
      indatetime: checkinTz.indatetime?.isValid() ? checkinTz.indatetime.toJSON() : undefined,
      gotime: checkinTz.gotime?.isValid() ? checkinTz.gotime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.indatetime = res.body.indatetime ? dayjs(res.body.indatetime) : undefined;
      res.body.gotime = res.body.gotime ? dayjs(res.body.gotime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((checkinTz: ICheckinTz) => {
        checkinTz.hoteltime = checkinTz.hoteltime ? dayjs(checkinTz.hoteltime) : undefined;
        checkinTz.indatetime = checkinTz.indatetime ? dayjs(checkinTz.indatetime) : undefined;
        checkinTz.gotime = checkinTz.gotime ? dayjs(checkinTz.gotime) : undefined;
      });
    }
    return res;
  }
}
