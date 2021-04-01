import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDGoods, getDGoodsIdentifier } from '../d-goods.model';

export type EntityResponseType = HttpResponse<IDGoods>;
export type EntityArrayResponseType = HttpResponse<IDGoods[]>;

@Injectable({ providedIn: 'root' })
export class DGoodsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-goods');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-goods');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dGoods: IDGoods): Observable<EntityResponseType> {
    return this.http.post<IDGoods>(this.resourceUrl, dGoods, { observe: 'response' });
  }

  update(dGoods: IDGoods): Observable<EntityResponseType> {
    return this.http.put<IDGoods>(`${this.resourceUrl}/${getDGoodsIdentifier(dGoods) as number}`, dGoods, { observe: 'response' });
  }

  partialUpdate(dGoods: IDGoods): Observable<EntityResponseType> {
    return this.http.patch<IDGoods>(`${this.resourceUrl}/${getDGoodsIdentifier(dGoods) as number}`, dGoods, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDGoods>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDGoods[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDGoods[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDGoodsToCollectionIfMissing(dGoodsCollection: IDGoods[], ...dGoodsToCheck: (IDGoods | null | undefined)[]): IDGoods[] {
    const dGoods: IDGoods[] = dGoodsToCheck.filter(isPresent);
    if (dGoods.length > 0) {
      const dGoodsCollectionIdentifiers = dGoodsCollection.map(dGoodsItem => getDGoodsIdentifier(dGoodsItem)!);
      const dGoodsToAdd = dGoods.filter(dGoodsItem => {
        const dGoodsIdentifier = getDGoodsIdentifier(dGoodsItem);
        if (dGoodsIdentifier == null || dGoodsCollectionIdentifiers.includes(dGoodsIdentifier)) {
          return false;
        }
        dGoodsCollectionIdentifiers.push(dGoodsIdentifier);
        return true;
      });
      return [...dGoodsToAdd, ...dGoodsCollection];
    }
    return dGoodsCollection;
  }
}
