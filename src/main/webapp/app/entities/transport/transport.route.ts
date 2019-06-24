import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Transport } from 'app/shared/model/transport.model';
import { TransportService } from './transport.service';
import { TransportComponent } from './transport.component';
import { TransportDetailComponent } from './transport-detail.component';
import { TransportUpdateComponent } from './transport-update.component';
import { TransportSearchComponent } from './transport-search.component';
import { TransportDeletePopupComponent } from './transport-delete-dialog.component';
import { ITransport } from 'app/shared/model/transport.model';

@Injectable({ providedIn: 'root' })
export class TransportResolve implements Resolve<ITransport> {
  constructor(private service: TransportService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransport> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Transport>) => response.ok),
        map((transport: HttpResponse<Transport>) => transport.body)
      );
    }
    return of(new Transport());
  }
}

export const transportRoute: Routes = [
  {
    path: '',
    component: TransportComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transports'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransportDetailComponent,
    resolve: {
      transport: TransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transports'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransportUpdateComponent,
    resolve: {
      transport: TransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transports'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'search',
    component: TransportSearchComponent,
    resolve: {
      transport: TransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transports'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransportUpdateComponent,
    resolve: {
      transport: TransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transports'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transportPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransportDeletePopupComponent,
    resolve: {
      transport: TransportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transports'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
