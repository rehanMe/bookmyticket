import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Airplane } from 'app/shared/model/airplane.model';
import { AirplaneService } from './airplane.service';
import { AirplaneComponent } from './airplane.component';
import { AirplaneDetailComponent } from './airplane-detail.component';
import { AirplaneUpdateComponent } from './airplane-update.component';
import { AirplaneDeletePopupComponent } from './airplane-delete-dialog.component';
import { IAirplane } from 'app/shared/model/airplane.model';

@Injectable({ providedIn: 'root' })
export class AirplaneResolve implements Resolve<IAirplane> {
  constructor(private service: AirplaneService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAirplane> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Airplane>) => response.ok),
        map((airplane: HttpResponse<Airplane>) => airplane.body)
      );
    }
    return of(new Airplane());
  }
}

export const airplaneRoute: Routes = [
  {
    path: '',
    component: AirplaneComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Airplanes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AirplaneDetailComponent,
    resolve: {
      airplane: AirplaneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Airplanes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AirplaneUpdateComponent,
    resolve: {
      airplane: AirplaneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Airplanes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AirplaneUpdateComponent,
    resolve: {
      airplane: AirplaneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Airplanes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const airplanePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AirplaneDeletePopupComponent,
    resolve: {
      airplane: AirplaneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Airplanes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
