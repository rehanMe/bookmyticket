import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAirplane, Airplane } from 'app/shared/model/airplane.model';
import { AirplaneService } from './airplane.service';

@Component({
  selector: 'jhi-airplane-update',
  templateUrl: './airplane-update.component.html'
})
export class AirplaneUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    flightName: [],
    flightNumber: [],
    fFare: [],
    fTiming: []
  });

  constructor(protected airplaneService: AirplaneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ airplane }) => {
      this.updateForm(airplane);
    });
  }

  updateForm(airplane: IAirplane) {
    this.editForm.patchValue({
      id: airplane.id,
      flightName: airplane.flightName,
      flightNumber: airplane.flightNumber,
      fFare: airplane.fFare,
      fTiming: airplane.fTiming
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const airplane = this.createFromForm();
    if (airplane.id !== undefined) {
      this.subscribeToSaveResponse(this.airplaneService.update(airplane));
    } else {
      this.subscribeToSaveResponse(this.airplaneService.create(airplane));
    }
  }

  private createFromForm(): IAirplane {
    return {
      ...new Airplane(),
      id: this.editForm.get(['id']).value,
      flightName: this.editForm.get(['flightName']).value,
      flightNumber: this.editForm.get(['flightNumber']).value,
      fFare: this.editForm.get(['fFare']).value,
      fTiming: this.editForm.get(['fTiming']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAirplane>>) {
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
