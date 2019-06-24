/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingappTestModule } from '../../../test.module';
import { BookingsDetailComponent } from 'app/entities/bookings/bookings-detail.component';
import { Bookings } from 'app/shared/model/bookings.model';

describe('Component Tests', () => {
  describe('Bookings Management Detail Component', () => {
    let comp: BookingsDetailComponent;
    let fixture: ComponentFixture<BookingsDetailComponent>;
    const route = ({ data: of({ bookings: new Bookings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [BookingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BookingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
