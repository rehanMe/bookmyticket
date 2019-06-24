import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingappSharedModule } from 'app/shared';
import {
  TransportComponent,
  TransportDetailComponent,
  TransportUpdateComponent,
  TransportDeletePopupComponent,
  TransportDeleteDialogComponent,
  TransportSearchComponent,
  transportRoute,
  transportPopupRoute
} from './';

const ENTITY_STATES = [...transportRoute, ...transportPopupRoute];

@NgModule({
  imports: [BookingappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransportComponent,
    TransportDetailComponent,
    TransportUpdateComponent,
    TransportSearchComponent,
    TransportDeleteDialogComponent,
    TransportDeletePopupComponent
  ],
  entryComponents: [
    TransportComponent,
    TransportUpdateComponent,
    TransportSearchComponent,
    TransportDeleteDialogComponent,
    TransportDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappTransportModule {}
