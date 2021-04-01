import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDPdb, getDPdbIdentifier } from '../d-pdb.model';

export type EntityResponseType = HttpResponse<IDPdb>;
export type EntityArrayResponseType = HttpResponse<IDPdb[]>;

@Injectable({ providedIn: 'root' })
export class DPdbService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-pdbs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-pdbs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dPdb: IDPdb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dPdb);
    return this.http
      .post<IDPdb>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dPdb: IDPdb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dPdb);
    return this.http
      .put<IDPdb>(`${this.resourceUrl}/${getDPdbIdentifier(dPdb) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(dPdb: IDPdb): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dPdb);
    return this.http
      .patch<IDPdb>(`${this.resourceUrl}/${getDPdbIdentifier(dPdb) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDPdb>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDPdb[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDPdb[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addDPdbToCollectionIfMissing(dPdbCollection: IDPdb[], ...dPdbsToCheck: (IDPdb | null | undefined)[]): IDPdb[] {
    const dPdbs: IDPdb[] = dPdbsToCheck.filter(isPresent);
    if (dPdbs.length > 0) {
      const dPdbCollectionIdentifiers = dPdbCollection.map(dPdbItem => getDPdbIdentifier(dPdbItem)!);
      const dPdbsToAdd = dPdbs.filter(dPdbItem => {
        const dPdbIdentifier = getDPdbIdentifier(dPdbItem);
        if (dPdbIdentifier == null || dPdbCollectionIdentifiers.includes(dPdbIdentifier)) {
          return false;
        }
        dPdbCollectionIdentifiers.push(dPdbIdentifier);
        return true;
      });
      return [...dPdbsToAdd, ...dPdbCollection];
    }
    return dPdbCollection;
  }

  protected convertDateFromClient(dPdb: IDPdb): IDPdb {
    return Object.assign({}, dPdb, {
      begindate: dPdb.begindate?.isValid() ? dPdb.begindate.toJSON() : undefined,
      enddate: dPdb.enddate?.isValid() ? dPdb.enddate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.begindate = res.body.begindate ? dayjs(res.body.begindate) : undefined;
      res.body.enddate = res.body.enddate ? dayjs(res.body.enddate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dPdb: IDPdb) => {
        dPdb.begindate = dPdb.begindate ? dayjs(dPdb.begindate) : undefined;
        dPdb.enddate = dPdb.enddate ? dayjs(dPdb.enddate) : undefined;
      });
    }
    return res;
  }
}
