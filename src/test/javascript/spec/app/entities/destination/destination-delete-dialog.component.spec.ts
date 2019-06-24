/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BookingappTestModule } from '../../../test.module';
import { DestinationDeleteDialogComponent } from 'app/entities/destination/destination-delete-dialog.component';
import { DestinationService } from 'app/entities/destination/destination.service';

describe('Component Tests', () => {
  describe('Destination Management Delete Component', () => {
    let comp: DestinationDeleteDialogComponent;
    let fixture: ComponentFixture<DestinationDeleteDialogComponent>;
    let service: DestinationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [DestinationDeleteDialogComponent]
      })
        .overrideTemplate(DestinationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DestinationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinationService);
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
