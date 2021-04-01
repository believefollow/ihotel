import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { ICardysq, getCardysqIdentifier } from '../cardysq.model';

export type EntityResponseType = HttpResponse<ICardysq>;
export type EntityArrayResponseType = HttpResponse<ICardysq[]>;

@Injectable({ providedIn: 'root' })
export class CardysqService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/cardysqs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/cardysqs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(cardysq: ICardysq): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardysq);
    return this.http
      .post<ICardysq>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cardysq: ICardysq): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardysq);
    return this.http
      .put<ICardysq>(`${this.resourceUrl}/${getCardysqIdentifier(cardysq) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(cardysq: ICardysq): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardysq);
    return this.http
      .patch<ICardysq>(`${this.resourceUrl}/${getCardysqIdentifier(cardysq) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICardysq>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICardysq[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICardysq[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addCardysqToCollectionIfMissing(cardysqCollection: ICardysq[], ...cardysqsToCheck: (ICardysq | null | undefined)[]): ICardysq[] {
    const cardysqs: ICardysq[] = cardysqsToCheck.filter(isPresent);
    if (cardysqs.length > 0) {
      const cardysqCollectionIdentifiers = cardysqCollection.map(cardysqItem => getCardysqIdentifier(cardysqItem)!);
      const cardysqsToAdd = cardysqs.filter(cardysqItem => {
        const cardysqIdentifier = getCardysqIdentifier(cardysqItem);
        if (cardysqIdentifier == null || cardysqCollectionIdentifiers.includes(cardysqIdentifier)) {
          return false;
        }
        cardysqCollectionIdentifiers.push(cardysqIdentifier);
        return true;
      });
      return [...cardysqsToAdd, ...cardysqCollection];
    }
    return cardysqCollection;
  }

  protected convertDateFromClient(cardysq: ICardysq): ICardysq {
    return Object.assign({}, cardysq, {
      rq: cardysq.rq?.isValid() ? cardysq.rq.toJSON() : undefined,
      hoteltime: cardysq.hoteltime?.isValid() ? cardysq.hoteltime.toJSON() : undefined,
      yxrq: cardysq.yxrq?.isValid() ? cardysq.yxrq.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.rq = res.body.rq ? dayjs(res.body.rq) : undefined;
      res.body.hoteltime = res.body.hoteltime ? dayjs(res.body.hoteltime) : undefined;
      res.body.yxrq = res.body.yxrq ? dayjs(res.body.yxrq) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cardysq: ICardysq) => {
        cardysq.rq = cardysq.rq ? dayjs(cardysq.rq) : undefined;
        cardysq.hoteltime = cardysq.hoteltime ? dayjs(cardysq.hoteltime) : undefined;
        cardysq.yxrq = cardysq.yxrq ? dayjs(cardysq.yxrq) : undefined;
      });
    }
    return res;
  }
}
