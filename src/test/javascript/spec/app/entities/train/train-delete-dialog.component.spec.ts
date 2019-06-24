/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingappTestModule } from '../../../test.module';
import { TrainDeleteDialogComponent } from 'app/entities/train/train-delete-dialog.component';
import { TrainService } from 'app/entities/train/train.service';

describe('Component Tests', () => {
  describe('Train Management Delete Component', () => {
    let comp: TrainDeleteDialogComponent;
    let fixture: ComponentFixture<TrainDeleteDialogComponent>;
    let service: TrainService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [TrainDeleteDialogComponent]
      })
        .overrideTemplate(TrainDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainService);
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
