import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IDCompany, getDCompanyIdentifier } from '../d-company.model';

export type EntityResponseType = HttpResponse<IDCompany>;
export type EntityArrayResponseType = HttpResponse<IDCompany[]>;

@Injectable({ providedIn: 'root' })
export class DCompanyService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/d-companies');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/d-companies');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(dCompany: IDCompany): Observable<EntityResponseType> {
    return this.http.post<IDCompany>(this.resourceUrl, dCompany, { observe: 'response' });
  }

  update(dCompany: IDCompany): Observable<EntityResponseType> {
    return this.http.put<IDCompany>(`${this.resourceUrl}/${getDCompanyIdentifier(dCompany) as number}`, dCompany, { observe: 'response' });
  }

  partialUpdate(dCompany: IDCompany): Observable<EntityResponseType> {
    return this.http.patch<IDCompany>(`${this.resourceUrl}/${getDCompanyIdentifier(dCompany) as number}`, dCompany, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDCompany>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDCompany[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDCompany[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addDCompanyToCollectionIfMissing(dCompanyCollection: IDCompany[], ...dCompaniesToCheck: (IDCompany | null | undefined)[]): IDCompany[] {
    const dCompanies: IDCompany[] = dCompaniesToCheck.filter(isPresent);
    if (dCompanies.length > 0) {
      const dCompanyCollectionIdentifiers = dCompanyCollection.map(dCompanyItem => getDCompanyIdentifier(dCompanyItem)!);
      const dCompaniesToAdd = dCompanies.filter(dCompanyItem => {
        const dCompanyIdentifier = getDCompanyIdentifier(dCompanyItem);
        if (dCompanyIdentifier == null || dCompanyCollectionIdentifiers.includes(dCompanyIdentifier)) {
          return false;
        }
        dCompanyCollectionIdentifiers.push(dCompanyIdentifier);
        return true;
      });
      return [...dCompaniesToAdd, ...dCompanyCollection];
    }
    return dCompanyCollection;
  }
}
