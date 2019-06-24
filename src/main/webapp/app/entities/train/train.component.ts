import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITrain } from 'app/shared/model/train.model';
import { AccountService } from 'app/core';
import { TrainService } from './train.service';

@Component({
  selector: 'jhi-train',
  templateUrl: './train.component.html'
})
export class TrainComponent implements OnInit, OnDestroy {
  trains: ITrain[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected trainService: TrainService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.trainService
      .query()
      .pipe(
        filter((res: HttpResponse<ITrain[]>) => res.ok),
        map((res: HttpResponse<ITrain[]>) => res.body)
      )
      .subscribe(
        (res: ITrain[]) => {
          this.trains = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTrains();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITrain) {
    return item.id;
  }

  registerChangeInTrains() {
    this.eventSubscriber = this.eventManager.subscribe('trainListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
