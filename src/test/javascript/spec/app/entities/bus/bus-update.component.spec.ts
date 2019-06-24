/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BookingappTestModule } from '../../../test.module';
import { BusUpdateComponent } from 'app/entities/bus/bus-update.component';
import { BusService } from 'app/entities/bus/bus.service';
import { Bus } from 'app/shared/model/bus.model';

describe('Component Tests', () => {
  describe('Bus Management Update Component', () => {
    let comp: BusUpdateComponent;
    let fixture: ComponentFixture<BusUpdateComponent>;
    let service: BusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [BusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bus(123);
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
        const entity = new Bus();
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
