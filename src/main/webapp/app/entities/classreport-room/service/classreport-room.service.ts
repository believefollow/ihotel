import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IClassreportRoom, getClassreportRoomIdentifier } from '../classreport-room.model';

export type EntityResponseType = HttpResponse<IClassreportRoom>;
export type EntityArrayResponseType = HttpResponse<IClassreportRoom[]>;

@Injectable({ providedIn: 'root' })
export class ClassreportRoomService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/classreport-rooms');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/classreport-rooms');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(classreportRoom: IClassreportRoom): Observable<EntityResponseType> {
    return this.http.post<IClassreportRoom>(this.resourceUrl, classreportRoom, { observe: 'response' });
  }

  update(classreportRoom: IClassreportRoom): Observable<EntityResponseType> {
    return this.http.put<IClassreportRoom>(
      `${this.resourceUrl}/${getClassreportRoomIdentifier(classreportRoom) as number}`,
      classreportRoom,
      { observe: 'response' }
    );
  }

  partialUpdate(classreportRoom: IClassreportRoom): Observable<EntityResponseType> {
    return this.http.patch<IClassreportRoom>(
      `${this.resourceUrl}/${getClassreportRoomIdentifier(classreportRoom) as number}`,
      classreportRoom,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassreportRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassreportRoom[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassreportRoom[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addClassreportRoomToCollectionIfMissing(
    classreportRoomCollection: IClassreportRoom[],
    ...classreportRoomsToCheck: (IClassreportRoom | null | undefined)[]
  ): IClassreportRoom[] {
    const classreportRooms: IClassreportRoom[] = classreportRoomsToCheck.filter(isPresent);
    if (classreportRooms.length > 0) {
      const classreportRoomCollectionIdentifiers = classreportRoomCollection.map(
        classreportRoomItem => getClassreportRoomIdentifier(classreportRoomItem)!
      );
      const classreportRoomsToAdd = classreportRooms.filter(classreportRoomItem => {
        const classreportRoomIdentifier = getClassreportRoomIdentifier(classreportRoomItem);
        if (classreportRoomIdentifier == null || classreportRoomCollectionIdentifiers.includes(classreportRoomIdentifier)) {
          return false;
        }
        classreportRoomCollectionIdentifiers.push(classreportRoomIdentifier);
        return true;
      });
      return [...classreportRoomsToAdd, ...classreportRoomCollection];
    }
    return classreportRoomCollection;
  }
}
