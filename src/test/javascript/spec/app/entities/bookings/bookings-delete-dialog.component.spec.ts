/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingappTestModule } from '../../../test.module';
import { BookingsDeleteDialogComponent } from 'app/entities/bookings/bookings-delete-dialog.component';
import { BookingsService } from 'app/entities/bookings/bookings.service';

describe('Component Tests', () => {
  describe('Bookings Management Delete Component', () => {
    let comp: BookingsDeleteDialogComponent;
    let fixture: ComponentFixture<BookingsDeleteDialogComponent>;
    let service: BookingsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [BookingsDeleteDialogComponent]
      })
        .overrideTemplate(BookingsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookingsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
