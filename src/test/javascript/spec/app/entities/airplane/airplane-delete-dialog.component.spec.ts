/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingappTestModule } from '../../../test.module';
import { AirplaneDeleteDialogComponent } from 'app/entities/airplane/airplane-delete-dialog.component';
import { AirplaneService } from 'app/entities/airplane/airplane.service';

describe('Component Tests', () => {
  describe('Airplane Management Delete Component', () => {
    let comp: AirplaneDeleteDialogComponent;
    let fixture: ComponentFixture<AirplaneDeleteDialogComponent>;
    let service: AirplaneService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [AirplaneDeleteDialogComponent]
      })
        .overrideTemplate(AirplaneDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AirplaneDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AirplaneService);
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
