import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IClassBak, getClassBakIdentifier } from '../class-bak.model';

export type EntityResponseType = HttpResponse<IClassBak>;
export type EntityArrayResponseType = HttpResponse<IClassBak[]>;

@Injectable({ providedIn: 'root' })
export class ClassBakService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/class-baks');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/class-baks');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(classBak: IClassBak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classBak);
    return this.http
      .post<IClassBak>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(classBak: IClassBak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classBak);
    return this.http
      .put<IClassBak>(`${this.resourceUrl}/${getClassBakIdentifier(classBak) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(classBak: IClassBak): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classBak);
    return this.http
      .patch<IClassBak>(`${this.resourceUrl}/${getClassBakIdentifier(classBak) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClassBak>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClassBak[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClassBak[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addClassBakToCollectionIfMissing(classBakCollection: IClassBak[], ...classBaksToCheck: (IClassBak | null | undefined)[]): IClassBak[] {
    const classBaks: IClassBak[] = classBaksToCheck.filter(isPresent);
    if (classBaks.length > 0) {
      const classBakCollectionIdentifiers = classBakCollection.map(classBakItem => getClassBakIdentifier(classBakItem)!);
      const classBaksToAdd = classBaks.filter(classBakItem => {
        const classBakIdentifier = getClassBakIdentifier(classBakItem);
        if (classBakIdentifier == null || classBakCollectionIdentifiers.includes(classBakIdentifier)) {
          return false;
        }
        classBakCollectionIdentifiers.push(classBakIdentifier);
        return true;
      });
      return [...classBaksToAdd, ...classBakCollection];
    }
    return classBakCollection;
  }

  protected convertDateFromClient(classBak: IClassBak): IClassBak {
    return Object.assign({}, classBak, {
      dt: classBak.dt?.isValid() ? classBak.dt.toJSON() : undefined,
      rq: classBak.rq?.isValid() ? classBak.rq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dt = res.body.dt ? dayjs(res.body.dt) : undefined;
      res.body.rq = res.body.rq ? dayjs(res.body.rq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((classBak: IClassBak) => {
        classBak.dt = classBak.dt ? dayjs(classBak.dt) : undefined;
        classBak.rq = classBak.rq ? dayjs(classBak.rq) : undefined;
      });
    }
    return res;
  }
}
