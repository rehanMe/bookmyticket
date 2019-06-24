/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingappTestModule } from '../../../test.module';
import { BookingsComponent } from 'app/entities/bookings/bookings.component';
import { BookingsService } from 'app/entities/bookings/bookings.service';
import { Bookings } from 'app/shared/model/bookings.model';

describe('Component Tests', () => {
  describe('Bookings Management Component', () => {
    let comp: BookingsComponent;
    let fixture: ComponentFixture<BookingsComponent>;
    let service: BookingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [BookingsComponent],
        providers: []
      })
        .overrideTemplate(BookingsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bookings(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bookings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
