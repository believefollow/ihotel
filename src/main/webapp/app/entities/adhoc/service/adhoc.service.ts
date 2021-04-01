import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IAdhoc, getAdhocIdentifier } from '../adhoc.model';

export type EntityResponseType = HttpResponse<IAdhoc>;
export type EntityArrayResponseType = HttpResponse<IAdhoc[]>;

@Injectable({ providedIn: 'root' })
export class AdhocService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/adhocs');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/adhocs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(adhoc: IAdhoc): Observable<EntityResponseType> {
    return this.http.post<IAdhoc>(this.resourceUrl, adhoc, { observe: 'response' });
  }

  update(adhoc: IAdhoc): Observable<EntityResponseType> {
    return this.http.put<IAdhoc>(`${this.resourceUrl}/${getAdhocIdentifier(adhoc) as string}`, adhoc, { observe: 'response' });
  }

  partialUpdate(adhoc: IAdhoc): Observable<EntityResponseType> {
    return this.http.patch<IAdhoc>(`${this.resourceUrl}/${getAdhocIdentifier(adhoc) as string}`, adhoc, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdhoc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdhoc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdhoc[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addAdhocToCollectionIfMissing(adhocCollection: IAdhoc[], ...adhocsToCheck: (IAdhoc | null | undefined)[]): IAdhoc[] {
    const adhocs: IAdhoc[] = adhocsToCheck.filter(isPresent);
    if (adhocs.length > 0) {
      const adhocCollectionIdentifiers = adhocCollection.map(adhocItem => getAdhocIdentifier(adhocItem)!);
      const adhocsToAdd = adhocs.filter(adhocItem => {
        const adhocIdentifier = getAdhocIdentifier(adhocItem);
        if (adhocIdentifier == null || adhocCollectionIdentifiers.includes(adhocIdentifier)) {
          return false;
        }
        adhocCollectionIdentifiers.push(adhocIdentifier);
        return true;
      });
      return [...adhocsToAdd, ...adhocCollection];
    }
    return adhocCollection;
  }
}
