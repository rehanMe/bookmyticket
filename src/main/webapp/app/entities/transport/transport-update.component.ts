import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITransport, Transport } from 'app/shared/model/transport.model';
import { TransportService } from './transport.service';
import { ITrain } from 'app/shared/model/train.model';
import { TrainService } from 'app/entities/train';
import { IBus } from 'app/shared/model/bus.model';
import { BusService } from 'app/entities/bus';
import { IAirplane } from 'app/shared/model/airplane.model';
import { AirplaneService } from 'app/entities/airplane';
import { ISource } from 'app/shared/model/source.model';
import { SourceService } from 'app/entities/source';
import { IDestination } from 'app/shared/model/destination.model';
import { DestinationService } from 'app/entities/destination';

@Component({
  selector: 'jhi-transport-update',
  templateUrl: './transport-update.component.html'
})
export class TransportUpdateComponent implements OnInit {
  isSaving: boolean;

  trains: ITrain[];

  buses: IBus[];

  airplanes: IAirplane[];

  sources: ISource[];

  destinations: IDestination[];

  editForm = this.fb.group({
    id: [],
    transportType: [null, [Validators.required]],
    serviceProviderName: [null, [Validators.required]],
    availability: [],
    train: [],
    bus: [],
    airplane: [],
    source: [],
    destination: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transportService: TransportService,
    protected trainService: TrainService,
    protected busService: BusService,
    protected airplaneService: AirplaneService,
    protected sourceService: SourceService,
    protected destinationService: DestinationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transport }) => {
      this.updateForm(transport);
    });
    this.trainService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITrain[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITrain[]>) => response.body)
      )
      .subscribe((res: ITrain[]) => (this.trains = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.busService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBus[]>) => response.body)
      )
      .subscribe((res: IBus[]) => (this.buses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.airplaneService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAirplane[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAirplane[]>) => response.body)
      )
      .subscribe((res: IAirplane[]) => (this.airplanes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISource[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISource[]>) => response.body)
      )
      .subscribe((res: ISource[]) => (this.sources = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.destinationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDestination[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDestination[]>) => response.body)
      )
      .subscribe((res: IDestination[]) => (this.destinations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transport: ITransport) {
    this.editForm.patchValue({
      id: transport.id,
      transportType: transport.transportType,
      serviceProviderName: transport.serviceProviderName,
      availability: transport.availability,
      train: transport.train,
      bus: transport.bus,
      airplane: transport.airplane,
      source: transport.source,
      destination: transport.destination
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transport = this.createFromForm();
    if (transport.id !== undefined) {
      this.subscribeToSaveResponse(this.transportService.update(transport));
    } else {
      this.subscribeToSaveResponse(this.transportService.create(transport));
    }
  }

  private createFromForm(): ITransport {
    return {
      ...new Transport(),
      id: this.editForm.get(['id']).value,
      transportType: this.editForm.get(['transportType']).value,
      serviceProviderName: this.editForm.get(['serviceProviderName']).value,
      availability: this.editForm.get(['availability']).value,
      train: this.editForm.get(['train']).value,
      bus: this.editForm.get(['bus']).value,
      airplane: this.editForm.get(['airplane']).value,
      source: this.editForm.get(['source']).value,
      destination: this.editForm.get(['destination']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransport>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTrainById(index: number, item: ITrain) {
    return item.id;
  }

  trackBusById(index: number, item: IBus) {
    return item.id;
  }

  trackAirplaneById(index: number, item: IAirplane) {
    return item.id;
  }

  trackSourceById(index: number, item: ISource) {
    return item.id;
  }

  trackDestinationById(index: number, item: IDestination) {
    return item.id;
  }
}
