import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDDepot, getDDepotIdentifier } from '../d-depot.model';

export type EntityResponseType = HttpResponse<IDDepot>;
export type EntityArrayResponseType = HttpResponse<IDDepot[]>;

@Injectable({ providedIn: 'root' })
export class DDepotService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-depots');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-depots');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dDepot: IDDepot): Observable<EntityResponseType> {
    return this.http.post<IDDepot>(this.resourceUrl, dDepot, { observe: 'response' });
  }

  update(dDepot: IDDepot): Observable<EntityResponseType> {
    return this.http.put<IDDepot>(`${this.resourceUrl}/${getDDepotIdentifier(dDepot) as number}`, dDepot, { observe: 'response' });
  }

  partialUpdate(dDepot: IDDepot): Observable<EntityResponseType> {
    return this.http.patch<IDDepot>(`${this.resourceUrl}/${getDDepotIdentifier(dDepot) as number}`, dDepot, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDDepot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDDepot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDDepot[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDDepotToCollectionIfMissing(dDepotCollection: IDDepot[], ...dDepotsToCheck: (IDDepot | null | undefined)[]): IDDepot[] {
    const dDepots: IDDepot[] = dDepotsToCheck.filter(isPresent);
    if (dDepots.length > 0) {
      const dDepotCollectionIdentifiers = dDepotCollection.map(dDepotItem => getDDepotIdentifier(dDepotItem)!);
      const dDepotsToAdd = dDepots.filter(dDepotItem => {
        const dDepotIdentifier = getDDepotIdentifier(dDepotItem);
        if (dDepotIdentifier == null || dDepotCollectionIdentifiers.includes(dDepotIdentifier)) {
          return false;
        }
        dDepotCollectionIdentifiers.push(dDepotIdentifier);
        return true;
      });
      return [...dDepotsToAdd, ...dDepotCollection];
    }
    return dDepotCollection;
  }
}
