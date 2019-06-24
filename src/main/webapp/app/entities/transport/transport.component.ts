import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransport } from 'app/shared/model/transport.model';
import { AccountService } from 'app/core';
import { TransportService } from './transport.service';

@Component({
  selector: 'jhi-transport',
  templateUrl: './transport.component.html'
})
export class TransportComponent implements OnInit, OnDestroy {
  transports: ITransport[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transportService: TransportService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transportService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransport[]>) => res.ok),
        map((res: HttpResponse<ITransport[]>) => res.body)
      )
      .subscribe(
        (res: ITransport[]) => {
          this.transports = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransports();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransport) {
    return item.id;
  }

  registerChangeInTransports() {
    this.eventSubscriber = this.eventManager.subscribe('transportListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
