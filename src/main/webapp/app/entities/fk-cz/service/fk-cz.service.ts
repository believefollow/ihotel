import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IFkCz, getFkCzIdentifier } from '../fk-cz.model';

export type EntityResponseType = HttpResponse<IFkCz>;
export type EntityArrayResponseType = HttpResponse<IFkCz[]>;

@Injectable({ providedIn: 'root' })
export class FkCzService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/fk-czs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/fk-czs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fkCz: IFkCz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fkCz);
    return this.http
      .post<IFkCz>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fkCz: IFkCz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fkCz);
    return this.http
      .put<IFkCz>(`${this.resourceUrl}/${getFkCzIdentifier(fkCz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(fkCz: IFkCz): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fkCz);
    return this.http
      .patch<IFkCz>(`${this.resourceUrl}/${getFkCzIdentifier(fkCz) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFkCz>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFkCz[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFkCz[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addFkCzToCollectionIfMissing(fkCzCollection: IFkCz[], ...fkCzsToCheck: (IFkCz | null | undefined)[]): IFkCz[] {
    const fkCzs: IFkCz[] = fkCzsToCheck.filter(isPresent);
    if (fkCzs.length > 0) {
      const fkCzCollectionIdentifiers = fkCzCollection.map(fkCzItem => getFkCzIdentifier(fkCzItem)!);
      const fkCzsToAdd = fkCzs.filter(fkCzItem => {
        const fkCzIdentifier = getFkCzIdentifier(fkCzItem);
        if (fkCzIdentifier == null || fkCzCollectionIdentifiers.includes(fkCzIdentifier)) {
          return false;
        }
        fkCzCollectionIdentifiers.push(fkCzIdentifier);
        return true;
      });
      return [...fkCzsToAdd, ...fkCzCollection];
    }
    return fkCzCollection;
  }

  protected convertDateFromClient(fkCz: IFkCz): IFkCz {
    return Object.assign({}, fkCz, {
      hoteltime: fkCz.hoteltime?.isValid() ? fkCz.hoteltime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fkCz: IFkCz) => {
        fkCz.hoteltime = fkCz.hoteltime ? dayjs(fkCz.hoteltime) : undefined;
      });
    }
    return res;
  }
}
