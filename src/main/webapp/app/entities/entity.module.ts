import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transport',
        loadChildren: './transport/transport.module#BookingappTransportModule'
      },
      {
        path: 'train',
        loadChildren: './train/train.module#BookingappTrainModule'
      },
      {
        path: 'bus',
        loadChildren: './bus/bus.module#BookingappBusModule'
      },
      {
        path: 'airplane',
        loadChildren: './airplane/airplane.module#BookingappAirplaneModule'
      },
      {
        path: 'bookings',
        loadChildren: './bookings/bookings.module#BookingappBookingsModule'
      },
      {
        path: 'customer',
        loadChildren: './customer/customer.module#BookingappCustomerModule'
      },
      {
        path: 'source',
        loadChildren: './source/source.module#BookingappSourceModule'
      },
      {
        path: 'destination',
        loadChildren: './destination/destination.module#BookingappDestinationModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappEntityModule {}
