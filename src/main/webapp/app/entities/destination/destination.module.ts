import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingappSharedModule } from 'app/shared';
import {
  DestinationComponent,
  DestinationDetailComponent,
  DestinationUpdateComponent,
  DestinationDeletePopupComponent,
  DestinationDeleteDialogComponent,
  destinationRoute,
  destinationPopupRoute
} from './';

const ENTITY_STATES = [...destinationRoute, ...destinationPopupRoute];

@NgModule({
  imports: [BookingappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DestinationComponent,
    DestinationDetailComponent,
    DestinationUpdateComponent,
    DestinationDeleteDialogComponent,
    DestinationDeletePopupComponent
  ],
  entryComponents: [DestinationComponent, DestinationUpdateComponent, DestinationDeleteDialogComponent, DestinationDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappDestinationModule {}
