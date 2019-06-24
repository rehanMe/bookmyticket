import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookings } from 'app/shared/model/bookings.model';
import { BookingsService } from './bookings.service';

@Component({
  selector: 'jhi-bookings-delete-dialog',
  templateUrl: './bookings-delete-dialog.component.html'
})
export class BookingsDeleteDialogComponent {
  bookings: IBookings;

  constructor(protected bookingsService: BookingsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.bookingsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'bookingsListModification',
        content: 'Deleted an bookings'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-bookings-delete-popup',
  template: ''
})
export class BookingsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bookings }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BookingsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.bookings = bookings;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/bookings', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/bookings', { outlets: { popup: null } }]);
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
