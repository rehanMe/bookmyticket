/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BookingappTestModule } from '../../../test.module';
import { BookingsUpdateComponent } from 'app/entities/bookings/bookings-update.component';
import { BookingsService } from 'app/entities/bookings/bookings.service';
import { Bookings } from 'app/shared/model/bookings.model';

describe('Component Tests', () => {
  describe('Bookings Management Update Component', () => {
    let comp: BookingsUpdateComponent;
    let fixture: ComponentFixture<BookingsUpdateComponent>;
    let service: BookingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [BookingsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BookingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bookings(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bookings();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
