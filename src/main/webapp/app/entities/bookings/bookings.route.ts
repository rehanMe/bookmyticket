import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bookings } from 'app/shared/model/bookings.model';
import { BookingsService } from './bookings.service';
import { BookingsComponent } from './bookings.component';
import { BookingsDetailComponent } from './bookings-detail.component';
import { BookingsUpdateComponent } from './bookings-update.component';
import { BookingsDeletePopupComponent } from './bookings-delete-dialog.component';
import { IBookings } from 'app/shared/model/bookings.model';

@Injectable({ providedIn: 'root' })
export class BookingsResolve implements Resolve<IBookings> {
  constructor(private service: BookingsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBookings> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Bookings>) => response.ok),
        map((bookings: HttpResponse<Bookings>) => bookings.body)
      );
    }
    return of(new Bookings());
  }
}

export const bookingsRoute: Routes = [
  {
    path: '',
    component: BookingsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bookings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BookingsDetailComponent,
    resolve: {
      bookings: BookingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bookings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BookingsUpdateComponent,
    resolve: {
      bookings: BookingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bookings'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BookingsUpdateComponent,
    resolve: {
      bookings: BookingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bookings'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bookingsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BookingsDeletePopupComponent,
    resolve: {
      bookings: BookingsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bookings'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
