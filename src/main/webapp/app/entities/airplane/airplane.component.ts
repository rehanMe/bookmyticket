import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAirplane } from 'app/shared/model/airplane.model';
import { AccountService } from 'app/core';
import { AirplaneService } from './airplane.service';

@Component({
  selector: 'jhi-airplane',
  templateUrl: './airplane.component.html'
})
export class AirplaneComponent implements OnInit, OnDestroy {
  airplanes: IAirplane[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected airplaneService: AirplaneService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.airplaneService
      .query()
      .pipe(
        filter((res: HttpResponse<IAirplane[]>) => res.ok),
        map((res: HttpResponse<IAirplane[]>) => res.body)
      )
      .subscribe(
        (res: IAirplane[]) => {
          this.airplanes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAirplanes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAirplane) {
    return item.id;
  }

  registerChangeInAirplanes() {
    this.eventSubscriber = this.eventManager.subscribe('airplaneListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
