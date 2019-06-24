import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingappSharedModule } from 'app/shared';
import {
  AirplaneComponent,
  AirplaneDetailComponent,
  AirplaneUpdateComponent,
  AirplaneDeletePopupComponent,
  AirplaneDeleteDialogComponent,
  airplaneRoute,
  airplanePopupRoute
} from './';

const ENTITY_STATES = [...airplaneRoute, ...airplanePopupRoute];

@NgModule({
  imports: [BookingappSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AirplaneComponent,
    AirplaneDetailComponent,
    AirplaneUpdateComponent,
    AirplaneDeleteDialogComponent,
    AirplaneDeletePopupComponent
  ],
  entryComponents: [AirplaneComponent, AirplaneUpdateComponent, AirplaneDeleteDialogComponent, AirplaneDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappAirplaneModule {}
