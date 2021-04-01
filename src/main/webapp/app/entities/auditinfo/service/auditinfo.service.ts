import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAuditinfo, getAuditinfoIdentifier } from '../auditinfo.model';

export type EntityResponseType = HttpResponse<IAuditinfo>;
export type EntityArrayResponseType = HttpResponse<IAuditinfo[]>;

@Injectable({ providedIn: 'root' })
export class AuditinfoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/auditinfos');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/auditinfos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(auditinfo: IAuditinfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditinfo);
    return this.http
      .post<IAuditinfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditinfo: IAuditinfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditinfo);
    return this.http
      .put<IAuditinfo>(`${this.resourceUrl}/${getAuditinfoIdentifier(auditinfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(auditinfo: IAuditinfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditinfo);
    return this.http
      .patch<IAuditinfo>(`${this.resourceUrl}/${getAuditinfoIdentifier(auditinfo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditinfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditinfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditinfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addAuditinfoToCollectionIfMissing(
    auditinfoCollection: IAuditinfo[],
    ...auditinfosToCheck: (IAuditinfo | null | undefined)[]
  ): IAuditinfo[] {
    const auditinfos: IAuditinfo[] = auditinfosToCheck.filter(isPresent);
    if (auditinfos.length > 0) {
      const auditinfoCollectionIdentifiers = auditinfoCollection.map(auditinfoItem => getAuditinfoIdentifier(auditinfoItem)!);
      const auditinfosToAdd = auditinfos.filter(auditinfoItem => {
        const auditinfoIdentifier = getAuditinfoIdentifier(auditinfoItem);
        if (auditinfoIdentifier == null || auditinfoCollectionIdentifiers.includes(auditinfoIdentifier)) {
          return false;
        }
        auditinfoCollectionIdentifiers.push(auditinfoIdentifier);
        return true;
      });
      return [...auditinfosToAdd, ...auditinfoCollection];
    }
    return auditinfoCollection;
  }

  protected convertDateFromClient(auditinfo: IAuditinfo): IAuditinfo {
    return Object.assign({}, auditinfo, {
      auditdate: auditinfo.auditdate?.isValid() ? auditinfo.auditdate.toJSON() : undefined,
      audittime: auditinfo.audittime?.isValid() ? auditinfo.audittime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.auditdate = res.body.auditdate ? dayjs(res.body.auditdate) : undefined;
      res.body.audittime = res.body.audittime ? dayjs(res.body.audittime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auditinfo: IAuditinfo) => {
        auditinfo.auditdate = auditinfo.auditdate ? dayjs(auditinfo.auditdate) : undefined;
        auditinfo.audittime = auditinfo.audittime ? dayjs(auditinfo.audittime) : undefined;
      });
    }
    return res;
  }
}
