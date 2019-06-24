import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBus } from 'app/shared/model/bus.model';
import { AccountService } from 'app/core';
import { BusService } from './bus.service';

@Component({
  selector: 'jhi-bus',
  templateUrl: './bus.component.html'
})
export class BusComponent implements OnInit, OnDestroy {
  buses: IBus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected busService: BusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.busService
      .query()
      .pipe(
        filter((res: HttpResponse<IBus[]>) => res.ok),
        map((res: HttpResponse<IBus[]>) => res.body)
      )
      .subscribe(
        (res: IBus[]) => {
          this.buses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBus) {
    return item.id;
  }

  registerChangeInBuses() {
    this.eventSubscriber = this.eventManager.subscribe('busListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
