import { NgModule } from '@angular/core';

import { BookingappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [BookingappSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [BookingappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BookingappSharedCommonModule {}
