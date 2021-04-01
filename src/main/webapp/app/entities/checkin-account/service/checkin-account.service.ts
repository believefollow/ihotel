import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICheckinAccount, getCheckinAccountIdentifier } from '../checkin-account.model';

export type EntityResponseType = HttpResponse<ICheckinAccount>;
export type EntityArrayResponseType = HttpResponse<ICheckinAccount[]>;

@Injectable({ providedIn: 'root' })
export class CheckinAccountService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/checkin-accounts');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/checkin-accounts');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(checkinAccount: ICheckinAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinAccount);
    return this.http
      .post<ICheckinAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(checkinAccount: ICheckinAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinAccount);
    return this.http
      .put<ICheckinAccount>(`${this.resourceUrl}/${getCheckinAccountIdentifier(checkinAccount) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(checkinAccount: ICheckinAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(checkinAccount);
    return this.http
      .patch<ICheckinAccount>(`${this.resourceUrl}/${getCheckinAccountIdentifier(checkinAccount) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICheckinAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckinAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICheckinAccount[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCheckinAccountToCollectionIfMissing(
    checkinAccountCollection: ICheckinAccount[],
    ...checkinAccountsToCheck: (ICheckinAccount | null | undefined)[]
  ): ICheckinAccount[] {
    const checkinAccounts: ICheckinAccount[] = checkinAccountsToCheck.filter(isPresent);
    if (checkinAccounts.length > 0) {
      const checkinAccountCollectionIdentifiers = checkinAccountCollection.map(
        checkinAccountItem => getCheckinAccountIdentifier(checkinAccountItem)!
      );
      const checkinAccountsToAdd = checkinAccounts.filter(checkinAccountItem => {
        const checkinAccountIdentifier = getCheckinAccountIdentifier(checkinAccountItem);
        if (checkinAccountIdentifier == null || checkinAccountCollectionIdentifiers.includes(checkinAccountIdentifier)) {
          return false;
        }
        checkinAccountCollectionIdentifiers.push(checkinAccountIdentifier);
        return true;
      });
      return [...checkinAccountsToAdd, ...checkinAccountCollection];
    }
    return checkinAccountCollection;
  }

  protected convertDateFromClient(checkinAccount: ICheckinAccount): ICheckinAccount {
    return Object.assign({}, checkinAccount, {
      indatetime: checkinAccount.indatetime?.isValid() ? checkinAccount.indatetime.toJSON() : undefined,
      gotime: checkinAccount.gotime?.isValid() ? checkinAccount.gotime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.indatetime = res.body.indatetime ? dayjs(res.body.indatetime) : undefined;
      res.body.gotime = res.body.gotime ? dayjs(res.body.gotime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((checkinAccount: ICheckinAccount) => {
        checkinAccount.indatetime = checkinAccount.indatetime ? dayjs(checkinAccount.indatetime) : undefined;
        checkinAccount.gotime = checkinAccount.gotime ? dayjs(checkinAccount.gotime) : undefined;
      });
    }
    return res;
  }
}
