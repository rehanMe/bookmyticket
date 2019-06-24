import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBookings, Bookings } from 'app/shared/model/bookings.model';
import { BookingsService } from './bookings.service';
import { ITransport } from 'app/shared/model/transport.model';
import { TransportService } from 'app/entities/transport';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-bookings-update',
  templateUrl: './bookings-update.component.html'
})
export class BookingsUpdateComponent implements OnInit {
  isSaving: boolean;

  transports: ITransport[];

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    cardNo: [null, [Validators.required, Validators.maxLength(16)]],
    validThru: [null, [Validators.required, Validators.maxLength(5)]],
    cvv: [null, [Validators.required]],
    name: [],
    status: [],
    transport: [],
    customer: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bookingsService: BookingsService,
    protected transportService: TransportService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bookings }) => {
      this.updateForm(bookings);
    });
    this.transportService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransport[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransport[]>) => response.body)
      )
      .subscribe((res: ITransport[]) => (this.transports = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bookings: IBookings) {
    this.editForm.patchValue({
      id: bookings.id,
      cardNo: bookings.cardNo,
      validThru: bookings.validThru,
      cvv: bookings.cvv,
      name: bookings.name,
      status: bookings.status,
      transport: bookings.transport,
      customer: bookings.customer
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bookings = this.createFromForm();
    if (bookings.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingsService.update(bookings));
    } else {
      this.subscribeToSaveResponse(this.bookingsService.create(bookings));
    }
  }

  private createFromForm(): IBookings {
    return {
      ...new Bookings(),
      id: this.editForm.get(['id']).value,
      cardNo: this.editForm.get(['cardNo']).value,
      validThru: this.editForm.get(['validThru']).value,
      cvv: this.editForm.get(['cvv']).value,
      name: this.editForm.get(['name']).value,
      status: this.editForm.get(['status']).value,
      transport: this.editForm.get(['transport']).value,
      customer: this.editForm.get(['customer']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookings>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;

    //this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTransportById(index: number, item: ITransport) {
    return item.id;
  }

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
