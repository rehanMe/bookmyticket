import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBus, Bus } from 'app/shared/model/bus.model';
import { BusService } from './bus.service';

@Component({
  selector: 'jhi-bus-update',
  templateUrl: './bus-update.component.html'
})
export class BusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    busName: [],
    busNumber: [],
    bFare: [],
    bTiming: []
  });

  constructor(protected busService: BusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bus }) => {
      this.updateForm(bus);
    });
  }

  updateForm(bus: IBus) {
    this.editForm.patchValue({
      id: bus.id,
      busName: bus.busName,
      busNumber: bus.busNumber,
      bFare: bus.bFare,
      bTiming: bus.bTiming
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bus = this.createFromForm();
    if (bus.id !== undefined) {
      this.subscribeToSaveResponse(this.busService.update(bus));
    } else {
      this.subscribeToSaveResponse(this.busService.create(bus));
    }
  }

  private createFromForm(): IBus {
    return {
      ...new Bus(),
      id: this.editForm.get(['id']).value,
      busName: this.editForm.get(['busName']).value,
      busNumber: this.editForm.get(['busNumber']).value,
      bFare: this.editForm.get(['bFare']).value,
      bTiming: this.editForm.get(['bTiming']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBus>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
