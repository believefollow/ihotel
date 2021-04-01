import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IChoice, getChoiceIdentifier } from '../choice.model';

export type EntityResponseType = HttpResponse<IChoice>;
export type EntityArrayResponseType = HttpResponse<IChoice[]>;

@Injectable({ providedIn: 'root' })
export class ChoiceService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/choices');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/choices');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(choice: IChoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(choice);
    return this.http
      .post<IChoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(choice: IChoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(choice);
    return this.http
      .put<IChoice>(`${this.resourceUrl}/${getChoiceIdentifier(choice) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(choice: IChoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(choice);
    return this.http
      .patch<IChoice>(`${this.resourceUrl}/${getChoiceIdentifier(choice) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChoice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChoice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  addChoiceToCollectionIfMissing(choiceCollection: IChoice[], ...choicesToCheck: (IChoice | null | undefined)[]): IChoice[] {
    const choices: IChoice[] = choicesToCheck.filter(isPresent);
    if (choices.length > 0) {
      const choiceCollectionIdentifiers = choiceCollection.map(choiceItem => getChoiceIdentifier(choiceItem)!);
      const choicesToAdd = choices.filter(choiceItem => {
        const choiceIdentifier = getChoiceIdentifier(choiceItem);
        if (choiceIdentifier == null || choiceCollectionIdentifiers.includes(choiceIdentifier)) {
          return false;
        }
        choiceCollectionIdentifiers.push(choiceIdentifier);
        return true;
      });
      return [...choicesToAdd, ...choiceCollection];
    }
    return choiceCollection;
  }

  protected convertDateFromClient(choice: IChoice): IChoice {
    return Object.assign({}, choice, {
      createdat: choice.createdat?.isValid() ? choice.createdat.toJSON() : undefined,
      updatedat: choice.updatedat?.isValid() ? choice.updatedat.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdat = res.body.createdat ? dayjs(res.body.createdat) : undefined;
      res.body.updatedat = res.body.updatedat ? dayjs(res.body.updatedat) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((choice: IChoice) => {
        choice.createdat = choice.createdat ? dayjs(choice.createdat) : undefined;
        choice.updatedat = choice.updatedat ? dayjs(choice.updatedat) : undefined;
      });
    }
    return res;
  }
}
