import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAirplane } from 'app/shared/model/airplane.model';

type EntityResponseType = HttpResponse<IAirplane>;
type EntityArrayResponseType = HttpResponse<IAirplane[]>;

@Injectable({ providedIn: 'root' })
export class AirplaneService {
  public resourceUrl = SERVER_API_URL + 'api/airplanes';

  constructor(protected http: HttpClient) {}

  create(airplane: IAirplane): Observable<EntityResponseType> {
    return this.http.post<IAirplane>(this.resourceUrl, airplane, { observe: 'response' });
  }

  update(airplane: IAirplane): Observable<EntityResponseType> {
    return this.http.put<IAirplane>(this.resourceUrl, airplane, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAirplane>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAirplane[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
