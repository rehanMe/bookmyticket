import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransport } from 'app/shared/model/transport.model';
import { TransportService } from './transport.service';

@Component({
  selector: 'jhi-transport-delete-dialog',
  templateUrl: './transport-delete-dialog.component.html'
})
export class TransportDeleteDialogComponent {
  transport: ITransport;

  constructor(protected transportService: TransportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transportService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transportListModification',
        content: 'Deleted an transport'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transport-delete-popup',
  template: ''
})
export class TransportDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transport }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transport = transport;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transport', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transport', { outlets: { popup: null } }]);
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
