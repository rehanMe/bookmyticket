import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBookings } from 'app/shared/model/bookings.model';
import { AccountService } from 'app/core';
import { BookingsService } from './bookings.service';

@Component({
  selector: 'jhi-bookings',
  templateUrl: './bookings.component.html'
})
export class BookingsComponent implements OnInit, OnDestroy {
  bookings: IBookings[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected bookingsService: BookingsService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.bookingsService
      .query()
      .pipe(
        filter((res: HttpResponse<IBookings[]>) => res.ok),
        map((res: HttpResponse<IBookings[]>) => res.body)
      )
      .subscribe(
        (res: IBookings[]) => {
          this.bookings = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBookings();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBookings) {
    return item.id;
  }

  registerChangeInBookings() {
    this.eventSubscriber = this.eventManager.subscribe('bookingsListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
