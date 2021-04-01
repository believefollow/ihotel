import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { SearchWithPagination } from 'app/core/request/request.model';
import { IBookYst, getBookYstIdentifier } from '../book-yst.model';

export type EntityResponseType = HttpResponse<IBookYst>;
export type EntityArrayResponseType = HttpResponse<IBookYst[]>;

@Injectable({ providedIn: 'root' })
export class BookYstService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/book-ysts');
  public resourceSearchUrl = this.applicationConfigService.getEndpointFor('api/_search/book-ysts');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(bookYst: IBookYst): Observable<EntityResponseType> {
    return this.http.post<IBookYst>(this.resourceUrl, bookYst, { observe: 'response' });
  }

  update(bookYst: IBookYst): Observable<EntityResponseType> {
    return this.http.put<IBookYst>(`${this.resourceUrl}/${getBookYstIdentifier(bookYst) as number}`, bookYst, { observe: 'response' });
  }

  partialUpdate(bookYst: IBookYst): Observable<EntityResponseType> {
    return this.http.patch<IBookYst>(`${this.resourceUrl}/${getBookYstIdentifier(bookYst) as number}`, bookYst, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookYst>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookYst[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookYst[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  addBookYstToCollectionIfMissing(bookYstCollection: IBookYst[], ...bookYstsToCheck: (IBookYst | null | undefined)[]): IBookYst[] {
    const bookYsts: IBookYst[] = bookYstsToCheck.filter(isPresent);
    if (bookYsts.length > 0) {
      const bookYstCollectionIdentifiers = bookYstCollection.map(bookYstItem => getBookYstIdentifier(bookYstItem)!);
      const bookYstsToAdd = bookYsts.filter(bookYstItem => {
        const bookYstIdentifier = getBookYstIdentifier(bookYstItem);
        if (bookYstIdentifier == null || bookYstCollectionIdentifiers.includes(bookYstIdentifier)) {
          return false;
        }
        bookYstCollectionIdentifiers.push(bookYstIdentifier);
        return true;
      });
      return [...bookYstsToAdd, ...bookYstCollection];
    }
    return bookYstCollection;
  }
}
