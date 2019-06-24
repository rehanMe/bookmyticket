import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBookings } from 'app/shared/model/bookings.model';

type EntityResponseType = HttpResponse<IBookings>;
type EntityArrayResponseType = HttpResponse<IBookings[]>;

@Injectable({ providedIn: 'root' })
export class BookingsService {
  public resourceUrl = SERVER_API_URL + 'api/bookings';

  constructor(protected http: HttpClient) {}

  create(bookings: IBookings): Observable<EntityResponseType> {
    return this.http.post<IBookings>(this.resourceUrl, bookings, { observe: 'response' });
  }

  update(bookings: IBookings): Observable<EntityResponseType> {
    return this.http.put<IBookings>(this.resourceUrl, bookings, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
