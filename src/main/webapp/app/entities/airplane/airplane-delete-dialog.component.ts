import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAirplane } from 'app/shared/model/airplane.model';
import { AirplaneService } from './airplane.service';

@Component({
  selector: 'jhi-airplane-delete-dialog',
  templateUrl: './airplane-delete-dialog.component.html'
})
export class AirplaneDeleteDialogComponent {
  airplane: IAirplane;

  constructor(protected airplaneService: AirplaneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.airplaneService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'airplaneListModification',
        content: 'Deleted an airplane'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-airplane-delete-popup',
  template: ''
})
export class AirplaneDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ airplane }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AirplaneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.airplane = airplane;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/airplane', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/airplane', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
