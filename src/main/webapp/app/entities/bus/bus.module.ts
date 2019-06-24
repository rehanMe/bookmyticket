import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingappSharedModule } from 'app/shared';
import {
  BusComponent,
  BusDetailComponent,
  BusUpdateComponent,
  BusDeletePopupComponent,
  BusDeleteDialogComponent,
  busRoute,
  busPopupRoute
} from './';

const ENTITY_STATES = [...busRoute, ...busPopupRoute];

@NgModule({
  imports: [BookingappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BusComponent, BusDetailComponent, BusUpdateComponent, BusDeleteDialogComponent, BusDeletePopupComponent],
  entryComponents: [BusComponent, BusUpdateComponent, BusDeleteDialogComponent, BusDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappBusModule {}
