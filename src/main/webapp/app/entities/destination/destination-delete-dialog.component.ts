import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDestination } from 'app/shared/model/destination.model';
import { DestinationService } from './destination.service';

@Component({
  selector: 'jhi-destination-delete-dialog',
  templateUrl: './destination-delete-dialog.component.html'
})
export class DestinationDeleteDialogComponent {
  destination: IDestination;

  constructor(
    protected destinationService: DestinationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.destinationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'destinationListModification',
        content: 'Deleted an destination'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-destination-delete-popup',
  template: ''
})
export class DestinationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ destination }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DestinationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.destination = destination;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/destination', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/destination', { outlets: { popup: null } }]);
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
