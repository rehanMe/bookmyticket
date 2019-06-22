import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BookingappSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BookingappSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BookingappSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BookingappSharedModule {
  static forRoot() {
    return {
      ngModule: BookingappSharedModule
    };
  }
}
