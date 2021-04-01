import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IClassRename, getClassRenameIdentifier } from '../class-rename.model';

export type EntityResponseType = HttpResponse<IClassRename>;
export type EntityArrayResponseType = HttpResponse<IClassRename[]>;

@Injectable({ providedIn: 'root' })
export class ClassRenameService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/class-renames');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/class-renames');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(classRename: IClassRename): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classRename);
    return this.http
      .post<IClassRename>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(classRename: IClassRename): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classRename);
    return this.http
      .put<IClassRename>(`${this.resourceUrl}/${getClassRenameIdentifier(classRename) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(classRename: IClassRename): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(classRename);
    return this.http
      .patch<IClassRename>(`${this.resourceUrl}/${getClassRenameIdentifier(classRename) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClassRename>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClassRename[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClassRename[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addClassRenameToCollectionIfMissing(
    classRenameCollection: IClassRename[],
    ...classRenamesToCheck: (IClassRename | null | undefined)[]
  ): IClassRename[] {
    const classRenames: IClassRename[] = classRenamesToCheck.filter(isPresent);
    if (classRenames.length > 0) {
      const classRenameCollectionIdentifiers = classRenameCollection.map(classRenameItem => getClassRenameIdentifier(classRenameItem)!);
      const classRenamesToAdd = classRenames.filter(classRenameItem => {
        const classRenameIdentifier = getClassRenameIdentifier(classRenameItem);
        if (classRenameIdentifier == null || classRenameCollectionIdentifiers.includes(classRenameIdentifier)) {
          return false;
        }
        classRenameCollectionIdentifiers.push(classRenameIdentifier);
        return true;
      });
      return [...classRenamesToAdd, ...classRenameCollection];
    }
    return classRenameCollection;
  }

  protected convertDateFromClient(classRename: IClassRename): IClassRename {
    return Object.assign({}, classRename, {
      dt: classRename.dt?.isValid() ? classRename.dt.toJSON() : undefined,
      gotime: classRename.gotime?.isValid() ? classRename.gotime.toJSON() : undefined,
      sjrq: classRename.sjrq?.isValid() ? classRename.sjrq.toJSON() : undefined,
      qsjrq: classRename.qsjrq?.isValid() ? classRename.qsjrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dt = res.body.dt ? dayjs(res.body.dt) : undefined;
      res.body.gotime = res.body.gotime ? dayjs(res.body.gotime) : undefined;
      res.body.sjrq = res.body.sjrq ? dayjs(res.body.sjrq) : undefined;
      res.body.qsjrq = res.body.qsjrq ? dayjs(res.body.qsjrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((classRename: IClassRename) => {
        classRename.dt = classRename.dt ? dayjs(classRename.dt) : undefined;
        classRename.gotime = classRename.gotime ? dayjs(classRename.gotime) : undefined;
        classRename.sjrq = classRename.sjrq ? dayjs(classRename.sjrq) : undefined;
        classRename.qsjrq = classRename.qsjrq ? dayjs(classRename.qsjrq) : undefined;
      });
    }
    return res;
  }
}
