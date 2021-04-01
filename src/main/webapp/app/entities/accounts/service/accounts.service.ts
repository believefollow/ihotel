import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAccounts, getAccountsIdentifier } from '../accounts.model';

export type EntityResponseType = HttpResponse<IAccounts>;
export type EntityArrayResponseType = HttpResponse<IAccounts[]>;

@Injectable({ providedIn: 'root' })
export class AccountsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/accounts');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/accounts');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .post<IAccounts>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .put<IAccounts>(`${this.resourceUrl}/${getAccountsIdentifier(accounts) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(accounts: IAccounts): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accounts);
    return this.http
      .patch<IAccounts>(`${this.resourceUrl}/${getAccountsIdentifier(accounts) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccounts[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccounts[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addAccountsToCollectionIfMissing(accountsCollection: IAccounts[], ...accountsToCheck: (IAccounts | null | undefined)[]): IAccounts[] {
    const accounts: IAccounts[] = accountsToCheck.filter(isPresent);
    if (accounts.length > 0) {
      const accountsCollectionIdentifiers = accountsCollection.map(accountsItem => getAccountsIdentifier(accountsItem)!);
      const accountsToAdd = accounts.filter(accountsItem => {
        const accountsIdentifier = getAccountsIdentifier(accountsItem);
        if (accountsIdentifier == null || accountsCollectionIdentifiers.includes(accountsIdentifier)) {
          return false;
        }
        accountsCollectionIdentifiers.push(accountsIdentifier);
        return true;
      });
      return [...accountsToAdd, ...accountsCollection];
    }
    return accountsCollection;
  }

  protected convertDateFromClient(accounts: IAccounts): IAccounts {
    return Object.assign({}, accounts, {
      consumetime: accounts.consumetime?.isValid() ? accounts.consumetime.toJSON() : undefined,
      hoteltime: accounts.hoteltime?.isValid() ? accounts.hoteltime.toJSON() : undefined,
      jzhotel: accounts.jzhotel?.isValid() ? accounts.jzhotel.toJSON() : undefined,
      jztime: accounts.jztime?.isValid() ? accounts.jztime.toJSON() : undefined,
      bjtime: accounts.bjtime?.isValid() ? accounts.bjtime.toJSON() : undefined,
      rz: accounts.rz?.isValid() ? accounts.rz.toJSON() : undefined,
      gz: accounts.gz?.isValid() ? accounts.gz.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.consumetime = res.body.consumetime ? dayjs(res.body.consumetime) : undefined;
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.jzhotel = res.body.jzhotel ? dayjs(res.body.jzhotel) : undefined;
      res.body.jztime = res.body.jztime ? dayjs(res.body.jztime) : undefined;
      res.body.bjtime = res.body.bjtime ? dayjs(res.body.bjtime) : undefined;
      res.body.rz = res.body.rz ? dayjs(res.body.rz) : undefined;
      res.body.gz = res.body.gz ? dayjs(res.body.gz) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accounts: IAccounts) => {
        accounts.consumetime = accounts.consumetime ? dayjs(accounts.consumetime) : undefined;
        accounts.hoteltime = accounts.hoteltime ? dayjs(accounts.hoteltime) : undefined;
        accounts.jzhotel = accounts.jzhotel ? dayjs(accounts.jzhotel) : undefined;
        accounts.jztime = accounts.jztime ? dayjs(accounts.jztime) : undefined;
        accounts.bjtime = accounts.bjtime ? dayjs(accounts.bjtime) : undefined;
        accounts.rz = accounts.rz ? dayjs(accounts.rz) : undefined;
        accounts.gz = accounts.gz ? dayjs(accounts.gz) : undefined;
      });
    }
    return res;
  }
}
