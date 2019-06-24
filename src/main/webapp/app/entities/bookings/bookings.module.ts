import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingappSharedModule } from 'app/shared';
import {
  BookingsComponent,
  BookingsDetailComponent,
  BookingsUpdateComponent,
  BookingsDeletePopupComponent,
  BookingsDeleteDialogComponent,
  bookingsRoute,
  bookingsPopupRoute
} from './';

const ENTITY_STATES = [...bookingsRoute, ...bookingsPopupRoute];

@NgModule({
  imports: [BookingappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BookingsComponent,
    BookingsDetailComponent,
    BookingsUpdateComponent,
    BookingsDeleteDialogComponent,
    BookingsDeletePopupComponent
  ],
  entryComponents: [BookingsComponent, BookingsUpdateComponent, BookingsDeleteDialogComponent, BookingsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappBookingsModule {}
