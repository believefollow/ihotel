import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IClassreport, getClassreportIdentifier } from '../classreport.model';

export type EntityResponseType = HttpResponse<IClassreport>;
export type EntityArrayResponseType = HttpResponse<IClassreport[]>;

@Injectable({ providedIn: 'root' })
export class ClassreportService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/classreports');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/classreports');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(classreport: IClassreport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classreport);
    return this.http
      .post<IClassreport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(classreport: IClassreport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classreport);
    return this.http
      .put<IClassreport>(`${this.resourceUrl}/${getClassreportIdentifier(classreport) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(classreport: IClassreport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classreport);
    return this.http
      .patch<IClassreport>(`${this.resourceUrl}/${getClassreportIdentifier(classreport) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClassreport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClassreport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClassreport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addClassreportToCollectionIfMissing(
    classreportCollection: IClassreport[],
    ...classreportsToCheck: (IClassreport | null | undefined)[]
  ): IClassreport[] {
    const classreports: IClassreport[] = classreportsToCheck.filter(isPresent);
    if (classreports.length > 0) {
      const classreportCollectionIdentifiers = classreportCollection.map(classreportItem => getClassreportIdentifier(classreportItem)!);
      const classreportsToAdd = classreports.filter(classreportItem => {
        const classreportIdentifier = getClassreportIdentifier(classreportItem);
        if (classreportIdentifier == null || classreportCollectionIdentifiers.includes(classreportIdentifier)) {
          return false;
        }
        classreportCollectionIdentifiers.push(classreportIdentifier);
        return true;
      });
      return [...classreportsToAdd, ...classreportCollection];
    }
    return classreportCollection;
  }

  protected convertDateFromClient(classreport: IClassreport): IClassreport {
    return Object.assign({}, classreport, {
      dt: classreport.dt?.isValid() ? classreport.dt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dt = res.body.dt ? dayjs(res.body.dt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((classreport: IClassreport) => {
        classreport.dt = classreport.dt ? dayjs(classreport.dt) : undefined;
      });
    }
    return res;
  }
}
