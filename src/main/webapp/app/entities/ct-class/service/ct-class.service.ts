import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICtClass, getCtClassIdentifier } from '../ct-class.model';

export type EntityResponseType = HttpResponse<ICtClass>;
export type EntityArrayResponseType = HttpResponse<ICtClass[]>;

@Injectable({ providedIn: 'root' })
export class CtClassService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ct-classes');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/ct-classes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ctClass: ICtClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ctClass);
    return this.http
      .post<ICtClass>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ctClass: ICtClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ctClass);
    return this.http
      .put<ICtClass>(`${this.resourceUrl}/${getCtClassIdentifier(ctClass) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ctClass: ICtClass): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ctClass);
    return this.http
      .patch<ICtClass>(`${this.resourceUrl}/${getCtClassIdentifier(ctClass) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICtClass>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICtClass[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICtClass[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCtClassToCollectionIfMissing(ctClassCollection: ICtClass[], ...ctClassesToCheck: (ICtClass | null | undefined)[]): ICtClass[] {
    const ctClasses: ICtClass[] = ctClassesToCheck.filter(isPresent);
    if (ctClasses.length > 0) {
      const ctClassCollectionIdentifiers = ctClassCollection.map(ctClassItem => getCtClassIdentifier(ctClassItem)!);
      const ctClassesToAdd = ctClasses.filter(ctClassItem => {
        const ctClassIdentifier = getCtClassIdentifier(ctClassItem);
        if (ctClassIdentifier == null || ctClassCollectionIdentifiers.includes(ctClassIdentifier)) {
          return false;
        }
        ctClassCollectionIdentifiers.push(ctClassIdentifier);
        return true;
      });
      return [...ctClassesToAdd, ...ctClassCollection];
    }
    return ctClassCollection;
  }

  protected convertDateFromClient(ctClass: ICtClass): ICtClass {
    return Object.assign({}, ctClass, {
      dt: ctClass.dt?.isValid() ? ctClass.dt.toJSON() : undefined,
      gotime: ctClass.gotime?.isValid() ? ctClass.gotime.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dt = res.body.dt ? dayjs(res.body.dt) : undefined;
      res.body.gotime = res.body.gotime ? dayjs(res.body.gotime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ctClass: ICtClass) => {
        ctClass.dt = ctClass.dt ? dayjs(ctClass.dt) : undefined;
        ctClass.gotime = ctClass.gotime ? dayjs(ctClass.gotime) : undefined;
      });
    }
    return res;
  }
}
