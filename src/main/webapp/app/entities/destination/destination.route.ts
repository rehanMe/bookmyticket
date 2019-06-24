import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Destination } from 'app/shared/model/destination.model';
import { DestinationService } from './destination.service';
import { DestinationComponent } from './destination.component';
import { DestinationDetailComponent } from './destination-detail.component';
import { DestinationUpdateComponent } from './destination-update.component';
import { DestinationDeletePopupComponent } from './destination-delete-dialog.component';
import { IDestination } from 'app/shared/model/destination.model';

@Injectable({ providedIn: 'root' })
export class DestinationResolve implements Resolve<IDestination> {
  constructor(private service: DestinationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDestination> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Destination>) => response.ok),
        map((destination: HttpResponse<Destination>) => destination.body)
      );
    }
    return of(new Destination());
  }
}

export const destinationRoute: Routes = [
  {
    path: '',
    component: DestinationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DestinationDetailComponent,
    resolve: {
      destination: DestinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DestinationUpdateComponent,
    resolve: {
      destination: DestinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DestinationUpdateComponent,
    resolve: {
      destination: DestinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const destinationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DestinationDeletePopupComponent,
    resolve: {
      destination: DestinationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Destinations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
